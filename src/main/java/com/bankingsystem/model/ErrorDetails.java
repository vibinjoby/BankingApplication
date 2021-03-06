package com.bankingsystem.model;

public class ErrorDetails {
	private String errorCode;
	private String errorMessage;
	private String errorDescription;

	public ErrorDetails(String errorCode, String errorMessage, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		return "ErrorDetails [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", errorDescription="
				+ errorDescription + "]";
	};

}
