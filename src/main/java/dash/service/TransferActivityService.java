package dash.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import dash.errorhandling.AppException;
import dash.pojo.TransferActivity;

public interface TransferActivityService {
	/*
	 * ******************** Create related methods **********************
	 *
	 *Create a new image and set the current user as owner and manager.
	 */
	public Long createTransferActivity(TransferActivity transferActivity) throws AppException;	

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
	public List<TransferActivity> getTransferActivity(String orderByInsertionDate) throws AppException;

	/**
	 * Returns transferActivity given its id
	 *
	 * @param id
	 * @return
	 * @throws AppException
	 */
	@PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
	public TransferActivity getTransferActivityById(Long id) throws AppException;
	
	@PostFilter("hasRole('ROLE_ADMIN')")
	public List<TransferActivity> getTransferActivityByAppId(Long appId) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 */
	@PreAuthorize("hasPermission(#transferActivity, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updateFullyTransferActivity(TransferActivity application) throws AppException;

	@PreAuthorize("hasPermission(#transferActivity, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updatePartiallyTransferActivity(TransferActivity application) throws AppException;
	
	/*
	 * ******************** Delete related methods **********************
	 */
	@PreAuthorize("hasPermission(#transferActivity, 'delete') or hasRole('ROLE_ADMIN')")
	public void deleteTransferActivity(TransferActivity transferActivity);
	
	/** removes all applications
	 * DO NOT USE, IMPROPERLY UPDATES ACL_OBJECT table
	 * Functional but does not destroy old acl's which doesnt hurt anything
	 * but they will take up space if this is commonly used */
	@PreAuthorize("hasRole('ROLE_ROOT')")
	public void deleteTransferActivitys();	

	/*
	 * ******************** Helper methods **********************
	 */
	// TODO: This also should not exist, or it should be changed to
	// private/protected. Redundant
	// Could be made a boolean so it was not a security vulnerability
	public TransferActivity verifyTransferActivityExistenceById(Long id);	

}
