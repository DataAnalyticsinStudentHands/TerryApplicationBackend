package dash.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import dash.errorhandling.AppException;
import dash.pojo.Military;

public interface MilitaryService {
	/*
	 * ******************** Create related methods **********************
	 *
	 *Create a new image and set the current user as owner and manager.
	 */
	public Long createMilitary(Military military) throws AppException;	

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
	public List<Military> getMilitary(String orderByInsertionDate) throws AppException;

	/**
	 * Returns military given its id
	 *
	 * @param id
	 * @return
	 * @throws AppException
	 */
	@PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
	public Military getMilitaryById(Long id) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 */
	@PreAuthorize("hasPermission(#military, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updateFullyMilitary(Military application) throws AppException;

	@PreAuthorize("hasPermission(#military, 'WRITE') or hasRole('ROLE_ADMIN')")
	public void updatePartiallyMilitary(Military application) throws AppException;
	
	@PostFilter("hasRole('ROLE_ADMIN')")
	public List<Military> getMilitaryByAppId(Long appId) throws AppException;
	
	/*
	 * ******************** Delete related methods **********************
	 */
	@PreAuthorize("hasPermission(#military, 'delete') or hasRole('ROLE_ADMIN')")
	public void deleteMilitary(Military military);
	
	/*
	 * ******************** Helper methods **********************
	 */
	// TODO: This also should not exist, or it should be changed to
	// private/protected. Redundant
	// Could be made a boolean so it was not a security vulnerability
	public Military verifyMilitaryExistenceById(Long id);	

}
