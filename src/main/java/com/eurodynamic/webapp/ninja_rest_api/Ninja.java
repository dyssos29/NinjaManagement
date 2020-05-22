package com.eurodynamic.webapp.ninja_rest_api;

import java.sql.Date;

public class Ninja
{
	private int id;
	private String firstName;
	private String lastName;
	private String ability;
	private Date birthday;
	private String gender;
	private String village;
	
	public Ninja()
	{
		
	}
		
	public Ninja(int id, String firstName, String lastName
			, String ability
			, Date birthday
			, String gender
			, String village)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ability = ability;
		this.birthday = birthday;
		this.gender = gender;
		this.village = village;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getAbility()
	{
		return ability;
	}
	
	public void setAbility(String ability) {
		this.ability = ability;
	}
	
	public Date getBirthday()
	{
		return birthday;
	}
	
	public void setBirthday(Date age)
	{
		this.birthday = age;
	}
	
	public String getGender() 
	{
		return gender;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getVillage()
	{
		return village;
	}

	public void setVillage(String village)
	{
		this.village = village;
	}
}