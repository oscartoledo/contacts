package com.kuehnenagel.contacts.services;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.repositories.ContactsRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LoadDataService {

	@Value("${data.file.load_at_start}")
	protected Boolean loadAtStart;
	
	@Value("${data.file.path}")
	protected String filePath;
	
	private final ContactsRepository contactsRepository;
	
	
	@Autowired
	public LoadDataService(ContactsRepository contactsRepository) {
		super();
		this.contactsRepository = contactsRepository;
	}

	public void loadData() {
		Long contactCount = this.contactsRepository.count();
		if (this.loadAtStart && contactCount == 0) {
			log.info("==================== Start load data from file =====================");
			
			try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
					CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
							.withIgnoreHeaderCase().withQuote(null).withIgnoreSurroundingSpaces(true).withTrim());) {

				for (CSVRecord csvRecord : csvParser) {
					createContact(csvRecord.get(0).trim(), csvRecord.get(1).trim());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			log.info("==================== Finished load data from file =====================");
		}
	}

	protected Contact createContact(String name, String pictureUrl) {
		Contact contact = null;
		try {
			contact = this.contactsRepository.save(new Contact(name, pictureUrl));
		} catch (Exception e) {
			log.info(String.format("Cannot create a Contact with Name: %s and PictureUrl: %s", name, pictureUrl));
		}
		
		return contact;
	}

}
