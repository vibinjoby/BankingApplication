package com.bankingsystem.db;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.ErrorDetails;

public interface CustomerReadData extends CustomerWriteData {
	/**
	 * Method to get customer details using debit card number
	 */
	public CustomerDetails getCustDetailsUsingDebitCard(String debitCard, String mothMaidenName, String emailId);

	/**
	 * Method to activate credit using card number and mother's maiden name
	 */
	public ErrorDetails activateCreditCard(String creditCardNumber, String mothMaidenName);

	/**
	 * Method to do interac money transfer option
	 */
	public ErrorDetails interacMoneyTransfer(String toName, String toEmail, CustomerDetails customerInfo, String payAmt,
			String acctType);

	/**
	 * Method to deposit money into their account
	 */
	public ErrorDetails depositMoney(CustomerDetails customerInfo, String payAmt, String acctType);

	/**
	 * Method to withdraw money from their account
	 */
	public ErrorDetails withdrawMoney(CustomerDetails customerInfo, String payAmt, String acctType);

	/**
	 * Method to transfer between their accounts
	 */
	public ErrorDetails transferBtwnAccts(CustomerDetails customerInfo, String payAmt, String fromAcct, String toAcct);

	/**
	 * Method to pay utilty bills including mobile and hydro
	 */
	public ErrorDetails payUtilitiesBill(CustomerDetails customerInfo, String payAmt, String acctType);

}
