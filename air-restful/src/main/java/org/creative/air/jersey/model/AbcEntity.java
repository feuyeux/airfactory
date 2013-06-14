package org.creative.air.jersey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JPA Entity Bean
 * 
 * @author feuyeux@gmail.com
 * 05/08/2012
 * @version 0.1.0
 * @since 0.0.1
 */
@Entity
@Table(name = "abc")
public class AbcEntity implements Serializable {
	private static final long serialVersionUID = 7413531871973759206L;
	private Integer id;
	private String name;
	private String value;

	public AbcEntity() {
	}

	public AbcEntity(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "EMP_SEQ")
	@SequenceGenerator(name = "EMP_SEQ")
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 45)
	public String getName() {
		return name;
	}

	@Column(length = 45)
	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + "id = " + id + ", " + "name = " + name + ", " + "value = " + value + "]";
	}
}
