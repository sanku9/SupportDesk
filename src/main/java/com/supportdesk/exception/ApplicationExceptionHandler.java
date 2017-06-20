package com.supportdesk.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.supportdesk.common.Constants;
import com.supportdesk.common.ErrorCodeConstants;

public class ApplicationExceptionHandler 
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * This method logs the errors encountered in the application.
	 * Method logErrors
	 *
	 * @version 1.0
	 * @param exception  
	 *
	 */
	public void logErrors(final Throwable exception)
	{
		LOGGER.debug("Entering ExceptionHandler.logErrors method");
		if(exception instanceof ApplicationBaseException){
			final ApplicationBaseException baseExp = (ApplicationBaseException)exception;
			if("".equalsIgnoreCase(baseExp.getErrorCode())){
				LOGGER.error(Constants.EXCEPTION_ERROR_CODE+ErrorCodeConstants.ERROR_CODE_BASE_EXCEPTION);
			}else{
				LOGGER.error(Constants.EXCEPTION_ERROR_CODE+baseExp.getErrorCode());
			}
			if("".equalsIgnoreCase(baseExp.getErrorMessage())){
				LOGGER.error(Constants.EXCEPTION_ERROR_MESSAGE+Constants.EXCEPTION_DEFAULT_MESSAGE);
				LOGGER.error(Constants.EXCEPTION_DETAILS,baseExp);
			}else{
				LOGGER.error(Constants.EXCEPTION_ERROR_MESSAGE+baseExp.getErrorMessage());
				LOGGER.error(Constants.EXCEPTION_DETAILS,baseExp);
				
			}
			
		}else{
			LOGGER.error(Constants.EXCEPTION_ERROR_CODE+ErrorCodeConstants.ERROR_CODE_SYSTEM_EXCEPTION);
			if("".equalsIgnoreCase(exception.getMessage())){
			LOGGER.error(Constants.EXCEPTION_ERROR_MESSAGE+Constants.SYSTEM_EXCEPTION_DEFAULT_MESSAGE);
			}else{
			LOGGER.error(Constants.EXCEPTION_ERROR_MESSAGE+exception.getMessage());
			}
			LOGGER.error(Constants.EXCEPTION_DETAILS,exception);
		}
		LOGGER.debug("Leaving ExceptionHandler.logErrors method");
	}
	 
}
