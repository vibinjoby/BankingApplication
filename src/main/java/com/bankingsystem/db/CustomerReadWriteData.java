package com.bankingsystem.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.ErrorDetails;
import com.bankingsystem.model.FileDetails;
import com.bankingsystem.util.BackEndUtils;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class CustomerReadWriteData extends BackEndUtils {

	public static List<CustomerDetails> customerDetailsList;
	static {
		customerDetailsList = readJsonFields();
	}

	public static ErrorDetails addNewCustomer(CustomerDetails customerDetails) {
		if (checkEmailExists(customerDetails.getCustomerEmail()))
			return new ErrorDetails("001", "Email", "Email Already Exists");
		String fileName = customerDetails.getName().getFirstName() + customerDetails.getCustomerId().substring(0, 4);
		FileDetails fileDetails = new FileDetails("C:\\Users\\vibin\\Customer_Data\\" + fileName + ".txt",
				"C:\\Users\\vibin\\Customer_Data\\" + fileName + ".txt");
		try {
			writeIntoJsonFile(fileDetails, customerDetails);
			updateJsonFields(fileDetails, customerDetails);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorDetails("002", "Exception", e.getMessage());
		}
	}

	/**
	 * @param filename
	 * @throws Exception https://stackabuse.com/reading-and-writing-json-in-java/
	 */
	public static void writeIntoJsonFile(FileDetails fileDetails, CustomerDetails customerDetails) throws Exception {
		createNewFileForCustomer(fileDetails);
		String json = getJsonObjAsString(customerDetails);
		Files.write(Paths.get(fileDetails.getWriteFileTo()), toPrettyFormat(json).getBytes());
	}

	/**
	 * @param filename
	 * @throws Exception https://stackabuse.com/reading-and-writing-json-in-java/
	 */
	public static void updateJsonFields(FileDetails fileDetails, CustomerDetails customerDetailsUpdatedObj)
			throws JSONException, IOException {
		String json = getJsonObjAsString(customerDetailsUpdatedObj);
		FileWriter file = null;
		try {
			file = new FileWriter(fileDetails.getWriteFileTo());
			file.write(toPrettyFormat(json));
		} finally {
			if (file != null)
				file.close();
		}
	}

	/**
	 * @return
	 * @throws FileNotFoundException https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
	 */
	public static List<CustomerDetails> readJsonFields() {
		List<CustomerDetails> customerDetailsList = null;
		try {
			customerDetailsList = new ArrayList<CustomerDetails>();
			final File folder = new File("C:\\Users\\vibin\\Customer_Data\\");
			List<File> fileList = new ArrayList<File>();
			fileList = listFilesForFolder(folder, fileList);
			Gson gson = new Gson();

			for (File f : fileList) {
				JsonReader reader = new JsonReader(new FileReader(f.getAbsolutePath()));
				CustomerDetails custDetails = gson.fromJson(reader, CustomerDetails.class);
				customerDetailsList.add(custDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDetailsList;
	}

	/**
	 * @param transactionAmt
	 * @param custId
	 * @param acctType
	 * @param operation
	 * @return
	 */
	public static ErrorDetails makeTransaction(String transactionAmt, String custId, String acctType,
			String operation) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteData.customerDetailsList;
		ErrorDetails errorDetails = null;
		CustomerDetails updatedCustomerDetails = null;
		if (custDetailsList != null) {
			for (CustomerDetails cust : custDetailsList) {
				int index = custDetailsList.indexOf(cust);
				if (custId.equalsIgnoreCase(cust.getCustomerId())) {
					if (cust.isChequingAcc() && "CHEQUING".equalsIgnoreCase(acctType)) {
						String accBalance = cust.getAccountType().getChequingAccount().getAccountBalance();
						if (checkSufficientBalance(accBalance, transactionAmt, operation)) {
							double updatedBalance = updateBalance(accBalance, transactionAmt, operation);
							cust.getAccountType().getChequingAccount()
									.setAccountBalance(String.valueOf(updatedBalance));
						} else {
							errorDetails = new ErrorDetails("004", "Insufficient Balance",
									"You do not have sufficient balance to perform this transaction");
						}
					} else if (cust.isSavingsAcc() && "SAVINGS".equalsIgnoreCase(acctType)) {
						String accBalance = cust.getAccountType().getSavingsAccount().getAccountBalance();
						if (checkSufficientBalance(accBalance, transactionAmt, operation)) {
							double updatedBalance = updateBalance(accBalance, transactionAmt, operation);
							cust.getAccountType().getSavingsAccount().setAccountBalance(String.valueOf(updatedBalance));
						} else {
							errorDetails = new ErrorDetails("004", "Insufficient Balance",
									"You do not have sufficient balance to perform this transaction");
						}
					} else if (cust.isStudentAcc() && "STUDENT".equalsIgnoreCase(acctType)) {
						String accBalance = cust.getAccountType().getStudentAccount().getAccountBalance();
						if (checkSufficientBalance(accBalance, transactionAmt, operation)) {
							double updatedBalance = updateBalance(accBalance, transactionAmt, operation);
							cust.getAccountType().getStudentAccount().setAccountBalance(String.valueOf(updatedBalance));
						} else {
							errorDetails = new ErrorDetails("004", "Insufficient Balance",
									"You do not have sufficient balance to perform this transaction");
						}
					}
					custDetailsList.set(index, cust);
					updatedCustomerDetails = cust;
				}
			}
		}

		if (updatedCustomerDetails != null) {
			updateCustomerDetailsInFile(updatedCustomerDetails);
		}

		return errorDetails;
	}

	/**
	 * @param debitCard
	 * @return
	 */
	public static CustomerDetails getCustDetailsUsingDebitCard(String debitCard, String mothMaidenName,
			String emailId) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteData.customerDetailsList;
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
	 * @param debitCard
	 * @return
	 */
	public static CustomerDetails getCustDetailsUsingEmail(String emailId) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteData.customerDetailsList;
		if (custDetailsList != null) {
			for (CustomerDetails cust : custDetailsList) {
				if (emailId.equalsIgnoreCase(cust.getCustomerEmail())) {
					return cust;
				}
			}
		}
		return null;
	}

	/**
	 * @param creditCardNumber
	 * @return
	 */
	public static ErrorDetails activateCreditCard(String creditCardNumber, String mothMaidenName) {
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
						updatedCustomerDetails = cust;
						cust.getCreditCardDetails().setActive(true);
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
			return new ErrorDetails("005", "Credit Card", "Invalid Card..Please check the card number");
		}
		if (isCardActive) {
			return new ErrorDetails("003", "Credit Card", "Card already Active");
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
	 */
	public static ErrorDetails interacMoneyTransfer(String toName, String toEmail, CustomerDetails customerInfo,
			String payAmt, String acctType) {
		ErrorDetails error = null;
		// Make sure there is no self transfer using email via Interac
		if (!toEmail.equalsIgnoreCase(customerInfo.getCustomerEmail())) {
			if (checkEmailExists(toEmail)) {
				try {
					Double.parseDouble(payAmt);
				} catch(NumberFormatException e) {
					e.printStackTrace();
					return new ErrorDetails("009", "Invalid Inputs", "Please enter a valid number for payment!!");
				}
				CustomerDetails recepientCustInfo = getCustDetailsUsingEmail(toEmail);
				error = makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, "");
				if (error == null) {
					// Add the amount in the recepient's account
					if (recepientCustInfo.isChequingAcc())
						acctType = "CHEQUING";
					else if (recepientCustInfo.isSavingsAcc())
						acctType = "SAVINGS";
					else
						acctType = "STUDENT";
					error = makeTransaction(payAmt, recepientCustInfo.getCustomerId(), acctType, "ADD");
				}
			} else {
				error = new ErrorDetails("006", "Invalid Email", "Email does not exist in the Bank");
			}
		} else {
			error = new ErrorDetails("008", "Invalid Email", "From and To Email cannot be the same!!");
		}
		return error;
	}

	/**
	 * @param customerInfo
	 * @param payAmt
	 * @param acctType
	 * @return
	 */
	public static ErrorDetails depositMoney(CustomerDetails customerInfo, String payAmt, String acctType) {
		return makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, "ADD");
	}

	/**
	 * @param customerInfo
	 * @param payAmt
	 * @param acctType
	 * @return
	 */
	public static ErrorDetails withdrawMoney(CustomerDetails customerInfo, String payAmt, String acctType) {
		return makeTransaction(payAmt, customerInfo.getCustomerId(), acctType, "");
	}

	/**
	 * @param customerInfo
	 * @param payAmt
	 * @param fromAcct
	 * @param toAcct
	 * @return
	 */
	public static ErrorDetails transferBtwnAccts(CustomerDetails customerInfo, String payAmt, String fromAcct,
			String toAcct) {
		ErrorDetails errorDetails = null;
		if (!fromAcct.equalsIgnoreCase(toAcct)) {
			try {
				Double.parseDouble(payAmt);
			} catch(NumberFormatException e) {
				e.printStackTrace();
				return new ErrorDetails("009", "Invalid Inputs", "Please enter a valid number for payment!!");
			}
			// Transfer from Account
			errorDetails = makeTransaction(payAmt, customerInfo.getCustomerId(), fromAcct, "");
			if (errorDetails == null) {
				// Transfer to Account
				errorDetails = makeTransaction(payAmt, customerInfo.getCustomerId(), toAcct, "ADD");
			}
		} else {
			return new ErrorDetails("007", "Incorrect From and To Account", "The From and To account cannot be same!!");
		}
		return errorDetails;
	}

}
