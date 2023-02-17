package com.epic.energyservices.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.InvoiceStatus;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

	//pageable
	public List<Invoice> findByClient(Client client);
	
	@Query(name = "getSum") //mostra fatturato annuale del cliente per l'anno scelto
	public BigDecimal getSum(int clientId, int year);
	
	@Query(name = "getAmountByYear") //mostra fatturato annuale del cliente per l'anno scelto
	public Map<Client , BigDecimal> getAmountByYear(int year);
	
	public Page<Invoice> findByClient(Client client, Pageable page);
	
	public Page<Invoice> findByInvoiceStatus(InvoiceStatus invoiceStatus, Pageable page);
	
//-----------------------------------------------------------------------	
	public Page<Invoice> findByCreatedAt(Date createdAt, Pageable page);
	
	public Page<Invoice> findByYear(int year, Pageable page); 
	
	
	//?
	public List<Invoice> findByAmount(BigDecimal amount);
	
	
	
	//servono per update
	public Invoice findByNumber(int number);
	public Invoice findByNumberAndClientId(int number, int clientIid);
	


//    public interface AmountByYear {
//        /**
//         * @return il cliente
//         */
//        Client getClient();
//        /**
//         * @return il fatturato
//         */
//        
//        BigDecimal getAmount();
//    }
//   /**
//     * Fatturato annuo per tutti i clienti.
//     * 
//     * @param year anno di riferimento.
//    */
//    @Query(value = "SELECT (SELECT c FROM Client c WHERE c.id = i.client.id) as client, "
//           + "SUM(i.amount) as amount FROM Invoice i WHERE i.year=:year GROUP BY i.client.id")
//    List<AmountByYear> getAmountByYear(int year);
//   
	
	//stessa query ma divide in pageable
	/*
	
    @Query(value = "SELECT (SELECT c FROM customers c WHERE c.id = i.customer.id) as customer, "
            + "SUM(i.amount) as amount FROM invoices i WHERE i.year=:year GROUP BY i.customer.id", 
            countQuery = "SELECT COUNT(*) FROM invoices i WHERE i.year=:year GROUP BY i.customer.id")
    Page<AmountByYear> getPagedAmountByYear(int year, Pageable pageable);
*/
	
	
	
	
}
