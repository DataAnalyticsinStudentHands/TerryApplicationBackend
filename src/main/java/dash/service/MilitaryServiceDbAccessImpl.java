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

import dash.dao.MilitaryDao;
import dash.dao.MilitaryEntity;
import dash.errorhandling.AppException;
import dash.filters.AppConstants;
import dash.helpers.NullAwareBeanUtilsBean;
import dash.pojo.Military;
import dash.security.CustomPermission;
import dash.security.GenericAclController;

@Component("militaryService")
public class MilitaryServiceDbAccessImpl extends ApplicationObjectSupport implements
MilitaryService {

	@Autowired
	MilitaryDao militaryDao;

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private GenericAclController<Military> aclController;


	/********************* Create related methods implementation ***********************/
	@Override
	@Transactional
	public Long createMilitary(Military military) throws AppException {

		validateInputForCreation(military);

		long militaryId = militaryDao.createMilitary(new MilitaryEntity(military));
		military.setId(militaryId);
		
		aclController.createACL(military);
		aclController.createAce(military, CustomPermission.READ);
		aclController.createAce(military, CustomPermission.WRITE);
		aclController.createAce(military, CustomPermission.DELETE);
		
		return militaryId;
	}

	private void validateInputForCreation(Military military) throws AppException {
		if (military.getBranch() == null || military.getApplication_id() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the name is properly generated/set",
					AppConstants.DASH_POST_URL);
		}		
	}

		// ******************** Read related methods implementation **********************
	@Override
	public List<Military> getMilitary(String orderByInsertionDate) throws AppException {

		if(isOrderByInsertionDateParameterValid(orderByInsertionDate)){
			throw new AppException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please set either ASC or DESC for the orderByInsertionDate parameter",
					null, AppConstants.DASH_POST_URL);
		}
		List<MilitaryEntity> militarys = militaryDao.getMilitary(orderByInsertionDate);

		return getMilitaryFromEntities(militarys);
	}

	private boolean isOrderByInsertionDateParameterValid(
			String orderByInsertionDate) {
		return orderByInsertionDate!=null
				&& !("ASC".equalsIgnoreCase(orderByInsertionDate) || "DESC".equalsIgnoreCase(orderByInsertionDate));
	}

	@Override
	public Military getMilitaryById(Long id) throws AppException {
		MilitaryEntity militaryById = militaryDao.getMilitaryById(id);
		if (militaryById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The object you requested with id " + id
					+ " was not found in the database",
					"Verify the existence of the object with the id " + id
					+ " in the database", AppConstants.DASH_POST_URL);
		}

		return new Military(militaryDao.getMilitaryById(id));
	}
	
	@Override
	public List<Military> getMilitaryByAppId(Long appId) throws AppException {
		
		List<MilitaryEntity> militarys = militaryDao.getMilitaryByAppId(appId);
		
		return getMilitaryFromEntities(militarys);
	}

	private List<Military> getMilitaryFromEntities(List<MilitaryEntity> militaryEntities) {
		List<Military> response = new ArrayList<Military>();
		for (MilitaryEntity militaryEntity : militaryEntities) {
			response.add(new Military(militaryEntity));
		}

		return response;
	}

	/********************* UPDATE-related methods implementation ***********************/
	@Override
	@Transactional
	public void updateFullyMilitary(Military military) throws AppException {
		//do a validation to verify FULL update with PUT
		if (isFullUpdate(military)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please specify all properties for Full UPDATE",
					"required properties - name",
					AppConstants.DASH_POST_URL);
		}

		Military verifyMilitaryExistenceById = verifyMilitaryExistenceById(military
				.getId());
		if (verifyMilitaryExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ military.getId(),
							AppConstants.DASH_POST_URL);
		}

		militaryDao.updateMilitary(new MilitaryEntity(military));
	}

	/**
	 * Verifies the "completeness" of the military resource sent over the wire
	 *
	 * @param Military
	 * @return
	 */
	private boolean isFullUpdate(Military military) {
		return military.getId() == null
				|| military.getBranch() == null;
	}

	/********************* DELETE-related methods implementation ***********************/

	@Override
	@Transactional
	public void deleteMilitary(Military military) {

		militaryDao.deleteMilitary(military);
		aclController.deleteACL(military);

	}

	@Override
	// TODO: This doesn't need to exist. It is the exact same thing as
	// getMilitaryById(Long)
	public Military verifyMilitaryExistenceById(Long id) {
		MilitaryEntity militaryById = militaryDao.getMilitaryById(id);
		if (militaryById == null) {
			return null;
		} else {
			return new Military(militaryById);
		}
	}

	@Override
	@Transactional
	public void updatePartiallyMilitary(Military military) throws AppException {
		//do a validation to verify existence of the resource
		Military verifyMilitaryExistenceById = verifyMilitaryExistenceById(military.getId());
		if (verifyMilitaryExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ military.getId(), AppConstants.DASH_POST_URL);
		}
		copyPartialProperties(verifyMilitaryExistenceById, military);
		
		militaryDao.updateMilitary(new MilitaryEntity(verifyMilitaryExistenceById));
	}

	private void copyPartialProperties(Military verifyMilitaryExistenceById, Military military) {

		BeanUtilsBean notNull=new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyMilitaryExistenceById, military);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
