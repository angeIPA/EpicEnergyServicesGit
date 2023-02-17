package com.epic.energyservices.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client extends BaseEntity{
	
	private String businessName;
	@Column(length=13)
	private String partitaIva;
	private String email;  
	private String pec;
	private long phone;
	
	
	//indirizzo
	@OneToOne(cascade = CascadeType.ALL)
	private OperationalHeadquarters operationalHeadquarters;
	
	@Embedded
	private RegisteredOffice registeredOffice;
	
	@Embedded
	private Contact contact;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private BusinessType businessType;

	
	//@NonNull
	
}
