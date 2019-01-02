package com.vplus.business;

/**
 * Utilities for sending Amazon Lex AWS requests
 * and handling responses
 */
public interface IAwsUtil{
	/**
	 * Get AWS response with input String
	 * @param freeStyleFunctionality the String that is sent to Lex
	 * @return Lex's response
	 */
	public String getAwsResponse(String freeStyleFunctionality);
}
