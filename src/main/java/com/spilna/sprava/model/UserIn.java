package com.spilna.sprava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ivanov Eduard
 * @version 1.0
 */
// This annotation, we specify that the class is an entity.
@Entity
/**
 * This annotation, we point out that for this
 * entity in the database corresponds to a table named 'user'
 */
@Table(name = "user")
public class UserIn {
	@Id
	@Column(name = "id_user")
	private String idUser;
	@Column(name = "name")
	private String name;
	@Column(name = "token")
	private String token;


	
	public String getIdU() {
		return idUser;
	}

	public void setIdU(String idU) {
		this.idUser = idU;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
