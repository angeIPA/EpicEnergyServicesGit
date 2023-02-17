package com.epic.energyservices.security;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.epic.energyservices.model.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity{

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name="auth_roles_generator", sequenceName = "auth_roles_seq")
//	private int id;
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	
	//@ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
	//private Set<Utente> utenti = new HashSet<Utente>();


	@Override
	public String toString() {
		return String.format("Role: id%d, roletype%s ", roleType);

	}

}