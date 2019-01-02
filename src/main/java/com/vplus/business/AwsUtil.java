package com.vplus.business;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

/**
 * Utilities for sending Amazon Lex AWS requests
 * and handling responses
 * Need an Amazon AWS Lex Path set with valid keys
 */
@Service
public class AwsUtil implements IAwsUtil{
	/**
	 * Create AWSLambda Instance
	 * @return AWSLambda instance
	 */
    private AWSLambda createLambda(){
        Regions region = Regions.fromName("us-east-1");
        AWSLambdaClientBuilder builder = AWSLambdaClientBuilder.standard()
                .withRegion(region);
        AWSLambda client = builder.build();
	    return client;
    }
    
    /**
     * Parse response from AWS
     * @param result Result of AWS
     * @return Result of AWS parsed into JSON, and then into String
     */
    private String parseResponse(InvokeResult result){
        String response = new String(result.getPayload().array());
        JSONObject jsonObj = new JSONObject(response);
        String toS = jsonObj.get("greeting").toString();
        return toS;
    }

    /**
     * Send request to AWS and parse AWS response
     * @param input AWS request
     * @return Result of AWS response
     */
    public String getAwsResponse(String input){   	
    	String user = "user";
    	String detect = "false";
    	String body = "{\"userId\": \""+ user + "\", \"message\": {\"word\":"+ "\""+input +";"+detect+"\"" +"}}";
    	InvokeRequest req = new InvokeRequest().withFunctionName("Chatbot").withPayload(body); // optional
        AWSLambda client = createLambda();
        InvokeResult result = client.invoke(req);
    	String res = parseResponse(result);        	
    	return res;
    }
}
