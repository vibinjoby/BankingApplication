package com.bankingsystem.model;

public class CardDetails {
	private String cardNumber;
	private String cardExpiryDate;
	private String cvv;
	private boolean isActive;

	public CardDetails(String cardNumber, String cardExpiryDate, String cvv, boolean isActive) {
		super();
		this.cardNumber = cardNumber;
		this.cardExpiryDate = cardExpiryDate;
		this.cvv = cvv;
		this.isActive = isActive;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "CardDetails [cardNumber=" + cardNumber + ", cardExpiryDate=" + cardExpiryDate + ", cvv=" + cvv
				+ ", isActive=" + isActive + "]";
	}

}
