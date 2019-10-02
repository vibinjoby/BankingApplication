package com.bankingsystem.db;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.FileDetails;
import com.bankingsystem.util.BankingUtil;

public class CustomerReadWriteData extends BankingUtil {
	public static boolean addNewCustomer(CustomerDetails customerDetails) {
		String fileName = customerDetails.getName().getFirstName() + customerDetails.getCustomerId().substring(0, 4);
		FileDetails fileDetails = new FileDetails("C:\\Users\\vibin\\"+fileName+".txt","C:\\Users\\vibin\\"+fileName+".txt");
		try {
			writeIntoJsonFile(fileDetails, customerDetails);
			updateJsonFields(fileDetails, customerDetails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

}
