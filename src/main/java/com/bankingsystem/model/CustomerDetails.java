package com.bankingsystem.model;

public class CustomerDetails {
	private String customerId;
	private CustomerName name;
	private String accountNumber;
	private String customerEmail;
	private String dateOfBirth;
	private PersonalDetails personalDetails;
	private AccountType accountType;
	private CardDetails debitCardDetails;
	private CardDetails creditCardDetails;
	private boolean isSavingsAcc;
	private boolean isChequingAcc;
	private boolean isStudentAcc;

	public CustomerDetails(String customerId, CustomerName name, String accountNumber, String customerEmail,
			String dateOfBirth, PersonalDetails personalDetails, AccountType accountType, CardDetails debitCardDetails,
			CardDetails creditCardDetails, boolean isSavingsAcc, boolean isChequingAcc, boolean isStudentAcc) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.accountNumber = accountNumber;
		this.customerEmail = customerEmail;
		this.dateOfBirth = dateOfBirth;
		this.personalDetails = personalDetails;
		this.accountType = accountType;
		this.debitCardDetails = debitCardDetails;
		this.creditCardDetails = creditCardDetails;
		this.isSavingsAcc = isSavingsAcc;
		this.isChequingAcc = isChequingAcc;
		this.isStudentAcc = isStudentAcc;
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

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public boolean isSavingsAcc() {
		return isSavingsAcc;
	}

	public void setSavingsAcc(boolean isSavingsAcc) {
		this.isSavingsAcc = isSavingsAcc;
	}

	public boolean isChequingAcc() {
		return isChequingAcc;
	}

	public void setChequingAcc(boolean isChequingAcc) {
		this.isChequingAcc = isChequingAcc;
	}

	public boolean isStudentAcc() {
		return isStudentAcc;
	}

	public void setStudentAcc(boolean isStudentAcc) {
		this.isStudentAcc = isStudentAcc;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "CustomerDetails [customerId=" + customerId + ", name=" + name + ", accountNumber=" + accountNumber
				+ ", customerEmail=" + customerEmail + ", dateOfBirth=" + dateOfBirth + ", personalDetails="
				+ personalDetails + ", accountType=" + accountType + ", debitCardDetails=" + debitCardDetails
				+ ", creditCardDetails=" + creditCardDetails + ", isSavingsAcc=" + isSavingsAcc + ", isChequingAcc="
				+ isChequingAcc + ", isStudentAcc=" + isStudentAcc + "]";
	}

}
