package com.supportdesk.common;

public class ErrorCodeConstants {
	
	public final static String ERROR_CODE_SYSTEM_EXCEPTION = "E0001";
	public final static String ERROR_CODE_BASE_EXCEPTION = "E0002";
	public static final String ERROR_CODE_SERVICE_EXCEPTION = "E0003";
	public static final String ERROR_CODE_DAO_EXCEPTION = "E0004";
	public static final String ERROR_CODE_DB_EXCEPTION = "E0005";
	
	//Email Component;
	public static final String E0005 = "E0005";
	public static final String E0005_MSG = "MailType should not be null or blank.";
	public static final String E0006 = "E0006";
	public static final String E0006_MSG = "Email Language Id should not be null or blank.";
	public static final String E0007 = "E0007";
	public static final String E0007_MSG = "Email Issue. Unable to Send Email.";
	public static final String E0008 = "E0008";
	public static final String E0008_MSG = "Unable to Connect Database. Please check connectivity or Database Authentication Details.";
	public static final String E0009 = "E0009";
	public static final String E0009_MSG = "Email Template not found.";
	public static final String E0010 = "E0010";
	public static final String E0010_MSG = "Email sent partially. There are some SMTP errors";
	
}
