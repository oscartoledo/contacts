package com.kuehnenagel.contacts.utils.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface GenericCriteriaUtils<T> {

	public default Predicate createPredicate(SpecSearchCriteria criteria, Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		switch (criteria.getOperation()) {
			case EQUALITY:
				return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
			case NEGATION:
				return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
			case GREATER_THAN:
				return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
			case LESS_THAN:
				return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
			case LIKE:
				return criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue().toString());
			case STARTS_WITH:
				return criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
			case ENDS_WITH:
				return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
			case CONTAINS:
				return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
			default:
				return null;
		}	
	}

}
