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
		if(checkEmailExists(customerDetails.getCustomerEmail()))
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

}
