package org.wickedsource;

import java.io.Serializable;

public class Account implements Serializable{

	private long id;

	private String holder;

	private double balance;

	private double overdraft;

	public Account(Long id, String holder, double balance, double overdraft) {
		this.id = id;
		this.holder = holder;
		this.balance = balance;
		this.overdraft = overdraft;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	public void credit(double amount) {
		this.balance += amount;
	}

}
