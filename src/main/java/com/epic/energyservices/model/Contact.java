package com.epic.energyservices.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	private String contactEmail;
	private String contactName;
	private String contactSurname;
	private long contactPhone;
}
