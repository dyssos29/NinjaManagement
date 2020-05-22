package com.eurodynamic.webapp.ninja_rest_api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Root resource (exposed at "ninjas" path)
 */
@Path("ninjas")
public class NinjaResource
{	
	NinjaDAO dao = new NinjaDAO();
	
	/**
     * Method handling HTTP GET requests. The returned objects will be sent
     * to the client as list of "JSON" media types.
     *
     * @return List of Ninja objects each of which will be returned in the JSON format.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ninja> getAllNinjas()
    {
    	List<Ninja> ninjas = null;
    	
        try
        {
			ninjas = dao.retrieveAllNinjas();
		}
        catch (SQLException e)
        {
        	ErrorMessage errMessage = new ErrorMessage("SQL error: " + e.getMessage(), "https://dev.mysql.com/doc/");
    		Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
    													.entity(errMessage).build();
    		
			throw new InternalServerErrorException(response);
		}
        
        return ninjas;
    }
    
    /**
     * Method handling HTTP POST requests. The returned object will be the Ninja
     * that was created.
     * 
     * @param Ninja object which is sent from the client.
     * @return Ninja object that will be returned in the JSON format.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNinja(Ninja aNinja)
    {
    	Ninja persistedNinja = null;
    	
        try
        {
        	persistedNinja = dao.persistNinja(aNinja);
		}
        catch (SQLException e)
        {
        	ErrorMessage errMessage = new ErrorMessage("SQL error: " + e.getMessage(), "https://dev.mysql.com/doc/");
    		Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
    													.entity(errMessage).build();
    		
			throw new InternalServerErrorException(response);
		}
        
        return Response.status(Status.CREATED)
        				.entity(persistedNinja)
        				.build();
    }
    
    /**
     * Method handling HTTP PUT requests. The returned object will be the Ninja
     * that was updated.
     *
     * @param Ninja object which is sent from the client.
     * @return Ninja object that will be returned in the JSON format.
     */
    @PUT
    @Path("/ninja")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Ninja updateNinja(Ninja aNinja)
    {
    	Ninja updatedNinja = null;
    	
        try
        {
        	updatedNinja = dao.modifyNinja(aNinja);
        	
        	if (updatedNinja == null)
        	{
        		ErrorMessage errMessage = new ErrorMessage("Updating ninja failed, no rows affected.",
        													"https://eclipse-ee4j.github.io/jersey/");
        		Response response = Response.status(Status.NOT_FOUND).entity(errMessage).build();
        		
        		throw new NotFoundException(response);
        	}
		}
        catch (SQLException e)
        {
        	ErrorMessage errMessage = new ErrorMessage("SQL error: " + e.getMessage(), "https://dev.mysql.com/doc/");
    		Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
    													.entity(errMessage).build();
    		
			throw new InternalServerErrorException(response);
		}
        
        return updatedNinja;
    }
    
    /**
     * Method handling HTTP DELETE requests. It does not return anything
     * since it is a "void" method.
     * @param Id of a Ninja object which is sent from the client.
     */
    @DELETE
    @Path("/ninja/{id}")
    public void deleteNinja(@PathParam("id") int id)
    {
        try
        {
        	if (!dao.removeNinja(id))
        	{
        		ErrorMessage errMessage = new ErrorMessage("Deleting ninja failed, no rows affected.",
        													"https://eclipse-ee4j.github.io/jersey/");
        		Response response = Response.status(Status.NOT_FOUND).entity(errMessage).build();
        		
        		throw new NotFoundException(response);
        	}
		}
        catch (SQLException e)
        {
        	ErrorMessage errMessage = new ErrorMessage("SQL error: " + e.getMessage(), "https://dev.mysql.com/doc/");
    		Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
    													.entity(errMessage).build();
    		
			throw new InternalServerErrorException(response);
		}
    }
}