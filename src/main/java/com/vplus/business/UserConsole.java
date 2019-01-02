package com.vplus.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vplus.mapper.CourseMapper;

/**
 * Console processing for getting user input
 */
@Service
public class UserConsole implements IUserConsole{
	/**
	 * Injected mapper, util for aws, and instance buffered reader
	 */
	private final CourseMapper courseMapper;
	private final IAwsUtil awsUtil;
	private final BufferedReader br;
	
	/**
	 * Constructor for injecting and also constructing new buffered reader
	 */
	public UserConsole(CourseMapper courseMapper, IAwsUtil awsUtil){
		this.courseMapper = courseMapper;
		this.awsUtil = awsUtil;
		this.br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/** Ask for professor **/
	private void promptProfessor(){
		System.out.println("Please enter the professor's name. (E.g., Gail Kaiser)");
	}
	
	/**
	 * Get professor name from user
	 * @return a professor that the user is interested in
	 * @throws IOException
	 */
	public String getProfessor() throws IOException{
		String userInput = "";
		
		// while prof not found, prompt again
		while(!courseMapper.isValidInstructor(userInput)){
			promptProfessor();
			userInput = br.readLine();
			
			
			String[] userInputArr = userInput.split("\\s+");
			
			if(userInputArr.length < 2){
				continue;
			}
			
			userInput = userInputArr[1].toUpperCase() + ", " + userInputArr[0].toUpperCase();
			if(!courseMapper.isValidInstructor(userInput)){
				promptTryAgain();
			}
		}
		
		return userInput;
	}

	/** Prompt welcome message **/
	public void promptWelcomeMessage(){
		System.out.println("Welcome to VergilPlus - Course Selection Made Easier.");
		System.out.println("What can we do for you?");
	}

	/**
	 * Get functionality from user
	 * @return a functionality that the user is interested in
	 * @throws IOException
	 */
	private void promptFunctionality() {
		System.out.println("Sorry, we didn't quite catch that.");
		System.out.println("What can we do for you?");
	}
	
	/** Prompt the user to try again **/
	private void promptTryAgain(){
		System.out.println("Sorry, we didn't understand that.  "
				+ "Please try again.  "
				+ "Your sentences might be too complex, "
				+ "you might not be following the correct format, "
				+ "or the information you've requested is not in our database.");		
	}
	
	/** Prompt the forced choices that the user has to make **/
	private void promptForceChoiceFunctionality(){
		System.out.println("We are sorry we are having trouble understanding you."
				+ "Here are the things that we can do for you:\n"
				+ "1. Tell me about a professor and recommend that professor's courses\n"
				+ "2. Recommend courses based on a topic.\n"
				+ "3. Recommend courses based on previous courses.\n"
				+ "You may enter 1, 2, or 3.");
	}	
	
	/**
	 * Ask user for functionality through a series of prompts
	 * @return a valid functionality that the user chooses
	 **/
	public int getFunctionality() throws IOException{
		int numFails = 0;
		String freeStyleFunctionality = "";

		// 1, 2, 3 or -1
		String awsResp = "S";

		while (awsResp.substring(0, 1).equals("S") && numFails < 3) {
			freeStyleFunctionality = br.readLine();
			if (freeStyleFunctionality.trim().length() == 0) {
				promptFunctionality();
				numFails++;
				continue;
			}

			awsResp = awsUtil.getAwsResponse(freeStyleFunctionality);
			numFails++;
			if (numFails != 3 && awsResp.substring(0, 1).equals("S")) {
				promptFunctionality();
			}
		}
		// System.out.println(awsResp);
		if (numFails >= 3) {
			promptForceChoiceFunctionality();
			String forcedChoice = br.readLine();

			while (!forcedChoice.equals("1") && !forcedChoice.equals("2") && !forcedChoice.equals("3")) {
				promptTryAgain();
				forcedChoice = br.readLine();
				if (forcedChoice.trim().length() == 0) {
					continue;
				}
			}

			return Integer.parseInt(forcedChoice);
		}
		return Integer.parseInt(awsResp);

	}

	/** Prompt topic **/
	private void promptTopic(){
		System.out.println("Please enter the topic. (E.g., Machine Learning)");
	}

	/**
	 * Get topic from user
	 * @return a topic that the user is interested in
	 * @throws IOException
	 */
	public String getTopic() throws IOException{
		String awsResp = "-1";
		String userInput = "";
		
		// while lex returns error code -1, prompt again
		while(awsResp.equals("-1") || !isValidTopic(awsResp)){
			promptTopic();
			userInput = br.readLine();
			if(userInput.trim().length() == 0){
				continue;
			}
			
			awsResp = awsUtil.getAwsResponse(userInput);
			if(!isValidTopic(awsResp)){
				System.out.println("Sorry, we either didn't understand that, or that is not one of our supported topics.  Please try again.");
			}
		}
		
		return awsResp;
	}

	/** Prompt taken courses **/
	private void promptTakenCourses(){
		System.out.println("What courses have you taken? (Enter using format WCOMS1234,WCOMS4321)\n"
				+ "Enter none if you have not taken any.");
	}
	
	/** Parse user input courses and check if courses are valid **/
	private boolean validCourses(String courses){
		String[] courseNumbers = courses.split(",");
		for(String c : courseNumbers){
			if(!courseMapper.isValidCourse(c)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get taken courses from user
	 * @return list of taken courses
	 * @throws IOException
	 */
	public List<String> getTakenCourses() throws IOException{
		promptTakenCourses();
		String input = br.readLine();
		List<String> res = new ArrayList<>();
		
		if(input.equals("none")){
			return res;
		} 
		
		while(!validCourses(input)){
			promptTryAgain();
			promptTakenCourses();
			input = br.readLine();
			if(input.equals("none")){
				return res;
			} 
		}
		
		res = Arrays.asList(input.split(","));
		return res;
	}

	/**
	 * Get all valid topics
	 * @return list of valid topics
	 */
    private List<String> getAllTopics(){
        ArrayList<String> allTopics = new ArrayList<>();
        allTopics.add("computer vision");
        allTopics.add("operating system");
        allTopics.add("machine learning");
        allTopics.add("robotics");
        allTopics.add("natural language processing");
        allTopics.add("graphics");
        allTopics.add("networks");
        allTopics.add("artificial intelligence");
        allTopics.add("database");
        allTopics.add("system");
        allTopics.add("theory");
        allTopics.add("computational biology");
        allTopics.add("algorithm");
		allTopics.add("security");
        return allTopics;
    }
    
    /** check if topic is valid **/
    private boolean isValidTopic(String topic){
    	for(String t : getAllTopics()){
    		if(t.equals(topic)){
    			return true;
    		}
    	}
    	return false;
    }
	
}
