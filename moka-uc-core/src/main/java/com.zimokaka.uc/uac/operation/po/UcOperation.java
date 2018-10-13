package com.zimokaka.uc.uac.operation.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: Operation
 *
 */
@Table(name="uc_operation")
@Entity
public class UcOperation implements Serializable {


	private int id;
	private String operationDesc;
	private String name;
	private String operation;
	private static final long serialVersionUID = 1L;

	public UcOperation() {
		super();
	}   
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	@Column(unique=true)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
   
}
