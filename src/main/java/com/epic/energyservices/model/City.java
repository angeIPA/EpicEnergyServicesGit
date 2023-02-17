package com.epic.energyservices.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

public class City extends BaseEntity {
	private String name;
	private boolean capital;
	@ManyToOne(cascade = CascadeType.ALL)
	private Province province;
	
}
