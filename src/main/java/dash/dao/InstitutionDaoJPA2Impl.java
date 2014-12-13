package dash.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import dash.pojo.Institution;

@Component("institutionDao")
public class InstitutionDaoJPA2Impl implements InstitutionDao {

	@PersistenceContext(unitName = "dashPersistence")
	private EntityManager entityManager;

	@Override
	public List<InstitutionEntity> getInstitution(String orderByInsertionDate) {
		String sqlString = null;
		if (orderByInsertionDate != null) {
			sqlString = "SELECT o FROM InstitutionEntity o"
					+ " ORDER BY o.insertionDate " + orderByInsertionDate;
		} else {
			sqlString = "SELECT o FROM InstitutionEntity o";
		}
		TypedQuery<InstitutionEntity> query = entityManager.createQuery(
				sqlString, InstitutionEntity.class);

		return query.getResultList();
	}

	@Override
	public InstitutionEntity getInstitutionById(Long id) {

		try {
			String qlString = "SELECT u FROM InstitutionEntity u WHERE u.id = ?1";
			TypedQuery<InstitutionEntity> query = entityManager.createQuery(
					qlString, InstitutionEntity.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<InstitutionEntity> getInstitutionByAppId(Long appId) {
		
		try {
			String qlString = "SELECT u FROM InstitutionEntity u WHERE u.application_id = ?1";
			TypedQuery<InstitutionEntity> query = entityManager.createQuery(
					qlString, InstitutionEntity.class);
			query.setParameter(1, appId);

			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteInstitution(Institution institutionPojo) {

		InstitutionEntity institution = entityManager.find(
				InstitutionEntity.class, institutionPojo.getId());
		entityManager.remove(institution);
	}

	@Override
	public Long createInstitution(InstitutionEntity institution) {

		entityManager.persist(institution);
		entityManager.flush();// force insert to receive the id of the
								// institution

		// Give admin over new institution to the user
		return institution.getId();
	}

	@Override
	public void updateInstitution(InstitutionEntity institution) {
		// TODO think about partial update and full update
		entityManager.merge(institution);
	}

	@Override
	public void deleteInstitutions() {
		Query query = entityManager
				.createNativeQuery("TRUNCATE TABLE institution");
		query.executeUpdate();
	}

}
