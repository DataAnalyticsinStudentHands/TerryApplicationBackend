package dash.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import dash.errorhandling.AppException;
import dash.pojo.Institution;

public interface InstitutionService {
	/*
	 * ******************** Create related methods **********************
	 *
	 *Create a new image and set the current user as owner and manager.
	 */
	public Long createInstitution(Institution institution) throws AppException;	

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
	public List<Institution> getInstitution(String orderByInsertionDate) throws AppException;

	/**
	 * Returns institution given its id
	 *
	 * @param id
	 * @return
	 * @throws AppException
	 */
	@PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
	public Institution getInstitutionById(Long id) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 */
	@PreAuthorize("hasPermission(#institution, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updateFullyInstitution(Institution application) throws AppException;

	@PreAuthorize("hasPermission(#institution, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updatePartiallyInstitution(Institution application) throws AppException;
	
	@PostFilter("hasRole('ROLE_ADMIN')")
	public List<Institution> getInstitutionByAppId(Long appId) throws AppException;
	
	/*
	 * ******************** Delete related methods **********************
	 */
	@PreAuthorize("hasPermission(#institution, 'delete') or hasRole('ROLE_ADMIN')")
	public void deleteInstitution(Institution institution);
	
	/*
	 * ******************** Helper methods **********************
	 */
	// TODO: This also should not exist, or it should be changed to
	// private/protected. Redundant
	// Could be made a boolean so it was not a security vulnerability
	public Institution verifyInstitutionExistenceById(Long id);	

}
