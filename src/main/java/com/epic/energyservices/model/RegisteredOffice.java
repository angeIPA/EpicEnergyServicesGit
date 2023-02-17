package com.epic.energyservices.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

  @Data
  @Embeddable
  @Builder
  @AllArgsConstructor 
  @NoArgsConstructor
  public class RegisteredOffice{
		
		private String adress;
		private String streetNumber;
		private String locality;
		@Column(length = 5)
		private int postalCode;
		@ManyToOne(cascade = CascadeType.ALL)
		private City city;
  
  }
 
