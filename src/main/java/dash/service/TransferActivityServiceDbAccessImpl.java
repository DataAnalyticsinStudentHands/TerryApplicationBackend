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

import dash.dao.TransferActivityDao;
import dash.dao.TransferActivityEntity;
import dash.errorhandling.AppException;
import dash.filters.AppConstants;
import dash.helpers.NullAwareBeanUtilsBean;
import dash.pojo.TransferActivity;
import dash.security.CustomPermission;
import dash.security.GenericAclController;

@Component("transferActivityService")
public class TransferActivityServiceDbAccessImpl extends ApplicationObjectSupport implements
TransferActivityService {

	@Autowired
	TransferActivityDao transferActivityDao;

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private GenericAclController<TransferActivity> aclController;


	/********************* Create related methods implementation ***********************/
	@Override
	@Transactional
	public Long createTransferActivity(TransferActivity transferActivity) throws AppException {

		validateInputForCreation(transferActivity);

		long transferActivityId = transferActivityDao.createTransferActivity(new TransferActivityEntity(transferActivity));
		transferActivity.setId(transferActivityId);
		
		aclController.createACL(transferActivity);
		aclController.createAce(transferActivity, CustomPermission.READ);
		aclController.createAce(transferActivity, CustomPermission.WRITE);
		aclController.createAce(transferActivity, CustomPermission.DELETE);
		
		return transferActivityId;
	}

	private void validateInputForCreation(TransferActivity transferActivity) throws AppException {
		if (transferActivity.getActivity() == null || transferActivity.getApplication_id() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the name is properly generated/set",
					AppConstants.DASH_POST_URL);
		}		
	}

		// ******************** Read related methods implementation **********************
	@Override
	public List<TransferActivity> getTransferActivity(String orderByInsertionDate) throws AppException {

		if(isOrderByInsertionDateParameterValid(orderByInsertionDate)){
			throw new AppException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please set either ASC or DESC for the orderByInsertionDate parameter",
					null, AppConstants.DASH_POST_URL);
		}
		List<TransferActivityEntity> transferActivitys = transferActivityDao.getTransferActivity(orderByInsertionDate);

		return getTransferActivityFromEntities(transferActivitys);
	}

	private boolean isOrderByInsertionDateParameterValid(
			String orderByInsertionDate) {
		return orderByInsertionDate!=null
				&& !("ASC".equalsIgnoreCase(orderByInsertionDate) || "DESC".equalsIgnoreCase(orderByInsertionDate));
	}

	@Override
	public TransferActivity getTransferActivityById(Long id) throws AppException {
		TransferActivityEntity transferActivityById = transferActivityDao.getTransferActivityById(id);
		if (transferActivityById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The object you requested with id " + id
					+ " was not found in the database",
					"Verify the existence of the object with the id " + id
					+ " in the database", AppConstants.DASH_POST_URL);
		}

		return new TransferActivity(transferActivityDao.getTransferActivityById(id));
	}
	
	@Override
	public List<TransferActivity> getTransferActivityByAppId(Long appId) throws AppException {
		
		List<TransferActivityEntity> transferActivitys = transferActivityDao.getTransferActivityByAppId(appId);
		
		return getTransferActivityFromEntities(transferActivitys);
	}

	private List<TransferActivity> getTransferActivityFromEntities(List<TransferActivityEntity> transferActivityEntities) {
		List<TransferActivity> response = new ArrayList<TransferActivity>();
		for (TransferActivityEntity transferActivityEntity : transferActivityEntities) {
			response.add(new TransferActivity(transferActivityEntity));
		}

		return response;
	}

	/********************* UPDATE-related methods implementation ***********************/
	@Override
	@Transactional
	public void updateFullyTransferActivity(TransferActivity transferActivity) throws AppException {
		//do a validation to verify FULL update with PUT
		if (isFullUpdate(transferActivity)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please specify all properties for Full UPDATE",
					"required properties - name",
					AppConstants.DASH_POST_URL);
		}

		TransferActivity verifyTransferActivityExistenceById = verifyTransferActivityExistenceById(transferActivity
				.getId());
		if (verifyTransferActivityExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ transferActivity.getId(),
							AppConstants.DASH_POST_URL);
		}

		transferActivityDao.updateTransferActivity(new TransferActivityEntity(transferActivity));
	}

	/**
	 * Verifies the "completeness" of the transferActivity resource sent over the wire
	 *
	 * @param TransferActivity
	 * @return
	 */
	private boolean isFullUpdate(TransferActivity transferActivity) {
		return transferActivity.getId() == null
				|| transferActivity.getActivity() == null;
	}

	/********************* DELETE-related methods implementation ***********************/

	@Override
	@Transactional
	public void deleteTransferActivity(TransferActivity transferActivity) {

		transferActivityDao.deleteTransferActivity(transferActivity);
		aclController.deleteACL(transferActivity);

	}

	@Override
	@Transactional
	// TODO: This shouldn't exist? If it must, then it needs to accept a list of
	// TransferActivitys to delete
	public void deleteTransferActivitys() {
		transferActivityDao.deleteTransferActivitys();
	}

	@Override
	// TODO: This doesn't need to exist. It is the exact same thing as
	// getTransferActivityById(Long)
	public TransferActivity verifyTransferActivityExistenceById(Long id) {
		TransferActivityEntity transferActivityById = transferActivityDao.getTransferActivityById(id);
		if (transferActivityById == null) {
			return null;
		} else {
			return new TransferActivity(transferActivityById);
		}
	}

	@Override
	@Transactional
	public void updatePartiallyTransferActivity(TransferActivity transferActivity) throws AppException {
		//do a validation to verify existence of the resource
		TransferActivity verifyTransferActivityExistenceById = verifyTransferActivityExistenceById(transferActivity.getId());
		if (verifyTransferActivityExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ transferActivity.getId(), AppConstants.DASH_POST_URL);
		}
		copyPartialProperties(verifyTransferActivityExistenceById, transferActivity);
		
		transferActivityDao.updateTransferActivity(new TransferActivityEntity(verifyTransferActivityExistenceById));
	}

	private void copyPartialProperties(TransferActivity verifyTransferActivityExistenceById, TransferActivity transferActivity) {

		BeanUtilsBean notNull=new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyTransferActivityExistenceById, transferActivity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
