package com.kuehnenagel.contacts.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.repositories.ContactsRepository;
import com.kuehnenagel.contacts.specifications.ContactSpecification;
import com.kuehnenagel.contacts.utils.specifications.CriteriaParser;
import com.kuehnenagel.contacts.utils.specifications.GenericSpecificationsBuilder;
import com.kuehnenagel.contacts.utils.specifications.SpecSearchCriteria;

@RunWith(MockitoJUnitRunner.class)	
public class ContactServiceTest {
	
	@Mock
	private ContactsRepository contactsRepository;
	
	@Mock
	private GenericSpecificationsBuilder<Contact> builder;
	
	@Mock
	private CriteriaParser parser;
	
	@InjectMocks
	private ContactService contactService;

	@Test
	@SuppressWarnings("unchecked")
	public void testGetContactsWithFilter() {
		List<Contact> expected = new ArrayList<>();
		expected.add(new Contact("Friend 1", "http://localhost/picture/friend1.png"));
		expected.add(new Contact("Friend 2", "http://localhost/picture/friend2.png"));
		Page<Contact> expectedPage = new PageImpl<>(expected);
		Pageable pageSpecification = PageRequest.of(0, 10);
		
		when(parser.parse(anyString())).thenReturn(new ArrayDeque<>());
		when(builder.build(any(Deque.class), any(Function.class))).thenReturn(new ContactSpecification(new SpecSearchCriteria()));
		when(contactsRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);
		
		List<Contact> actual = contactService.getContacts("name:*", pageSpecification).getContent();
		
        verify(contactsRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verifyNoMoreInteractions(contactsRepository);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetContactsWithoutFilter() {
		List<Contact> expected = new ArrayList<>();
		expected.add(new Contact("Friend 1", "http://localhost/picture/friend1.png"));
		expected.add(new Contact("Friend 2", "http://localhost/picture/friend2.png"));
		Page<Contact> expectedPage = new PageImpl<>(expected);
		Pageable pageSpecification = PageRequest.of(0, 10);
		
		when(contactsRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
		
		List<Contact> actual = contactService.getContacts(null, pageSpecification).getContent();
		
        verify(contactsRepository, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(contactsRepository);
		
		assertEquals(expected, actual);
	}

}
