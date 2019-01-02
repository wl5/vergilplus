package com.vplus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vplus.business.Recommender;

/**
 * Application class for running VergilPlus.
 */

@SpringBootApplication
public class Application implements CommandLineRunner{
	private final Recommender recommender;	
	
	/** Constructor for injecting recommender **/
	public Application(Recommender recommender){
		this.recommender = recommender;
	}
	
	/** Main method, call SpringApplication.run to run the application **/
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
	
	/** Define what happens when the application is run **/
	public void run(String... args) throws Exception{
		recommender.recommend();
	}

}
