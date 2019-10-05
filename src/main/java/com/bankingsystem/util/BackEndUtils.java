package com.bankingsystem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.bankingsystem.db.CustomerReadWriteData;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.FileDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BackEndUtils {
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
			throw new IllegalArgumentException("file is not found!");
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
			writer = new PrintWriter(fileDetails.getWriteFileTo(), "UTF-8");
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
		List<CustomerDetails> custDetailsList = CustomerReadWriteData.customerDetailsList;
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
		if ("ADD".equalsIgnoreCase(operation))
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
			if ("ADD".equalsIgnoreCase(operation)) {
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
		FileDetails fileDetails = new FileDetails("C:\\Users\\vibin\\Customer_Data\\" + fileName + ".txt",
				"C:\\Users\\vibin\\Customer_Data\\" + fileName + ".txt");
		try {
			String json = getJsonObjAsString(updatedCustomerDetails);
			Files.write(Paths.get(fileDetails.getWriteFileTo()), toPrettyFormat(json).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
