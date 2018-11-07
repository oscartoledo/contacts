package com.kuehnenagel.contacts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.services.ContactService;

@RestController
@RequestMapping(value="/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {
	
	private final ContactService contactService;

	@Autowired
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}
	
	@GetMapping
	public Page<Contact> find(@RequestParam(value = "search", required = false) String search, 
			Pageable pageable) {
		 
		return contactService.getContacts(search, pageable);
	}

}
