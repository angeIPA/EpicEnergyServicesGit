package com.epic.energyservices.model;

import javax.persistence.Entity;

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
public class InvoiceStatus extends BaseEntity{

	private String invoiceStatus;
	/*
	 * sent
	 * paid
	 * overdue
	 */
}
