package com.supportdesk.exception;

public class ApplicationRunTimeException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	private String errorCode;

	/**
	 * This is a parameterised constructor for the RunTimeException class. It expects a String (error code) as
	 * argument.
	 * 
	 * @param errorCode Error code for this Exception
	 */
	public ApplicationRunTimeException(String errorCode)
	{
		super();
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

}

