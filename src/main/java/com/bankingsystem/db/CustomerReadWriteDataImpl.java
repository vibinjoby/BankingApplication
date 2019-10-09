package com.bankingsystem.db;

import java.util.List;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.ErrorDetails;
import com.bankingsystem.model.FileDetails;
import com.bankingsystem.util.BackEndUtils;

public class CustomerReadWriteDataImpl extends BackEndUtils implements CustomerReadData {

	public static List<CustomerDetails> customerDetailsList;
	static {
		customerDetailsList = readJsonFields();
	}

	/**
	 * Method to Add a new Customer to the file
	 */
	@Override
	public ErrorDetails addNewCustomer(CustomerDetails customerDetails) {
		if (checkEmailExists(customerDetails.getCustomerEmail()))
			return new ErrorDetails(errorCode_001, errorCode_001_message, errorCode_001_description);
		else if (checkSinNumberExists(customerDetails.getPersonalDetails().getSinNumber())) {
			return new ErrorDetails(errorCode_010, errorCode_010_message, errorCode_010_description);
		}
		String fileName = customerDetails.getName().getFirstName() + customerDetails.getCustomerId().substring(0, 4);
		FileDetails fileDetails = new FileDetails(folderPath + fileName + fileExtension,
				folderPath + fileName + fileExtension);
		try {
			writeIntoJsonFile(fileDetails, customerDetails);
			updateJsonFields(fileDetails, customerDetails);
			customerDetailsList = readJsonFields();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorDetails(errorCode_002, errorCode_002_message, e.getMessage());
		}
	}

	/**
	 * @param debitCard
	 * @return
	 */
	@Override
	public CustomerDetails getCustDetailsUsingDebitCard(String debitCard, String mothMaidenName, String emailId) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteDataImpl.customerDetailsList;
		if (custDetailsList != null) {
			for (CustomerDetails cust : custDetailsList) {
				if (validateCustDetails(cust, mothMaidenName, emailId)) {
					if (debitCard.equalsIgnoreCase(cust.getDebitCardDetails().getCardNumber())) {
						return cust;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param creditCardNumber
	 * @return
	 * Method to activate credit using card number and mother's maiden name
	 */
	@Override
	public ErrorDetails activateCreditCard(String creditCardNumber, String mothMaidenName) {
		boolean isCardActive = false;
		boolean isCardFound = false;
		boolean isUpdateReq = false;
		CustomerDetails updatedCustomerDetails = null;
		if (customerDetailsList != null) {
			for (CustomerDetails cust : customerDetailsList) {
				if (cust.getCreditCardDetails() != null
						&& creditCardNumber.equalsIgnoreCase(cust.getCreditCardDetails().getCardNumber())) {
					if (cust.getPersonalDetails() != null && !cust.getCreditCardDetails().isActive()
							&& mothMaidenName.equalsIgnoreCase(cust.getPersonalDetails().getMotherMaidenDetails())) {
						isCardFound = true;
						isUpdateReq = true;
						cust.getCreditCardDetails().setActive(true);
						updatedCustomerDetails = cust;
					} else if (creditCardNumber.equalsIgnoreCase(cust.getCreditCardDetails().getCardNumber())
							&& cust.getCreditCardDetails().isActive()) {
						isCardActive = true;
						isCardFound = true;
					}
				}
			}
		}
		if (isUpdateReq) {
			updateCustomerDetailsInFile(updatedCustomerDetails);
		}
		if (!isCardFound) {
			return new ErrorDetails(errorCode_005, errorCode_005_message, errorCode_005_description);
		}
		if (isCardActive) {
			return new ErrorDetails(errorCode_003, errorCode_003_message, errorCode_003_description);
		}
		return null;
	}

	/**
	 * @param toName
	 * @param toEmail
	 * @param customerInfo
	 * @param payAmt
	 * @param acctType
	 * @return
	 * Method to do interac money transfer option
	 */
	@Override
	public ErrorDetails interacMoneyTransfer(String toName, String toEmail, CustomerDetails customerInfo, String payAmt,
			String acctType) {
		ErrorDetails error = null;
		// Make sure there is no self transfer using email via Interac
		if (!toEmail.equalsIgnoreCase(customerInfo.getCustomerEmail())) {
			if (checkEmailExists(toEmail)) {
				try {
					Double.parseDouble(payAmt);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					return new ErrorDetails(errorCode_009, errorCode_009_message, errorCode_009_description);
				}
				CustomerDetails recepientCustInfo = getCustDetailsUsingEmail(toEmail);
				error = makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, "", "Interac money from Account");
				if (error == null) {
					// Add the amount in the recepient's account
					if (recepientCustInfo.isChequingAcc())
						acctType = chequing;
					else if (recepientCustInfo.isSavingsAcc())
						acctType = savings;
					else
						acctType = student;
					error = makeTransaction(payAmt, recepientCustInfo.getCustomerId(), acctType, operation_ADD,"Interac Money Recieved to Account");
				}
			} else {
				error = new ErrorDetails(errorCode_006, errorCode_006_message, errorCode_006_description);
			}
		} else {
			error = new ErrorDetails(errorCode_008, errorCode_008_message, errorCode_008_description);
		}
		return error;
	}

	/**
	 * @param customerInfo
	 * @param payAmt
	 * @param acctType
	 * @return
	 * Method to deposit money into their account
	 */
	@Override
	public ErrorDetails depositMoney(CustomerDetails customerInfo, String payAmt, String acctType) {
		try {
			Double.parseDouble(payAmt);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ErrorDetails(errorCode_009, errorCode_009_message, errorCode_009_description);
		}
		return makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, operation_ADD,"Money deposited to Account");
	}

	/**
	 * @param customerInfo
	 * @param payAmt
	 * @param acctType
	 * @return
	 *  Method to withdraw money from their account
	 */
	@Override
	public ErrorDetails withdrawMoney(CustomerDetails customerInfo, String payAmt, String acctType) {
		try {
			Double.parseDouble(payAmt);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ErrorDetails(errorCode_009, errorCode_009_message, errorCode_009_description);
		}
		return makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, "","Money withdrawn from Account");
	}

	/**
	 * @param customerInfo
	 * @param payAmt
	 * @param fromAcct
	 * @param toAcct
	 * @return
	 * Method to transfer between their accounts
	 */
	@Override
	public ErrorDetails transferBtwnAccts(CustomerDetails customerInfo, String payAmt, String fromAcct, String toAcct) {
		ErrorDetails errorDetails = null;
		if (!fromAcct.equalsIgnoreCase(toAcct)) {
			try {
				Double.parseDouble(payAmt);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return new ErrorDetails(errorCode_009, errorCode_009_message, errorCode_009_description);
			}
			// Transfer from Account
			errorDetails = makeTransaction(payAmt, customerInfo.getCustomerId(), fromAcct, "","Transfer between Accounts");
			if (errorDetails == null) {
				// Transfer to Account
				errorDetails = makeTransaction(payAmt, customerInfo.getCustomerId(), toAcct, operation_ADD,"");
			}
		} else {
			return new ErrorDetails(errorCode_007, errorCode_007_message, errorCode_007_description);
		}
		return errorDetails;
	}

	/**
	 *	Method to pay utilty bills including mobile and hydro
	 */
	@Override
	public ErrorDetails payUtilitiesBill(CustomerDetails customerInfo, String payAmt, String acctType) {
		try {
			Double.parseDouble(payAmt);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ErrorDetails(errorCode_009, errorCode_009_message, errorCode_009_description);
		}
		return makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, "","Paid utilities Bill");
	}

}
