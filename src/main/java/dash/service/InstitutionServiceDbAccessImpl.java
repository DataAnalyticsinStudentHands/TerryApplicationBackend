package dash.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dash.dao.InstitutionDao;
import dash.dao.InstitutionEntity;
import dash.errorhandling.AppException;
import dash.filters.AppConstants;
import dash.helpers.NullAwareBeanUtilsBean;
import dash.pojo.Institution;
import dash.security.CustomPermission;
import dash.security.GenericAclController;

@Component("institutionService")
public class InstitutionServiceDbAccessImpl extends ApplicationObjectSupport implements
InstitutionService {

	@Autowired
	InstitutionDao institutionDao;

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private GenericAclController<Institution> aclController;


	/********************* Create related methods implementation ***********************/
	@Override
	@Transactional
	public Long createInstitution(Institution institution) throws AppException {

		validateInputForCreation(institution);

		long institutionId = institutionDao.createInstitution(new InstitutionEntity(institution));
		institution.setId(institutionId);
		
		aclController.createACL(institution);
		aclController.createAce(institution, CustomPermission.READ);
		aclController.createAce(institution, CustomPermission.WRITE);
		aclController.createAce(institution, CustomPermission.DELETE);
		
		return institutionId;
	}

	private void validateInputForCreation(Institution institution) throws AppException {
		if (institution.getName() == null || institution.getApplication_id() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the name is properly generated/set",
					AppConstants.DASH_POST_URL);
		}		
	}

		// ******************** Read related methods implementation **********************
	@Override
	public List<Institution> getInstitution(String orderByInsertionDate) throws AppException {

		if(isOrderByInsertionDateParameterValid(orderByInsertionDate)){
			throw new AppException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please set either ASC or DESC for the orderByInsertionDate parameter",
					null, AppConstants.DASH_POST_URL);
		}
		List<InstitutionEntity> institutions = institutionDao.getInstitution(orderByInsertionDate);

		return getInstitutionFromEntities(institutions);
	}

	private boolean isOrderByInsertionDateParameterValid(
			String orderByInsertionDate) {
		return orderByInsertionDate!=null
				&& !("ASC".equalsIgnoreCase(orderByInsertionDate) || "DESC".equalsIgnoreCase(orderByInsertionDate));
	}

	@Override
	public Institution getInstitutionById(Long id) throws AppException {
		InstitutionEntity institutionById = institutionDao.getInstitutionById(id);
		if (institutionById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The object you requested with id " + id
					+ " was not found in the database",
					"Verify the existence of the object with the id " + id
					+ " in the database", AppConstants.DASH_POST_URL);
		}

		return new Institution(institutionDao.getInstitutionById(id));
	}
	
	@Override
	public List<Institution> getInstitutionByAppId(Long appId) throws AppException {
		
		List<InstitutionEntity> institutions = institutionDao.getInstitutionByAppId(appId);
		
		return getInstitutionFromEntities(institutions);
	}

	private List<Institution> getInstitutionFromEntities(List<InstitutionEntity> institutionEntities) {
		List<Institution> response = new ArrayList<Institution>();
		for (InstitutionEntity institutionEntity : institutionEntities) {
			response.add(new Institution(institutionEntity));
		}

		return response;
	}

	/********************* UPDATE-related methods implementation ***********************/
	@Override
	@Transactional
	public void updateFullyInstitution(Institution institution) throws AppException {
		//do a validation to verify FULL update with PUT
		if (isFullUpdate(institution)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please specify all properties for Full UPDATE",
					"required properties - name",
					AppConstants.DASH_POST_URL);
		}

		Institution verifyInstitutionExistenceById = verifyInstitutionExistenceById(institution
				.getId());
		if (verifyInstitutionExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ institution.getId(),
							AppConstants.DASH_POST_URL);
		}

		institutionDao.updateInstitution(new InstitutionEntity(institution));
	}

	/**
	 * Verifies the "completeness" of the institution resource sent over the wire
	 *
	 * @param Institution
	 * @return
	 */
	private boolean isFullUpdate(Institution institution) {
		return institution.getId() == null
				|| institution.getName() == null;
	}

	/********************* DELETE-related methods implementation ***********************/

	@Override
	@Transactional
	public void deleteInstitution(Institution institution) {

		institutionDao.deleteInstitution(institution);
		aclController.deleteACL(institution);

	}

	@Override
	// TODO: This doesn't need to exist. It is the exact same thing as
	// getInstitutionById(Long)
	public Institution verifyInstitutionExistenceById(Long id) {
		InstitutionEntity institutionById = institutionDao.getInstitutionById(id);
		if (institutionById == null) {
			return null;
		} else {
			return new Institution(institutionById);
		}
	}

	@Override
	@Transactional
	public void updatePartiallyInstitution(Institution institution) throws AppException {
		//do a validation to verify existence of the resource
		Institution verifyInstitutionExistenceById = verifyInstitutionExistenceById(institution.getId());
		if (verifyInstitutionExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ institution.getId(), AppConstants.DASH_POST_URL);
		}
		copyPartialProperties(verifyInstitutionExistenceById, institution);
		
		institutionDao.updateInstitution(new InstitutionEntity(verifyInstitutionExistenceById));
	}

	private void copyPartialProperties(Institution verifyInstitutionExistenceById, Institution institution) {

		BeanUtilsBean notNull=new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyInstitutionExistenceById, institution);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
