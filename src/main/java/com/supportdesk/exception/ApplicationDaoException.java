package com.supportdesk.exception;

/**
 * This is the Exception Class used in SUPPORT DESK Application.
 * 
 * @version 1.0
 * 
 */
public class ApplicationDaoException extends ApplicationBaseException 
{
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;
	
	
	/**
	 * This is a parameterised constructor for the DaoException class. It expects String (error code,errorMessage)
	 * and Throwable cause as arguments.
	 * 
	 * @param errorCode Error code for this Exception
	 * @param errorMessage Error Message for this Exception
	 * @param cause Error cause for this Exception
	 */
	public ApplicationDaoException(String errorCode,String errorMessage, Throwable cause)
	{
		super(errorCode,errorMessage,cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * This method is used to create Exception based on error code and error message.
	 *
	 * @version 1.0
	 * @param errorCode
	 * @param errorMessage
	 *
	 */
	public ApplicationDaoException(String errorCode,String errorMessage)
	{
		super(errorCode,errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
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
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
