package com.epic.energyservices.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
	
	//commento per modifica git 

	//secondo commento git
	//ciao
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column( //
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", //
			insertable = false, //
			updatable = false)
	@JsonFormat(pattern="yyyy/MM/dd")
	private Date createdAt;
	

}
