package com.epic.energyservices.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OperationalHeadquarters extends BaseEntity{
	
	
	private String adress;
	private String streetNumber;
	private String locality;
	@Column(length = 5)
	private int postalCode;
	@ManyToOne(cascade = CascadeType.ALL)
	private City city;

	@OneToOne//(mappedBy="operationalHeadquarters")
	private Client client;

}
