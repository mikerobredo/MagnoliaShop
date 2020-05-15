package info.magnolia.training.fullstack.templating.redsysModel;

import javax.ws.rs.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.magnolia.rest.*;
import info.magnolia.rest.EndpointDefinition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.*;
import io.swagger.*;
import org.jboss.resteasy.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Api(value = "/services/v1")
@Path("/services/v1")
public class UploadServicesEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private final Logger log = LoggerFactory.getLogger(UploadServicesEndpoint.class);

	public UploadServicesEndpoint(D endpointDefinition) {
		super(endpointDefinition);
	}

	@POST
	@Path("/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.TEXT_PLAIN })
	@ApiOperation(value = "Upload file to Magnolia.", notes = "Upload file.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "BAD_REQUEST") })
	public Response uploadFile(MultipartFormDataInput uploadedFile) throws Exception {
		if (uploadedFile != null) {
			log.info("Received file...");
			try {
				// do something useful, eg parse a CSV file and import data into Magnolia
				// uploadedFile.getFormDataMap()...
			} catch (Exception e) {
				log.error("Problem while uploading file.", e);
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("ERROR: Problem while uploading file: " + e.getMessage() + ".\n").build();
			}
		}
		return Response.ok().entity("File successfully uploaded.\n").build();
	}

	@POST
	@Path("/uploadFileData")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces({ MediaType.TEXT_PLAIN })
	@ApiOperation(value = "Post file data inline.", notes = "Upload file by providing inline data.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "BAD_REQUEST") })
	public Response uploadFileData(String data) throws Exception {
		log.info("uploadFileData - received data:\n" + data + "\n");
		try {
			// do something useful with the data...
		} catch (Exception e) {
			log.error("Problem while parsing inline data.", e);
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("ERROR: Problem while parsing inline data: " + e.getMessage() + ".\n").build();
		}
		return Response.ok().entity("Inline data successfully created.\n").build();
	}

}