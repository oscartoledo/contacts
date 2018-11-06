package com.kuehnenagel.contacts.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.services.ContactService;

@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {
	
	@Mock
	private ContactService contactService;
	
	@InjectMocks
	private ContactController contactController;

	@Test
	public void testFind() {
		List<Contact> expected = new ArrayList<>();
		expected.add(new Contact("Friend 1", "http://localhost/picture/friend1.png"));
		expected.add(new Contact("Friend 2", "http://localhost/picture/friend2.png"));
		Page<Contact> expectedPage = new PageImpl<>(expected);
		Pageable pageSpecification = PageRequest.of(0, 10);
		
		when(contactService.getContacts(anyString(), any(Pageable.class))).thenReturn(expectedPage);
		
		List<Contact> actual = contactController.find("name:**", pageSpecification).getContent();
		
		verify(contactService, times(1)).getContacts(anyString(), any(Pageable.class));
		verifyNoMoreInteractions(contactService);
		
		assertEquals(expected, actual);
	}

}
