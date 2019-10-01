package com.bankingsystem.model;

public class CustomerDetails {
	private String customerId;
	private CustomerName name;
	private String accountNumber;
	private PersonalDetails personalDetails;
	private AccountType accountType;
	private CardDetails debitCardDetails;
	private CardDetails creditCardDetails;

	public CustomerDetails(String customerId, CustomerName name, String accountNumber, PersonalDetails personalDetails,
			AccountType accountType, CardDetails debitCardDetails, CardDetails creditCardDetails) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.accountNumber = accountNumber;
		this.personalDetails = personalDetails;
		this.accountType = accountType;
		this.debitCardDetails = debitCardDetails;
		this.creditCardDetails = creditCardDetails;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public CustomerName getName() {
		return name;
	}

	public void setName(CustomerName name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public PersonalDetails getPersonalDetails() {
		return personalDetails;
	}

	public void setPersonalDetails(PersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public CardDetails getDebitCardDetails() {
		return debitCardDetails;
	}

	public void setDebitCardDetails(CardDetails debitCardDetails) {
		this.debitCardDetails = debitCardDetails;
	}

	public CardDetails getCreditCardDetails() {
		return creditCardDetails;
	}

	public void setCreditCardDetails(CardDetails creditCardDetails) {
		this.creditCardDetails = creditCardDetails;
	}

	@Override
	public String toString() {
		return "CustomerDetails [customerId=" + customerId + ", name=" + name + ", accountNumber=" + accountNumber
				+ ", personalDetails=" + personalDetails + ", accountType=" + accountType + ", debitCardDetails="
				+ debitCardDetails + ", creditCardDetails=" + creditCardDetails + "]";
	}

}
