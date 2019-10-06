package com.bankingsystem.db;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.ErrorDetails;

public interface CustomerWriteData {
	/**
	 * Method to Add a new Customer to the file
	 */
	public ErrorDetails addNewCustomer(CustomerDetails customerDetails) ;

}
