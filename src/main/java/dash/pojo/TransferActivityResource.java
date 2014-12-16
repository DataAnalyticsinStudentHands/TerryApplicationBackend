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
import dash.service.TransferActivityService;

/**
 * 
 * Service Class that handles REST requests for Applications
 * 
 * @author plindner
 */
@Component("transferActivityResource")
@Path("/transfer_activity")
public class TransferActivityResource {

	@Autowired
	private TransferActivityService transferActivityService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createTransferActivity(TransferActivity transferActivity)
			throws AppException {
		Long createTransferActivityId = transferActivityService
				.createTransferActivity(transferActivity);
		return Response.status(Response.Status.CREATED)
				// 201
				.entity(createTransferActivityId.toString())
				.header("Location",
						"http://..../applications/"
								+ String.valueOf(createTransferActivityId)).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, })
	public List<TransferActivity> getTransferActivity(
			@QueryParam("orderByInsertionDate") String orderByInsertionDate)
			throws IOException, AppException {
		List<TransferActivity> transferActivity = transferActivityService
				.getTransferActivity(orderByInsertionDate);
		return transferActivity;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, })
	public Response getTransferActivityById(@PathParam("id") Long id,
			@QueryParam("detailed") boolean detailed) throws IOException,
			AppException {
		TransferActivity transferActivityById = transferActivityService.getTransferActivityById(id);
		return Response
				.status(200)
				.entity(new GenericEntity<TransferActivity>(transferActivityById) {
				})
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}
	
	@GET
	@Path("list/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TransferActivity> getTransferActivityByApplicationId(@PathParam("id") Long appId) throws IOException,
			AppException {
		List<TransferActivity> transferActivitys = transferActivityService
				.getTransferActivityByAppId(appId);
		return transferActivitys;
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putTransferActivityById(@PathParam("id") Long id,
			TransferActivity transferActivity) throws AppException {

		TransferActivity transferActivityById = transferActivityService
				.verifyTransferActivityExistenceById(id);

		if (transferActivityById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createTransferActivityObjectId = transferActivityService
					.createTransferActivity(transferActivity);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new TransferActivity has been created AT THE LOCATION you specified")
					.header("Location",
							"http://.../transferActivity/"
									+ String.valueOf(createTransferActivityObjectId))
					.build();
		} else {
			// resource is existent and a full update should occur
			transferActivityService.updateFullyTransferActivity(transferActivity);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The transferActivity you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://.../transferActivity" + String.valueOf(id))
					.build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateTransferActivity(@PathParam("id") Long id,
			TransferActivity transferActivity) throws AppException {

		transferActivityService.updatePartiallyTransferActivity(transferActivity);
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("The transferActivity you specified has been successfully updated")
				.build();
	}

	/*
	 * *********************************** DELETE
	 * ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteTransferActivity(@PathParam("id") Long id)
			throws AppException {
		TransferActivity transferActivity = new TransferActivity();
		transferActivity.setId(id);
		transferActivityService.deleteTransferActivity(transferActivity);
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("transferActivity successfully removed from database")
				.build();
	}

	@DELETE
	@Path("admin")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteTransferActivitys() {
		transferActivityService.deleteTransferActivitys();
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("All transferActivity have been successfully removed")
				.build();
	}

}
