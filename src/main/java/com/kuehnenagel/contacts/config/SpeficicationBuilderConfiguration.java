package com.kuehnenagel.contacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.utils.specifications.CriteriaParser;
import com.kuehnenagel.contacts.utils.specifications.GenericSpecificationsBuilder;

@Configuration
public class SpeficicationBuilderConfiguration {

	@Bean
	public CriteriaParser getCriteriaParser() {
		return new CriteriaParser();
	}
	
	@Bean
	public GenericSpecificationsBuilder<Contact> getGenericSpecificationsBuilderForContact() {
		return new GenericSpecificationsBuilder<>();
	}

}
