package dash.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import dash.errorhandling.AppException;
import dash.pojo.TransferApplication;

public interface TransferApplicationService {
	/*
	 * ******************** Create related methods **********************
	 *
	 *Create a new image and set the current user as owner and manager.
	 */
	public Long createTransferApplication(TransferApplication transferApplication) throws AppException;	

	@PreAuthorize("hasPermission(#transferApplication, 'write') or hasRole('ROLE_ADMIN')")
	public void uploadFile(InputStream uploadedInputStream,
			String uploadedFileLocation, TransferApplication transferApplication) throws AppException;
	
	/*
	 * ******************* Read related methods ********************
	 */
	/**
	 *
	 * @param orderByInsertionDate
	 *            - if set, it represents the order by criteria (ASC or DESC)
	 *            for displaying objects
	 * @return list with objects corresponding to search criteria
	 * @throws AppException
	 */
	@PostFilter("hasPermission(filterObject, 'READ') or hasRole('ROLE_ADMIN')")
	public List<TransferApplication> getTransferApplications(String orderByInsertionDate) throws AppException;

	/**
	 * Returns a transferApplication given its id
	 *
	 * @param id
	 * @return
	 * @throws AppException
	 */
	@PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
	public TransferApplication getTransferApplicationById(Long id) throws AppException;
	
	@PreAuthorize("hasPermission(#transferApplication, 'read') or hasRole('ROLE_ADMIN')")
	public File getUploadFile(String uploadedFileLocation, TransferApplication transferApplication) throws AppException;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<TransferApplication> getTransferApplicationsWithFileNames() throws AppException;
	
	public void createPDF(String inputFilePath, String outputFilePath) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 */
	@PreAuthorize("hasPermission(#transferApplication, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updateFullyTransferApplication(TransferApplication transferApplication) throws AppException;

	@PreAuthorize("hasPermission(#transferApplication, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updatePartiallyTransferApplication(TransferApplication transferApplication) throws AppException;
	
	/*
	 * ******************** Delete related methods **********************
	 */
	@PreAuthorize("hasPermission(#transferApplication, 'delete') or hasRole('ROLE_ADMIN')")
	public void deleteTransferApplication(TransferApplication transferApplication);
	
	/** removes all transferApplications
	 * DO NOT USE, IMPROPERLY UPDATES ACL_OBJECT table
	 * Functional but does not destroy old acl's which doesnt hurt anything
	 * but they will take up space if this is commonly used */
	@PreAuthorize("hasRole('ROLE_ROOT')")
	public void deleteTransferApplications();	

	
	@PreAuthorize("hasPermission(#transferApplication, 'delete') or hasRole('ROLE_ADMIN')")
	public void deleteUploadFile(String uploadedFileLocation, TransferApplication transferApplication) throws AppException;
	/*
	 * ******************** Helper methods **********************
	 */
	// TODO: This also should not exist, or it should be changed to
	// private/protected. Redundant
	// Could be made a boolean so it was not a security vulnerability
	public TransferApplication verifyTransferApplicationExistenceById(Long id);
	
	@PreAuthorize("hasPermission(#transferApplication, 'read') or hasRole('ROLE_ADMIN')")
	public List<String> getFileNames(TransferApplication transferApplication);	

}
