package dash.pojo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.io.filefilter.*;

import dash.errorhandling.AppException;
import dash.filters.AppConstants;
import dash.service.TransferApplicationService;

/**
 * 
 * Service Class that handles REST requests for TransferApplications
 * 
 * @author plindner
 */
@Component("transferApplicationResource")
@Path("/transferApplications")
public class TransferApplicationResource {

	@Autowired
	private TransferApplicationService transferApplicationService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createTransferApplication(TransferApplication transferApplication)
			throws AppException {
		Long createTransferApplicationId = transferApplicationService
				.createTransferApplication(transferApplication);
		return Response.status(Response.Status.CREATED)
				// 201
				.entity(createTransferApplicationId.toString())
				.header("Location",
						"http://..../transferApplications/"
								+ String.valueOf(createTransferApplicationId)).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, })
	public List<TransferApplication> getTransferApplications(
			@QueryParam("orderByInsertionDate") String orderByInsertionDate)
			throws IOException, AppException {
		List<TransferApplication> transferApplications = transferApplicationService
				.getTransferApplications(orderByInsertionDate);
		return transferApplications;
	}
	
	@GET
	@Path("/withfilenames")
	@Produces({ MediaType.APPLICATION_JSON, })
	public List<TransferApplication> getTransferApplicationsWithFileNames(
			@QueryParam("orderByInsertionDate") String orderByInsertionDate)
			throws IOException, AppException {
		List<TransferApplication> transferApplications = transferApplicationService
				.getTransferApplicationsWithFileNames();
		return transferApplications;
	}


	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, })
	public Response getTransferApplicationById(@PathParam("id") Long id) throws IOException,
			AppException {
		TransferApplication applicaionById = transferApplicationService.getTransferApplicationById(id);
		return Response.status(200)
				.entity(new GenericEntity<TransferApplication>(applicaionById) {
				}).header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putTransferApplicationById(@PathParam("id") Long id,
			TransferApplication transferApplication) throws AppException {

		TransferApplication transferApplicationById = transferApplicationService
				.verifyTransferApplicationExistenceById(id);

		if (transferApplicationById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createApplictionObjectId = transferApplicationService
					.createTransferApplication(transferApplication);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new transferApplication has been created AT THE LOCATION you specified")
					.header("Location",
							"http://.../transferApplications/"
									+ String.valueOf(createApplictionObjectId))
					.build();
		} else {
			// resource is existent and a full update should occur
			transferApplicationService.updateFullyTransferApplication(transferApplication);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The transferApplication you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://.../transferApplications" + String.valueOf(id))
					.build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateTransferApplication(@PathParam("id") Long id,
			TransferApplication transferApplication) throws AppException {
		// transferApplication.setId(id);
		transferApplicationService.updatePartiallyTransferApplication(transferApplication);
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("The transferApplication you specified has been successfully updated")
				.build();
	}

	@POST
	@Path("/upload")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public Response uploadFile(@QueryParam("id") Long id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@HeaderParam("Content-Length") final long fileSize)
			throws AppException {

		TransferApplication transferApplication = transferApplicationService.getTransferApplicationById(id);

		// TODO: Generate directory if not set
		// if(transferApplication.getDocument_folder()==null)
		String uploadedFileLocation = AppConstants.APPLICATION_UPLOAD_LOCATION_FOLDER
				+ "/"
				+ transferApplication.getDocument_folder()
				+ "/"
				+ fileDetail.getFileName().replaceAll("%20", "_").toLowerCase();
		;
		// save it
		transferApplicationService.uploadFile(uploadedInputStream,
				uploadedFileLocation, transferApplication);

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}

	@GET
	@Path("/upload")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getFileNames(@QueryParam("transferApplicationId") Long id)
			throws AppException {

		TransferApplication transferApplication = transferApplicationService.getTransferApplicationById(id);
		JaxbList<String> fileNames = new JaxbList<String>(
				transferApplicationService.getFileNames(transferApplication));
		return Response.status(200).entity(fileNames).build();
	}

	// Gets a specific file and allows the user to download the pdf
	@GET
	@Path("/download")
	public Response getFile(@QueryParam("transferApplicationId") Long id,
			@QueryParam("fileName") String fileName) throws AppException {

		TransferApplication transferApplication = transferApplicationService.getTransferApplicationById(id);

		if (transferApplication == null) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Invalid transferApplicationId, unable to locate transferApplication with id: "
							+ id).build();
		}

		File dir = new File(AppConstants.APPLICATION_UPLOAD_LOCATION_FOLDER
				+ transferApplication.getDocument_folder());
		FileFilter fileFilter = new WildcardFileFilter(fileName);
		File[] files = dir.listFiles(fileFilter);

		if (files != null) {

			String uploadedFileLocation = files[0].getAbsolutePath();

			String ext = "";

			if (uploadedFileLocation.lastIndexOf(".") != -1
					&& uploadedFileLocation.lastIndexOf(".") != 0)
				ext = uploadedFileLocation.substring(uploadedFileLocation
						.lastIndexOf("."));

			if (ext.equals(".pdf")) {
				return Response
						.ok(transferApplicationService.getUploadFile(
								uploadedFileLocation, transferApplication))
						.type("transferApplication/pdf").build();
			} else {
				String generatedFileLocation = AppConstants.APPLICATION_UPLOAD_LOCATION_FOLDER
						+ transferApplication.getDocument_folder()
						+ "/generated/"
						+ files[0].getName().replace(ext, ".pdf");
				transferApplicationService.createPDF(uploadedFileLocation,
						generatedFileLocation);
				return Response
						.ok(transferApplicationService.getUploadFile(
								generatedFileLocation, transferApplication))
						.type("transferApplication/pdf").build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("No files uploaded.").build();
		}
	}

	@DELETE
	@Path("/upload")
	public Response deleteUpload(@QueryParam("transferApplicationId") Long id,
			@QueryParam("fileName") String fileName) throws AppException {

		TransferApplication transferApplication = transferApplicationService.getTransferApplicationById(id);

		String uploadedFileLocation = AppConstants.APPLICATION_UPLOAD_LOCATION_FOLDER
				+ transferApplication.getDocument_folder() + "/" + fileName;
		// save it
		transferApplicationService.deleteUploadFile(uploadedFileLocation, transferApplication);

		String output = "File removed from: " + uploadedFileLocation;

		return Response.status(200).entity(output).build();
	}

	/*
	 * *********************************** DELETE
	 * ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteTransferApplication(@PathParam("id") Long id)
			throws AppException {
		TransferApplication transferApplication = new TransferApplication();
		transferApplication.setId(id);
		transferApplicationService.deleteTransferApplication(transferApplication);
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("TransferApplication successfully removed from database")
				.build();
	}

	@DELETE
	@Path("admin")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteTransferApplications() {
		transferApplicationService.deleteTransferApplications();
		return Response.status(Response.Status.NO_CONTENT)
				// 204
				.entity("All transferApplications have been successfully removed")
				.build();
	}

	@XmlRootElement(name = "fileNames")
	public static class JaxbList<T> {
		protected List<T> list;

		public JaxbList() {
		}

		public JaxbList(List<T> list) {
			this.list = list;
		}

		@XmlElement(name = "fileName")
		public List<T> getList() {
			return list;
		}
	}

}
