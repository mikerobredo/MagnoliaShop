package info.magnolia.training.fullstack.templating.redsysModel;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import info.magnolia.context.MgnlContext;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
import io.swagger.annotations.Api;
 
@Api(value = "/RedsysEndpoint")
@Path("/RedsysEndpoint")
public class RedsysEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
 
    public RedsysEndpoint(D endpointDefinition) {
        super(endpointDefinition);
    }
 
    @GET
    @Path("/setIDRedsys")    
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response setIDRedsys()
    {  	
    	System.out.println("hi");
    	if (MgnlContext.getParameter("idredsys") != null) {
			MgnlContext.setAttribute("idr", MgnlContext.getParameter("idredsys"),2);	
    	}	    	
        return Response.ok().build();       	
    }
    
    @GET
    @Path("/proceed")    
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response proceed()
    {  	
    	String proc = MgnlContext.getParameter("proceed");
    	if (proc != null) {
			MgnlContext.setAttribute("proceed", proc, 2);
    	}	    	
        return Response.ok().build();   
    	
    }
}