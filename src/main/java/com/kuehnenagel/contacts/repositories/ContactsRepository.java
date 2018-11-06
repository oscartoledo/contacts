package com.kuehnenagel.contacts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kuehnenagel.contacts.models.Contact;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

}
