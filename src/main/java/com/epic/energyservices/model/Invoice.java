package com.epic.energyservices.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

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
@NamedQuery(name = "Invoice.getSum", query = "select sum(i.amount) from Invoice i where i.client.id=:clientId and i.year=:year")
@NamedQuery(name = "Invoice.getAmountByYear", query = "SELECT i.client.id, SUM(i.amount) AS amount FROM Invoice i WHERE i.year=:year GROUP BY client")
//
public class Invoice extends BaseEntity{
	
	

	private BigDecimal amount;
	private int number;
	@ManyToOne
	private Client client;
	@ManyToOne(cascade = CascadeType.ALL)
	private InvoiceStatus invoiceStatus;
	
	private int year;
}
