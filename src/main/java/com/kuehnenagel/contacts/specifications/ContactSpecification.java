package com.kuehnenagel.contacts.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kuehnenagel.contacts.models.Contact;
import com.kuehnenagel.contacts.utils.specifications.GenericCriteriaUtils;
import com.kuehnenagel.contacts.utils.specifications.SpecSearchCriteria;

public class ContactSpecification implements Specification<Contact>, GenericCriteriaUtils<Contact> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1643648537896235743L;
	
	private SpecSearchCriteria criteria;

	public ContactSpecification(final SpecSearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return createPredicate(criteria, root, query, criteriaBuilder);
	}

}
