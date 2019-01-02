package com.vplus.business;

import java.io.IOException;
import java.util.List;

/**
 * Console processing for getting user input
 */
public interface IUserConsole {
	/**
	 * Prompt welcome message
	 */
	public void promptWelcomeMessage();
	
	/**
	 * Get taken courses from user
	 * @return list of taken courses
	 * @throws IOException
	 */
	public List<String> getTakenCourses() throws IOException;
	
	/**
	 * Get topic from user
	 * @return a topic that the user is interested in
	 * @throws IOException
	 */
	public String getTopic() throws IOException;
	
	/**
	 * Get professor name from user
	 * @return a professor that the user is interested in
	 * @throws IOException
	 */
	public String getProfessor() throws IOException;
	
	/**
	 * Get functionality from user
	 * @return a functionality that the user is interested in
	 * @throws IOException
	 */
	public int getFunctionality() throws IOException;
}
