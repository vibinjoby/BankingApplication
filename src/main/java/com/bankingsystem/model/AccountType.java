package com.bankingsystem.model;

public class AccountType {
	private AccountDetails savingsAccount;
	private AccountDetails chequingAccount;
	private AccountDetails studentAccount;

	public AccountType(AccountDetails savingsAccount, AccountDetails chequingAccount, AccountDetails studentAccount) {
		super();
		this.savingsAccount = savingsAccount;
		this.chequingAccount = chequingAccount;
		this.studentAccount = studentAccount;
	}

	public AccountDetails getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(AccountDetails savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

	public AccountDetails getChequingAccount() {
		return chequingAccount;
	}

	public void setChequingAccount(AccountDetails chequingAccount) {
		this.chequingAccount = chequingAccount;
	}

	public AccountDetails getStudentAccount() {
		return studentAccount;
	}

	public void setStudentAccount(AccountDetails studentAccount) {
		this.studentAccount = studentAccount;
	}

	@Override
	public String toString() {
		return "AccountType [savingsAccount=" + savingsAccount + ", chequingAccount=" + chequingAccount
				+ ", studentAccount=" + studentAccount + "]";
	}

}
