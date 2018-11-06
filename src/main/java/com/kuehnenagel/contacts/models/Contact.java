package com.kuehnenagel.contacts.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Contact extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2565477192046515581L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;

	@URL
	@NotBlank
	@Column(nullable = false, unique = true)
	private String pictureUrl;
	
	public Contact() {
		super();
	}

	public Contact(@NotBlank String name, @URL @NotBlank String pictureUrl) {
		super();
		this.name = name;
		this.pictureUrl = pictureUrl;
	}
	
	

}
