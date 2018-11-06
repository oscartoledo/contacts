package com.kuehnenagel.contacts.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.repositories.ContactsRepository;
import com.kuehnenagel.contacts.specifications.ContactSpecification;
import com.kuehnenagel.contacts.utils.specifications.CriteriaParser;
import com.kuehnenagel.contacts.utils.specifications.GenericSpecificationsBuilder;

@Service
public class ContactService {
	
	private final ContactsRepository contactsRepository;
	private final CriteriaParser parser;
	private final GenericSpecificationsBuilder<Contact> builder;

	@Autowired
	public ContactService(ContactsRepository contactsRepository,
			CriteriaParser parser,
			GenericSpecificationsBuilder<Contact> builder) {
		this.contactsRepository = contactsRepository;
		this.parser = parser;
		this.builder = builder;
	}
	
	public Page<Contact> getContacts(String search, Pageable pageable) {
		Page<Contact> page = null;
		
		if(StringUtils.isNoneBlank(search)) {
			Specification<Contact> specification = builder.build(parser.parse(search), ContactSpecification::new);
			page = contactsRepository.findAll(specification, pageable);
		} else {
			page = contactsRepository.findAll(pageable);
		}
		
		return page;
	}

}
