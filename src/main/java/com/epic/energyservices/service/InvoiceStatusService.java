package com.epic.energyservices.service;

import com.epic.energyservices.model.InvoiceStatus;

public interface InvoiceStatusService {
	
	public int invoiceStatusId (String invoiceStatus);

	public InvoiceStatus addInvoiceStatus (InvoiceStatus invoiceStatus);
}
