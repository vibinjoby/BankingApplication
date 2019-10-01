package com.bankingsystem.model;

public class AccountDetails {
	private String accountNumber;
	private String accountBalance;

	public AccountDetails(String accountNumber, String accountBalance) {
		super();
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "AccountDetails [accountNumber=" + accountNumber + ", accountBalance=" + accountBalance + "]";
	}

}
