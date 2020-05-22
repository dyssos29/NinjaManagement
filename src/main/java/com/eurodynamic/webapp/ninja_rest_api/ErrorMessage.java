package com.eurodynamic.webapp.ninja_rest_api;

import javax.json.bind.annotation.JsonbTypeAdapter;

public class ErrorMessage
{
	private String message;
	private String documentation;
	
	public ErrorMessage()
	{
		
	}
	
	public ErrorMessage(String message, String documentation)
	{
		this.message = message;
		this.documentation = documentation;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setDocumentation(String documentation)
	{
		this.documentation = documentation;
	}
	
	public String getDocumentation()
	{
		return documentation;
	}
}