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
import dash.service.SurfApplicationService;

/**
 * 
 * Service Class that handles REST requests for SurfApplications
 * 
 * @author plindner
 */
@Component
@Path("/surf_application")
public class SurfApplicationResource {

	@Autowired
	private SurfApplicationService applicationService;
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createApplication(SurfApplication application)
			throws AppException {
		Long createApplicationId = applicationService
				.createApplication(application);
		return Response.status(Response.Status.CREATED)
				// 201
				.entity(createApplicationId.toString())
				.header("Location",
						"http://..../surf_applications/"
								+ String.valueOf(createApplicationId)).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<SurfApplication> getApplications(
			@QueryParam("orderByInsertionDate") String orderByInsertionDate)
			throws IOException, AppException {
		List<SurfApplication> applications = applicationService
				.getApplications(orderByInsertionDate);
		return applications;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getApplicationById(@PathParam("id") Long id,
			@QueryParam("detailed") boolean detailed) throws IOException,
			AppException {
		SurfApplication applicaionById = applicationService.getApplicationById(id);
		return Response
				.status(200)
				.entity(new GenericEntity<SurfApplication>(applicaionById) {
				})
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putApplicationById(@PathParam("id") Long id,
			SurfApplication application) throws AppException {

		SurfApplication applicationById = applicationService
				.verifyApplicationExistenceById(id);

		if (applicationById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createApplictionObjectId = applicationService
					.createApplication(application);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new application has been created AT THE LOCATION you specified")
					.header("Location",
							"http://.../surf_applications/"
									+ String.valueOf(createApplictionObjectId))
					.build();
		} else {
			// resource is existent and a full update should occur
			applicationService.updateFullyApplication(application);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The application you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://.../surf_applications" + String.valueOf(id))
					.build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateApplication(@PathParam("id") Long id,
			SurfApplication application) throws AppException {
//		application.setId(id);
		applicationService.updatePartiallyApplication(application);
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("The application you specified has been successfully updated")
				.build();
	}
	
	/*
	 * *********************************** DELETE
	 * ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteApplication(@PathParam("id") Long id)
			throws AppException {
		SurfApplication application = new SurfApplication();
		application.setId(id);
		applicationService.deleteApplication(application);
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("Application successfully removed from database")
				.build();
	}

	@DELETE
	@Path("admin")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteApplications() {
		applicationService.deleteApplications();
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("All applications have been successfully removed")
				.build();
	}

}
