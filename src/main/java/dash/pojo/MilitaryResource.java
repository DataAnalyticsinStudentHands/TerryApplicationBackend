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
import dash.service.MilitaryService;

/**
 * 
 * Service Class that handles REST requests for Applications
 * 
 * @author plindner
 */
@Component("militaryResource")
@Path("/military")
public class MilitaryResource {

	@Autowired
	private MilitaryService militaryService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createMilitary(Military military)
			throws AppException {
		Long createMilitaryId = militaryService
				.createMilitary(military);
		return Response.status(Response.Status.CREATED)
				// 201
				.entity(createMilitaryId.toString())
				.header("Location",
						"http://..../applications/"
								+ String.valueOf(createMilitaryId)).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, })
	public List<Military> getMilitary(
			@QueryParam("orderByInsertionDate") String orderByInsertionDate)
			throws IOException, AppException {
		List<Military> military = militaryService
				.getMilitary(orderByInsertionDate);
		return military;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, })
	public Response getMilitaryById(@PathParam("id") Long id,
			@QueryParam("detailed") boolean detailed) throws IOException,
			AppException {
		Military militaryById = militaryService.getMilitaryById(id);
		return Response
				.status(200)
				.entity(new GenericEntity<Military>(militaryById) {
				})
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}
	
	@GET
	@Path("list/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Military> getMilitaryByApplicationId(@PathParam("id") Long appId) throws IOException,
			AppException {
		List<Military> militarys = militaryService
				.getMilitaryByAppId(appId);
		return militarys;
	}


	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putMilitaryById(@PathParam("id") Long id,
			Military military) throws AppException {

		Military militaryById = militaryService
				.verifyMilitaryExistenceById(id);

		if (militaryById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createMilitaryObjectId = militaryService
					.createMilitary(military);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new Military has been created AT THE LOCATION you specified")
					.header("Location",
							"http://.../military/"
									+ String.valueOf(createMilitaryObjectId))
					.build();
		} else {
			// resource is existent and a full update should occur
			militaryService.updateFullyMilitary(military);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The military you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://.../military" + String.valueOf(id))
					.build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateMilitary(@PathParam("id") Long id,
			Military military) throws AppException {

		militaryService.updatePartiallyMilitary(military);
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("The military you specified has been successfully updated")
				.build();
	}

	/*
	 * *********************************** DELETE
	 * ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteMilitary(@PathParam("id") Long id)
			throws AppException {
		Military military = new Military();
		military.setId(id);
		militaryService.deleteMilitary(military);
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("military successfully removed from database")
				.build();
	}
}
