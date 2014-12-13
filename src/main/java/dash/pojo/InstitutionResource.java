package dash.pojo;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dash.errorhandling.AppException;
import dash.service.InstitutionService;

/**
 * 
 * Service Class that handles REST requests for Applications
 * 
 * @author plindner
 */
@Component("institutionResource")
@Path("/institution")
public class InstitutionResource {

	@Autowired
	private InstitutionService institutionService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createInstitution(Institution institution)
			throws AppException {
		Long createInstitutionId = institutionService
				.createInstitution(institution);
		return Response.status(Response.Status.CREATED)
				// 201
				.entity(createInstitutionId.toString())
				.header("Location",
						"http://..../applications/"
								+ String.valueOf(createInstitutionId)).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, })
	public List<Institution> getInstitution(
			@QueryParam("orderByInsertionDate") String orderByInsertionDate)
			throws IOException, AppException {
		List<Institution> institution = institutionService
				.getInstitution(orderByInsertionDate);
		return institution;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, })
	public Response getInstitutionById(@PathParam("id") Long id,
			@QueryParam("detailed") boolean detailed) throws IOException,
			AppException {
		Institution institutionById = institutionService.getInstitutionById(id);
		return Response
				.status(200)
				.entity(new GenericEntity<Institution>(institutionById) {
				})
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}
	
	@GET
	@Path("list/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Institution> getInstitutionByApplicationId(@PathParam("id") Long appId) throws IOException,
			AppException {
		List<Institution> institutions = institutionService
				.getInstitutionByAppId(appId);
		return institutions;
	}


	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putInstitutionById(@PathParam("id") Long id,
			Institution institution) throws AppException {

		Institution institutionById = institutionService
				.verifyInstitutionExistenceById(id);

		if (institutionById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createInstitutionObjectId = institutionService
					.createInstitution(institution);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new Institution has been created AT THE LOCATION you specified")
					.header("Location",
							"http://.../institution/"
									+ String.valueOf(createInstitutionObjectId))
					.build();
		} else {
			// resource is existent and a full update should occur
			institutionService.updateFullyInstitution(institution);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The institution you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://.../institution" + String.valueOf(id))
					.build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateInstitution(@PathParam("id") Long id,
			Institution institution) throws AppException {

		institutionService.updatePartiallyInstitution(institution);
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("The institution you specified has been successfully updated")
				.build();
	}

	/*
	 * *********************************** DELETE
	 * ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteInstitution(@PathParam("id") Long id)
			throws AppException {
		Institution institution = new Institution();
		institution.setId(id);
		institutionService.deleteInstitution(institution);
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("institution successfully removed from database")
				.build();
	}
}
