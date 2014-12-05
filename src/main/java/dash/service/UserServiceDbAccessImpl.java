 package dash.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dash.dao.UserDao;
import dash.dao.UserEntity;
import dash.errorhandling.AppException;
import dash.filters.AppConstants;
import dash.helpers.NullAwareBeanUtilsBean;
import dash.pojo.User;
import dash.security.UserLoginController;

@Component("userService")
public class UserServiceDbAccessImpl extends ApplicationObjectSupport implements
UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private UserLoginController authoritiesController;

	public static final String userRole = "ROLE_USER";


	/********************* Create related methods implementation ***********************/
	@Override
	@Transactional
	public Long createUser(User user) throws AppException {

		validateInputForCreation(user);

		//verify existence of resource in the db (feed must be unique)
		UserEntity userByName = userDao.getUserByName(user.getUsername());
		if (userByName != null) {
			throw new AppException(
					Response.Status.CONFLICT.getStatusCode(),
					409,
					"User with username already existing in the database with the id "
							+ userByName.getId(),
							"Please verify that the username and password are properly generated",
							AppConstants.DASH_POST_URL);
		}

		//create User ID (actually this done via auto increment in DB)
		long userId = userDao.createUser(new UserEntity(user));
		user.setId(userId);
		
		String fileName = user.getUsername() + ".png";
		 
        int hashcode = fileName.hashCode();
        int mask = 255;
        int firstDir = hashcode & mask;
        int secondDir = (hashcode >> 8) & mask;
 
        StringBuilder path = new StringBuilder(File.separator);
        path.append(String.format("%03d", firstDir));
        path.append(File.separator);
        path.append(String.format("%03d", secondDir));
        path.append(File.separator);
        path.append(fileName);
 
        System.out.println(path);
		
        user.setPicture(fileName);
		
		//create user object		
		authoritiesController.create(user, userRole);
		
		createUserACL(user, new PrincipalSid(user.getUsername()));	
		
		return userId;
	}

	private void validateInputForCreation(User user) throws AppException {
		if (user.getUsername() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the username is properly generated/set",
					AppConstants.DASH_POST_URL);
		}
		if (user.getPassword() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the password is properly generated/set",
					AppConstants.DASH_POST_URL);
		}
		//etc...
	}

	@Override
	@Transactional
	public void createUsers(List<User> users) throws AppException {
		for (User user : users) {
			createUser(user);
		}
	}


	// ******************** Read related methods implementation **********************
	@Override
	public List<User> getUsers(String orderByInsertionDate,
			Integer numberDaysToLookBack) throws AppException {

		//verify optional parameter numberDaysToLookBack first
		if(numberDaysToLookBack!=null){
			List<UserEntity> recentUsers = userDao
					.getRecentUsers(numberDaysToLookBack);
			return getUsersFromEntities(recentUsers);
		}

		if(isOrderByInsertionDateParameterValid(orderByInsertionDate)){
			throw new AppException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please set either ASC or DESC for the orderByInsertionDate parameter",
					null, AppConstants.DASH_POST_URL);
		}
		List<UserEntity> users = userDao.getUsers(orderByInsertionDate);

		return getUsersFromEntities(users);
	}
	
	@Override
	public List<User> getMyUser() throws AppException {
		return getUsers(null, null);
	}

	private boolean isOrderByInsertionDateParameterValid(
			String orderByInsertionDate) {
		return orderByInsertionDate!=null
				&& !("ASC".equalsIgnoreCase(orderByInsertionDate) || "DESC".equalsIgnoreCase(orderByInsertionDate));
	}

	@Override
	public User getUserById(Long id) throws AppException {
		UserEntity userById = userDao.getUserById(id);
		if (userById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The user you requested with id " + id
					+ " was not found in the database",
					"Verify the existence of the user with the id " + id
					+ " in the database", AppConstants.DASH_POST_URL);
		}

		return new User(userDao.getUserById(id));
	}

	private List<User> getUsersFromEntities(List<UserEntity> userEntities) {
		List<User> response = new ArrayList<User>();
		for (UserEntity userEntity : userEntities) {
			response.add(new User(userEntity));
		}

		return response;
	}

	public List<User> getRecentUsers(int numberOfDaysToLookBack) {
		List<UserEntity> recentUsers = userDao
				.getRecentUsers(numberOfDaysToLookBack);

		return getUsersFromEntities(recentUsers);
	}
	
	@Override
	public List<String> getRole(User user) {
		ArrayList<String> tempRole = new ArrayList<String>();
		tempRole.add(userDao.getRoleByName(user.getUsername()));
		return tempRole;
	}

	@Override
	public int getNumberOfUsers() {
		int totalNumber = userDao.getNumberOfUsers();

		return totalNumber;

	}



	/********************* UPDATE-related methods implementation ***********************/
	@Override
	@Transactional
	public void updateFullyUser(User user) throws AppException {
		//do a validation to verify FULL update with PUT
		if (isFullUpdate(user)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
					400,
					"Please specify all properties for Full UPDATE",
					"required properties - id, username, password, firstName, lastName, city, homePhone, cellPhone, email, picture",
					AppConstants.DASH_POST_URL);
		}

		User verifyUserExistenceById = verifyUserExistenceById(user
				.getId());
		if (verifyUserExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ user.getId(),
							AppConstants.DASH_POST_URL);
		}

		userDao.updateUser(new UserEntity(user));
	}

	/**
	 * Verifies the "completeness" of user resource sent over the wire
	 *
	 * @param User
	 * @return
	 */
	private boolean isFullUpdate(User user) {
		return user.getId() == null
				|| user.getUsername() == null
				|| user.getPassword() == null
				|| user.getFirstName() == null
				|| user.getLastName() == null
				|| user.getCity() == null
				|| user.getHomePhone() == null
				|| user.getCellPhone() == null
				|| user.getEmail() == null
				|| user.getPicture() == null;
	}
	
	@Override
	@Transactional
	public void resetPassword(User user) throws AppException{
		User verifyUserExistenceById = verifyUserExistenceById(user.getId());
		if (verifyUserExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ user.getId(), AppConstants.DASH_POST_URL);
		}else
		{
			authoritiesController.passwordReset(user);	
		}
	}

	/********************* DELETE-related methods implementation ***********************/

	@Override
	@Transactional
	public void deleteUser(User user) {

		userDao.deleteUserById(user);

	}

	@Override
	@Transactional
	// TODO: This shouldn't exist? If it must, then it needs to accept a list of
	// Users to delete
	public void deleteUsers() {
		userDao.deleteUsers();
	}

	@Override
	// TODO: This doesnt need to exist. It is the exact same thing as
	// getUserById(Long)
	public User verifyUserExistenceById(Long id) {
		UserEntity userById = userDao.getUserById(id);
		if (userById == null) {
			return null;
		} else {
			return new User(userById);
		}
	}

	@Override
	@Transactional
	public void updatePartiallyUser(User user) throws AppException {
		//do a validation to verify existence of the resource
		User verifyUserExistenceById = verifyUserExistenceById(user.getId());
		if (verifyUserExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
					404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - "
							+ user.getId(), AppConstants.DASH_POST_URL);
		}
		copyPartialProperties(verifyUserExistenceById, user);
		userDao.updateUser(new UserEntity(verifyUserExistenceById));

	}

	private void copyPartialProperties(User verifyUserExistenceById, User user) {

		BeanUtilsBean notNull=new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyUserExistenceById, user);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//****************** Methods for Acl *****************
	
	/**
	 * Creates/Updates the ACL of user.Is also an example of how to implement class specific ACL helper methods.
	 * @param user
	 * @param recipient
	 */ 
	public void createUserACL(User user, Sid recipient) {
		MutableAcl acl;
		ObjectIdentity oid = new ObjectIdentityImpl(User.class,
				user.getId());

		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			acl = mutableAclService.createAcl(oid);
		}
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, recipient,
				true);
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, recipient,
				true);
		acl.insertAce(acl.getEntries().size(), BasePermission.DELETE,
				recipient, true);
		mutableAclService.updateAcl(acl);
		acl.setOwner(recipient);
		mutableAclService.updateAcl(acl);

		logger.debug("Added permission " + "Read, Write, Delete" + " for Sid "
				+ recipient
				+ " contact " + user);

	}
	
	@Override
	@Transactional
	public void setRoleUser(User user) {
		userDao.updateUserRole("ROLE_USER", user.getUsername());
	}

	@Override
	@Transactional
	public void setRoleModerator(User user) {
		userDao.updateUserRole("ROLE_MODERATOR", user.getUsername());
	}

	@Override
	@Transactional
	public void setRoleAdmin(User user) {
		userDao.updateUserRole("ROLE_ADMIN", user.getUsername());		
	}
}
