package dash.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import dash.errorhandling.AppException;
import dash.pojo.SurfApplication;

public interface SurfApplicationService {
	/*
	 * ******************** Create related methods **********************
	 *
	 *Create a new image and set the current user as owner and manager.
	 */
	public Long createApplication(SurfApplication application) throws AppException;	
	
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
	public List<SurfApplication> getApplications(String orderByInsertionDate) throws AppException;

	/**
	 * Returns a application given its id
	 *
	 * @param id
	 * @return
	 * @throws AppException
	 */
	@PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
	public SurfApplication getApplicationById(Long id) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 */
	@PreAuthorize("hasPermission(#application, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updateFullyApplication(SurfApplication application) throws AppException;

	@PreAuthorize("hasPermission(#application, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updatePartiallyApplication(SurfApplication application) throws AppException;
	
	/*
	 * ******************** Delete related methods **********************
	 */
	@PreAuthorize("hasPermission(#application, 'delete') or hasRole('ROLE_ADMIN')")
	public void deleteApplication(SurfApplication application);
	
	/** removes all applications
	 * DO NOT USE, IMPROPERLY UPDATES ACL_OBJECT table
	 * Functional but does not destroy old acl's which doesnt hurt anything
	 * but they will take up space if this is commonly used */
	@PreAuthorize("hasRole('ROLE_ROOT')")
	public void deleteApplications();	
	
	/*
	 * ******************** Helper methods **********************
	 */
	// TODO: This also should not exist, or it should be changed to
	// private/protected. Redundant
	// Could be made a boolean so it was not a security vulnerability
	public SurfApplication verifyApplicationExistenceById(Long id);	

}
