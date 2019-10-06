package com.bankingsystem.constants;

public interface ErrorConstants {
	public static final String errorCode_001 = "001";
	public static final String errorCode_001_message = "Email";
	public static final String errorCode_001_description = "Email Already Exists";
	
	public static final String errorCode_002 = "002";
	public static final String errorCode_002_message = "Exception";
	
	public static final String errorCode_003 = "003";
	public static final String errorCode_003_message = "Credit Card";
	public static final String errorCode_003_description = "Card already Active";
	
	public static final String errorCode_004 = "004";
	public static final String errorCode_004_message = "Insufficient Balance";
	public static final String errorCode_004_description = "You do not have sufficient balance to perform this transaction";
	
	public static final String errorCode_005 = "005";
	public static final String errorCode_005_message = "Credit Card";
	public static final String errorCode_005_description = "Invalid Card..Please check the card number";
	
	public static final String errorCode_006 = "006";
	public static final String errorCode_006_message = "Invalid Email";
	public static final String errorCode_006_description = "Email does not exist in the Bank";
	
	public static final String errorCode_007 = "007";
	public static final String errorCode_007_message = "Incorrect From and To Account";
	public static final String errorCode_007_description = "The From and To account cannot be the same!!";
	
	public static final String errorCode_008 = "008";
	public static final String errorCode_008_message = "Invalid Email";
	public static final String errorCode_008_description = "From and To Email cannot be the same!!";
	
	public static final String errorCode_009 = "009";
	public static final String errorCode_009_message = "Invalid Inputs";
	public static final String errorCode_009_description = "Please enter a valid number for payment!!";

}
