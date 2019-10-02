package com.bankingsystem.model;

public class ApplicationProperties {
	private String customerId;
	private String accNumber;
	private String debitCardStartNumber;
	private String creditCardStartNumber;

	public ApplicationProperties(String customerId, String accNumber, String debitCardStartNumber,
			String creditCardStartNumber) {
		super();
		this.customerId = customerId;
		this.accNumber = accNumber;
		this.debitCardStartNumber = debitCardStartNumber;
		this.creditCardStartNumber = creditCardStartNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getDebitCardStartNumber() {
		return debitCardStartNumber;
	}

	public void setDebitCardStartNumber(String debitCardStartNumber) {
		this.debitCardStartNumber = debitCardStartNumber;
	}

	public String getCreditCardStartNumber() {
		return creditCardStartNumber;
	}

	public void setCreditCardStartNumber(String creditCardStartNumber) {
		this.creditCardStartNumber = creditCardStartNumber;
	}

	@Override
	public String toString() {
		return "ApplicationProperties [customerId=" + customerId + ", accNumber=" + accNumber
				+ ", debitCardStartNumber=" + debitCardStartNumber + ", creditCardStartNumber=" + creditCardStartNumber
				+ "]";
	}

}
