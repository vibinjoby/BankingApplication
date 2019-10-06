package com.bankingsystem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.bankingsystem.constants.BankingConstants;
import com.bankingsystem.db.CustomerReadWriteDataImpl;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.ErrorDetails;
import com.bankingsystem.model.FileDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class BackEndUtils implements BankingConstants {
	/**
	 * @param jsonString
	 * @return https://coderwall.com/p/ab5qha/convert-json-string-to-pretty-print-java-gson
	 */
	public static String toPrettyFormat(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);

		return prettyJson;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public File getFileFromResources(String fileName) {

		ClassLoader classLoader = getClass().getClassLoader();

		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException(file_not_found);
		} else {
			return new File(resource.getFile());
		}

	}

	/**
	 * @param fileDetails
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void createNewFileForCustomer(FileDetails fileDetails)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileDetails.getWriteFileTo(), UTF8);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * @param customerDetails
	 * @return
	 */
	public static String getJsonObjAsString(Object customerDetails) {
		Gson gson = new Gson();
		return gson.toJson(customerDetails);
	}

	/**
	 * @param folder
	 * @param fileList
	 * @return
	 */
	public static List<File> listFilesForFolder(final File folder, List<File> fileList) {
		if (folder.listFiles() != null)
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory())
					listFilesForFolder(fileEntry, fileList);
				else
					fileList.add(fileEntry);
			}
		return fileList;
	}

	/**
	 * @param email
	 * @return
	 */
	public static boolean checkEmailExists(String email) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteDataImpl.customerDetailsList;
		if (custDetailsList != null) {
			for (CustomerDetails cust : custDetailsList) {
				if (email.equalsIgnoreCase(cust.getCustomerEmail())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param custDetails
	 * @param mothMaidenName
	 * @param emailId
	 * @return
	 */
	public static boolean validateCustDetails(CustomerDetails custDetails, String mothMaidenName, String emailId) {
		if (custDetails.getPersonalDetails() != null) {
			if (mothMaidenName.equalsIgnoreCase(custDetails.getPersonalDetails().getMotherMaidenDetails())
					&& emailId.equalsIgnoreCase(custDetails.getCustomerEmail())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param accBalance
	 * @param transactionAmt
	 * @param operation
	 * @return
	 */
	public static double updateBalance(String accBalance, String transactionAmt, String operation) {
		if (operation_ADD.equalsIgnoreCase(operation))
			return Double.parseDouble(accBalance) + Double.parseDouble(transactionAmt);

		return Double.parseDouble(accBalance) - Double.parseDouble(transactionAmt);
	}

	/**
	 * @param accBalance
	 * @param transactionAmt
	 * @return
	 */
	public static boolean checkSufficientBalance(String accBalance, String transactionAmt, String operation) {
		try {
			if (operation_ADD.equalsIgnoreCase(operation)) {
				return true;
			}
			if (accBalance != null && transactionAmt != null) {
				if (Double.parseDouble(accBalance) > Double.parseDouble(transactionAmt)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param updatedCustomerDetails
	 */
	public static void updateCustomerDetailsInFile(CustomerDetails updatedCustomerDetails) {
		String fileName = updatedCustomerDetails.getName().getFirstName()
				+ updatedCustomerDetails.getCustomerId().substring(0, 4);
		FileDetails fileDetails = new FileDetails(folderPath + fileName + fileExtension,
				folderPath + fileName + fileExtension);
		try {
			String json = getJsonObjAsString(updatedCustomerDetails);
			Files.write(Paths.get(fileDetails.getWriteFileTo()), toPrettyFormat(json).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param debitCard
	 * @return
	 */
	public static CustomerDetails getCustDetailsUsingEmail(String emailId) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteDataImpl.customerDetailsList;
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
	 * @param transactionAmt
	 * @param custId
	 * @param acctType
	 * @param operation
	 * @return
	 */
	public static ErrorDetails makeTransaction(String transactionAmt, String custId, String acctType, String operation,
			String txnDescription) {
		List<CustomerDetails> custDetailsList = CustomerReadWriteDataImpl.customerDetailsList;
		ErrorDetails errorDetails = null;
		boolean isError = false;
		CustomerDetails updatedCustomerDetails = null;
		if (custDetailsList != null) {
			for (CustomerDetails cust : custDetailsList) {
				int index = custDetailsList.indexOf(cust);
				if (custId.equalsIgnoreCase(cust.getCustomerId())) {
					if (cust.isChequingAcc() && chequing.equalsIgnoreCase(acctType)) {
						String accBalance = cust.getAccountType().getChequingAccount().getAccountBalance();
						if (checkSufficientBalance(accBalance, transactionAmt, operation)) {
							double updatedBalance = updateBalance(accBalance, transactionAmt, operation);
							cust.getAccountType().getChequingAccount()
									.setAccountBalance(String.valueOf(updatedBalance));
							isError = false;
						} else {
							isError = true;
							errorDetails = new ErrorDetails(errorCode_004, errorCode_004_message,
									errorCode_004_description);
						}
					} else if (cust.isSavingsAcc() && savings.equalsIgnoreCase(acctType)) {
						String accBalance = cust.getAccountType().getSavingsAccount().getAccountBalance();
						if (checkSufficientBalance(accBalance, transactionAmt, operation)) {
							double updatedBalance = updateBalance(accBalance, transactionAmt, operation);
							cust.getAccountType().getSavingsAccount().setAccountBalance(String.valueOf(updatedBalance));
							isError = false;
						} else {
							isError = true;
							errorDetails = new ErrorDetails(errorCode_004, errorCode_004_message,
									errorCode_004_description);
						}
					} else if (cust.isStudentAcc() && student.equalsIgnoreCase(acctType)) {
						String accBalance = cust.getAccountType().getStudentAccount().getAccountBalance();
						if (checkSufficientBalance(accBalance, transactionAmt, operation)) {
							double updatedBalance = updateBalance(accBalance, transactionAmt, operation);
							cust.getAccountType().getStudentAccount().setAccountBalance(String.valueOf(updatedBalance));
							isError = false;
						} else {
							isError = true;
							errorDetails = new ErrorDetails(errorCode_004, errorCode_004_message,
									errorCode_004_description);
						}
					}
					if(!isError) {
						if (cust.getAccountStatement() != null && !txnDescription.isEmpty()) {
							cust.getAccountStatement().add(txnDescription + ":" + acctType + ":" + transactionAmt);
						} else {
							if (!txnDescription.isEmpty()) {
								List<String> stmlList = new ArrayList<String>();
								stmlList.add(txnDescription + ":" + acctType + ":" + transactionAmt);
								cust.setAccountStatement(stmlList);
							}
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
			final File folder = new File(folderPath);
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
}
