package com.bankingsystem.model;

public class AccountStatement {
	private String txnDescription;
	private String txnAcct;
	private String txnAmt;
	
	public AccountStatement(String txnDescription, String txnAcct, String txnAmt) {
		super();
		this.txnDescription = txnDescription;
		this.txnAcct = txnAcct;
		this.txnAmt = txnAmt;
	}

	public String getTxnDescription() {
		return txnDescription;
	}

	public void setTxnDescription(String txnDescription) {
		this.txnDescription = txnDescription;
	}

	public String getTxnAcct() {
		return txnAcct;
	}

	public void setTxnAcct(String txnAcct) {
		this.txnAcct = txnAcct;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	@Override
	public String toString() {
		return "AccountStatement [txnDescription=" + txnDescription + ", txnAcct=" + txnAcct + ", txnAmt=" + txnAmt
				+ "]";
	}
	

}
