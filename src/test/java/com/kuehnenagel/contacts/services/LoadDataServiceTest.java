package com.kuehnenagel.contacts.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.repositories.ContactsRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class LoadDataServiceTest {
	
	private File dataFile; 
	
	@Mock
	private ContactsRepository contactsRepository;
	
	@InjectMocks
	private LoadDataService loadDataService;

	@Before
	public void setUp() throws Exception {
		final String[] fileHeader = new String[] {"name", "url"};
		final String[][] fileData = new String[][] {
				new String[] {"Homer Simpson", "https://vignette.wikia.nocookie.net/simpsons/images/b/bd/Homer_Simpson.png/revision/latest/scale-to-width-down/72?cb=20140126234206"},
				new String[] {"Marge Simpson", "https://vignette.wikia.nocookie.net/simpsons/images/4/4d/MargeSimpson.png/revision/latest/scale-to-width-down/78?cb=20180314071936"}
		};
		
		final TemporaryFolder testFolder = new TemporaryFolder();
		testFolder.create();
		dataFile = testFolder.newFile("people.csv");
		
		
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(dataFile.getAbsolutePath()));
	        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
						.withHeader(fileHeader));) {
		
	        Arrays.asList(fileData).stream().forEach(row -> {
				try {
					csvPrinter.printRecord(Arrays.asList(row));
				} catch (IOException e) {
					log.error(String.format("We have problem adding this record: %s", row.toString()));
				}
			});
	        
	        csvPrinter.flush();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadDataWhenMustLoad() {
		loadDataService.filePath = dataFile.getAbsolutePath();
		loadDataService.loadAtStart = true;
		
		when(contactsRepository.count()).thenReturn(0L);
		when(contactsRepository.save(any(Contact.class))).thenReturn(new Contact());
		
		loadDataService.loadData();
		
		verify(contactsRepository, times(2)).save(any(Contact.class));
	}

	@Test
	public void testLoadDataWhenWereAlreadyLoaded() {
		loadDataService.filePath = dataFile.getAbsolutePath();
		loadDataService.loadAtStart = true;
		
		when(contactsRepository.count()).thenReturn(1L);
		
		loadDataService.loadData();
		
		verify(contactsRepository, times(0)).save(any(Contact.class));
	}
	
	@Test
	public void testLoadDataWhenIsDisable() {
		loadDataService.filePath = dataFile.getAbsolutePath();
		loadDataService.loadAtStart = false;
		
		when(contactsRepository.count()).thenReturn(0L);
		
		loadDataService.loadData();
		
		verify(contactsRepository, times(0)).save(any(Contact.class));
	}

	@Test
	public void testCreateContact() {
		when(contactsRepository.save(any(Contact.class))).thenReturn(new Contact());
		
		Contact contact = loadDataService.createContact("Homer Simpson", "https://vignette.wikia.nocookie.net/simpsons/images/4/4d/MargeSimpson.png/revision/latest/scale-to-width-down/78?cb=20180314071936");
		
		assertNotNull(contact);
	}
	
	@Test
	public void testCreateContactWithWronUrl() {
		when(contactsRepository.save(any(Contact.class))).thenThrow(new DataIntegrityViolationException("The field pictureUrl must be a valid URL."));
		
		Contact contact = loadDataService.createContact("Homer Simpson", "bad url");
		
		assertNull(contact);
	}

}
