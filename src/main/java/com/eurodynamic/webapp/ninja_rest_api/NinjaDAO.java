package com.eurodynamic.webapp.ninja_rest_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NinjaDAO
{
	private Connection dbConnection;
	
	public NinjaDAO()
	{
		String dbUrl = "jdbc:mysql://au7zlooj22xmka5m:usdi0u0z3k83ertu@z12itfj4c1vgopf8.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/lf46vfgr0add8f3h?"
				+ "					useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "au7zlooj22xmka5m";
		String password = "usdi0u0z3k83ertu;";
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			dbConnection = DriverManager.getConnection(dbUrl, username, password);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Ninja> retrieveAllNinjas() throws SQLException
	{
		List<Ninja> ninjas = new ArrayList<>();
        
		String sglStr = "select * from ninja";
		Statement aStatement = dbConnection.createStatement();
		ResultSet aResultSet = aStatement.executeQuery(sglStr);
		
		while (aResultSet.next())
		{
			Ninja retrievedNinja = new Ninja(aResultSet.getInt("id")
											, aResultSet.getString("first_name")
											, aResultSet.getString("last_name")
											, aResultSet.getString("ability")
											, aResultSet.getDate("birthday")
											, aResultSet.getString("gender")
											, aResultSet.getString("village"));
			
			ninjas.add(retrievedNinja);
		}
		
        return ninjas;
	}
	
	public Ninja persistNinja(Ninja aNinja) throws SQLException
	{
		String sqlStr = "insert into ninja (" + 
				"	first_name," + 
				"    last_name," + 
				"    ability," + 
				"    birthday," + 
				"    gender," + 
				"    village" + 
				") values (" + 
				"	?," + 
				"    ?," + 
				"    ?," + 
				"    ?," + 
				"    ?," + 
				"    ?" + 
				")";
		PreparedStatement statement = dbConnection.prepareStatement(sqlStr
													, Statement.RETURN_GENERATED_KEYS);
		
		
		statement.setString(1, aNinja.getFirstName());
		statement.setString(2, aNinja.getLastName());
		statement.setString(3, aNinja.getAbility());
		statement.setDate(4, aNinja.getBirthday());
		statement.setString(5, aNinja.getGender());
		statement.setString(6, aNinja.getVillage());
		
		int affectedRows = statement.executeUpdate();
		
		if (affectedRows == 0)
            throw new SQLException("Persisting ninja failed, no rows affected.");
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		
        if (generatedKeys.next())
            aNinja.setId((int)generatedKeys.getLong(1));
        else
            throw new SQLException("Persisting ninja failed, no ID obtained.");
        
        return aNinja;
	}
	
	public Ninja modifyNinja(Ninja aNinja) throws SQLException
	{
		String sqlStr = "update ninja set " + 
				"	first_name = ?," + 
				"    last_name = ?," + 
				"    ability = ?," + 
				"    birthday = ?," + 
				"    gender = ?," + 
				"    village  = ?" + 
				" where id = " + aNinja.getId();
		PreparedStatement statement = dbConnection.prepareStatement(sqlStr);
		
		
		statement.setString(1, aNinja.getFirstName());
		statement.setString(2, aNinja.getLastName());
		statement.setString(3, aNinja.getAbility());
		statement.setDate(4, aNinja.getBirthday());
		statement.setString(5, aNinja.getGender());
		statement.setString(6, aNinja.getVillage());
		
		int affectedRows = statement.executeUpdate();
		
		if (affectedRows == 0)
            aNinja = null;
		
        return aNinja;
	}
	
	public boolean removeNinja(int id) throws SQLException
	{
		String sqlStr = "delete from ninja where id = " + id;
		PreparedStatement statement = dbConnection.prepareStatement(sqlStr);
		
		int affectedRows = statement.executeUpdate();
		
		if (affectedRows == 0)
            return false;
		
		return true;
	}
}