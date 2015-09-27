package com.spilna.sprava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description of the table "message"
 * 
 * @author Ivanov Eduard
 * @version 1.0
 */

//This annotation, we specify that the class is an entity.
@Entity
/**
 * This annotation, we point out that for this
 * entity in the database corresponds to a table named 'post'
 */
@Table(name = "post")
public class PostInf {

	@Id// Id describe table
	@Column(name = "id")
	private String id;
	@Column(name = "id_post")// Definition of the column name
	private String idPost;

	@Column(name = "id_user")
	private String idUser;

	@Column(name = "message")
	private String post;

	// implementation of methods getters and setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String id) {
		this.idPost = id;
	}
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

}