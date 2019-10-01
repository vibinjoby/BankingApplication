package com.bankingsystem.model;

public class PersonalDetails {
	private String motherMaidenDetails;
	private String sinNumber;
	private String pinNumber;

	public PersonalDetails(String motherMaidenDetails, String sinNumber, String pinNumber) {
		super();
		this.motherMaidenDetails = motherMaidenDetails;
		this.sinNumber = sinNumber;
		this.pinNumber = pinNumber;
	}

	public String getMotherMaidenDetails() {
		return motherMaidenDetails;
	}

	public void setMotherMaidenDetails(String motherMaidenDetails) {
		this.motherMaidenDetails = motherMaidenDetails;
	}

	public String getSinNumber() {
		return sinNumber;
	}

	public void setSinNumber(String sinNumber) {
		this.sinNumber = sinNumber;
	}

	public String getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}

	@Override
	public String toString() {
		return "PersonalDetails [motherMaidenDetails=" + motherMaidenDetails + ", sinNumber=" + sinNumber
				+ ", pinNumber=" + pinNumber + "]";
	}

}
