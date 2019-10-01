package com.bankingsystem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.FileDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BankingUtil {
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
	public static String getJsonObjAsString(CustomerDetails customerDetails) {
		Gson gson = new Gson();
		return gson.toJson(customerDetails);
	}

}
