package dash.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dash.pojo.SurfApplication;

public class SurfApplicationDaoJPA2Impl implements SurfApplicationDao {

	@PersistenceContext(unitName = "dashPersistence")
	private EntityManager entityManager;

	@Override
	public List<SurfApplicationEntity> getApplications(String orderByInsertionDate) {
		String sqlString = null;
		if (orderByInsertionDate != null) {
			sqlString = "SELECT o FROM ApplicationEntity o"
					+ " ORDER BY o.insertionDate " + orderByInsertionDate;
		} else {
			sqlString = "SELECT o FROM ApplicationEntity o";
		}
		TypedQuery<SurfApplicationEntity> query = entityManager.createQuery(
				sqlString, SurfApplicationEntity.class);

		return query.getResultList();
	}

	@Override
	public SurfApplicationEntity getApplicationById(Long id) {

		try {
			String qlString = "SELECT u FROM ApplicationEntity u WHERE u.id = ?1";
			TypedQuery<SurfApplicationEntity> query = entityManager.createQuery(
					qlString, SurfApplicationEntity.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public SurfApplicationEntity getApplicationByName(String name) {

		try {
			String qlString = "SELECT o FROM ApplicationEntity o WHERE o.name = ?1";
			TypedQuery<SurfApplicationEntity> query = entityManager.createQuery(
					qlString, SurfApplicationEntity.class);
			query.setParameter(1, name);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteApplication(SurfApplication applicationPojo) {

		SurfApplicationEntity application = entityManager.find(
				SurfApplicationEntity.class, applicationPojo.getId());
		entityManager.remove(application);
	}

	@Override
	public Long createApplication(SurfApplicationEntity application) {

		application.setCreation_timestamp(new Date());
		
		entityManager.persist(application);
		entityManager.flush();// force insert to receive the id of the
								// application
		
		entityManager.merge(application);
	
		// Give admin over new application to the new user
		return application.getId();
	}
		

	@Override
	public void updateApplication(SurfApplicationEntity application) {
		// TODO think about partial update and full update
		entityManager.merge(application);
	}

	@Override
	public void deleteApplications() {
		Query query = entityManager
				.createNativeQuery("TRUNCATE TABLE surfapplications");
		query.executeUpdate();
	}

}
