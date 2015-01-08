package dash.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dash.dao.TransferApplicationDao;
import dash.dao.TransferApplicationEntity;
import dash.errorhandling.AppException;
import dash.filters.AppConstants;
import dash.helpers.NullAwareBeanUtilsBean;
import dash.pojo.TransferApplication;
import dash.security.CustomPermission;
import dash.security.GenericAclController;

@Component("transferApplicationService")
public class TransferApplicationServiceDbAccessImpl extends ApplicationObjectSupport
		implements TransferApplicationService {

	@Autowired
	TransferApplicationDao transferApplicationDao;

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private GenericAclController<TransferApplication> aclController;

	/********************* Create related methods implementation ***********************/
	@Override
	@Transactional
	public Long createTransferApplication(TransferApplication transferApplication) throws AppException {

		validateInputForCreation(transferApplication);

		//verify existence of resource in the db (uh_id must be unique)
		TransferApplicationEntity transferApplicationByUh_id =
				transferApplicationDao.getTransferApplicationByUh_id(transferApplication.getUh_id());
		
		 if (transferApplicationByUh_id != null) { throw new AppException(
				 Response.Status.CONFLICT.getStatusCode(), 409,
				 "Object with uh_id " + transferApplication.getUh_id() + " already existing in the database.",
				 "Please verify.",
				 	AppConstants.DASH_POST_URL); }
		 

		long transferApplicationId = transferApplicationDao
				.createTransferApplication(new TransferApplicationEntity(transferApplication));
		transferApplication.setId(transferApplicationId);

		aclController.createACL(transferApplication);
		aclController.createAce(transferApplication, CustomPermission.READ);
		aclController.createAce(transferApplication, CustomPermission.WRITE);
		aclController.createAce(transferApplication, CustomPermission.DELETE);

		return transferApplicationId;
	}

	private void validateInputForCreation(TransferApplication transferApplication)
			throws AppException {
		if (transferApplication.getFirst_name() == null
				|| transferApplication.getLast_name() == null
				|| transferApplication.getUh_id() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
					400, "Provided data not sufficient for insertion into database",
					"Please verify that the first_name, last_name and uh_id had been properly set",
					AppConstants.DASH_POST_URL);
		}
	}

	// save uploaded file to new location
	public void uploadFile(InputStream uploadedInputStream,
			String uploadedFileLocation, TransferApplication transferApplication)
			throws AppException {

		try {
			File file = new File(uploadedFileLocation);
			file.getParentFile().mkdirs();
			OutputStream out = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			throw new AppException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500,
					"Could not upload file due to IOException", "\n\n"
							+ e.getMessage(), AppConstants.DASH_POST_URL);
		}

	}
	
	@Override
	public List<TransferApplication> getTransferApplicationsWithFileNames() throws AppException {
		List<TransferApplication> transferApplications = getTransferApplications(null);
		
		for(Iterator<TransferApplication> i = transferApplications.iterator(); i.hasNext(); ) {
		    TransferApplication item = i.next();
		    List<String> files = new ArrayList<String>();
		    files.addAll(getFileNames(item));
		    item.setFile_names(files);
		}
		
		return transferApplications;
	}

	/**
	 * Generate PDF from word files docx
	 */
	@Override
	public void createPDF(String inputFilePath, String outputFilePath)
			throws AppException {

		// check whether we already have a generated pdf
		File outputTest = new File(outputFilePath);

		if (!outputTest.exists()) {
			if (!outputTest.getParentFile().exists()) {
				outputTest.getParentFile().mkdir();
			} 
		} else {
			//check whether generated file is older than current uploaded file
			File inputTest = new File (inputFilePath);
			if (inputTest.lastModified() < outputTest.lastModified()) {
				return;
			}				
		}
		
		try {
			long start = System.currentTimeMillis();

			// 1) Load DOCX into XWPFDocument
			InputStream is = new FileInputStream(new File(inputFilePath));
			XWPFDocument document = new XWPFDocument(is);

			// 2) Prepare Pdf options
			PdfOptions options = PdfOptions.create();

			// 3) Convert XWPFDocument to Pdf
			OutputStream out = new FileOutputStream(
					new File(outputFilePath));
			PdfConverter.getInstance().convert(document, out, options);

			logger.debug("Generated " + outputFilePath + " in "
					+ (System.currentTimeMillis() - start) + "ms");

		} catch (Throwable e) {
			logger.debug("Could not generate the pdf from word file."
					+ inputFilePath + "\n" + e.getMessage());
			throw new AppException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					AppConstants.GENERIC_APP_ERROR_CODE,
					"Could not generate the pdf from word file.", null,
					AppConstants.DASH_POST_URL);
		}
			
	}

	public File getUploadFile(String uploadedFileLocation,
			TransferApplication transferApplication) throws AppException {
		return new File(uploadedFileLocation);
	}

	// ******************** Read related methods implementation
	// **********************
	@Override
	public List<TransferApplication> getTransferApplications(String orderByInsertionDate)
			throws AppException {

		if (isOrderByInsertionDateParameterValid(orderByInsertionDate)) {
			throw new AppException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please set either ASC or DESC for the orderByInsertionDate parameter",
					null, AppConstants.DASH_POST_URL);
		}
		List<TransferApplicationEntity> applictions = transferApplicationDao
				.getTransferApplications(orderByInsertionDate);

		return getTransferApplicationsFromEntities(applictions);
	}

	private boolean isOrderByInsertionDateParameterValid(
			String orderByInsertionDate) {
		return orderByInsertionDate != null
				&& !("ASC".equalsIgnoreCase(orderByInsertionDate) || "DESC"
						.equalsIgnoreCase(orderByInsertionDate));
	}

	@Override
	public TransferApplication getTransferApplicationById(Long id) throws AppException {
		TransferApplicationEntity transferApplicationById = transferApplicationDao
				.getTransferApplicationById(id);
		if (transferApplicationById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404, "The object you requested with id " + id
							+ " was not found in the database",
					"Verify the existence of the object with the id " + id
							+ " in the database", AppConstants.DASH_POST_URL);
		}

		return new TransferApplication(transferApplicationDao.getTransferApplicationById(id));
	}

	private List<TransferApplication> getTransferApplicationsFromEntities(
			List<TransferApplicationEntity> transferApplicationEntities) {
		List<TransferApplication> response = new ArrayList<TransferApplication>();
		for (TransferApplicationEntity transferApplicationEntity : transferApplicationEntities) {
			response.add(new TransferApplication(transferApplicationEntity));
		}

		return response;
	}

	/********************* UPDATE-related methods implementation ***********************/
	@Override
	@Transactional
	public void updateFullyTransferApplication(TransferApplication transferApplication)
			throws AppException {
		// do a validation to verify FULL update with PUT
		if (isFullUpdate(transferApplication)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
					400, "Please specify all properties for Full UPDATE",
					"required properties - name", AppConstants.DASH_POST_URL);
		}

		TransferApplication verifyTransferApplicationExistenceById = verifyTransferApplicationExistenceById(transferApplication
				.getId());
		if (verifyTransferApplicationExistenceById == null) {
			throw new AppException(
					Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ transferApplication.getId(), AppConstants.DASH_POST_URL);
		}

		transferApplicationDao.updateTransferApplication(new TransferApplicationEntity(transferApplication));
	}

	/**
	 * Verifies the "completeness" of the transferApplication resource sent over the
	 * wire
	 *
	 * @param TransferApplication
	 * @return
	 */
	private boolean isFullUpdate(TransferApplication transferApplication) {
		return transferApplication.getId() == null;
	}

	/********************* DELETE-related methods implementation ***********************/

	@Override
	@Transactional
	public void deleteTransferApplication(TransferApplication transferApplication) {
		transferApplicationDao.deleteTransferApplication(transferApplication);
		aclController.deleteACL(transferApplication);
	}

	@Override
	@Transactional
	// TODO: This shouldn't exist? If it must, then it needs to accept a list of
	// transferApplications to delete
	public void deleteTransferApplications() {
		transferApplicationDao.deleteTransferApplications();
	}

	@Override
	// TODO: This doesn't need to exist. It is the exact same thing as
	// getTransferApplicationById(Long)
	public TransferApplication verifyTransferApplicationExistenceById(Long id) {
		TransferApplicationEntity transferApplicationById = transferApplicationDao
				.getTransferApplicationById(id);
		if (transferApplicationById == null) {
			return null;
		} else {
			return new TransferApplication(transferApplicationById);
		}
	}

	@Override
	@Transactional
	public void updatePartiallyTransferApplication(TransferApplication transferApplication)
			throws AppException {
		// do a validation to verify existence of the resource
		TransferApplication verifyTransferApplicationExistenceById = verifyTransferApplicationExistenceById(transferApplication
				.getId());
		if (verifyTransferApplicationExistenceById == null) {
			throw new AppException(
					Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ transferApplication.getId(), AppConstants.DASH_POST_URL);
		}
		copyPartialProperties(verifyTransferApplicationExistenceById, transferApplication);

		transferApplicationDao.updateTransferApplication(new TransferApplicationEntity(
				verifyTransferApplicationExistenceById));

	}

	private void copyPartialProperties(
			TransferApplication verifyTransferApplicationExistenceById, TransferApplication transferApplication) {

		BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyTransferApplicationExistenceById, transferApplication);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUploadFile(String uploadedFileLocation,
			TransferApplication transferApplication) throws AppException {
		Path path = Paths.get(uploadedFileLocation);
		try {
			Files.delete(path);
		} catch (NoSuchFileException x) {
			x.printStackTrace();
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404, "NoSuchFileException thrown, Operation unsuccesful.",
					"Please ensure the file you are attempting to"
							+ " delete exists at " + path + ".",
					AppConstants.DASH_POST_URL);

		} catch (DirectoryNotEmptyException x) {
			x.printStackTrace();
			throw new AppException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					404,
					"DirectoryNotEmptyException thrown, operation unsuccesful.",
					"This method should not attempt to delete,"
							+ " This should be considered a very serious error. Occured at "
							+ path + ".", AppConstants.DASH_POST_URL);
		} catch (IOException x) {
			x.printStackTrace();
			throw new AppException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					500,
					"IOException thrown and the designated file was not deleted.",
					" Permission problems occured at " + path + ".",
					AppConstants.DASH_POST_URL);
		}

	}

	@Override
	public List<String> getFileNames(TransferApplication transferApplication) {
		List<String> results = new ArrayList<String>();

		File[] files = new File(AppConstants.APPLICATION_UPLOAD_LOCATION_FOLDER
				+ "/" + transferApplication.getDocument_folder()).listFiles();
		// If this pathname does not denote a directory, then listFiles()
		// returns null.

		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					results.add(file.getName());
				}
			}
		}
		return results;
	}

	
}
