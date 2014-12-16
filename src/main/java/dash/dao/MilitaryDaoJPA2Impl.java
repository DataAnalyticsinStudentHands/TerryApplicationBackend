package dash.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import dash.pojo.Military;

@Component("militaryDao")
public class MilitaryDaoJPA2Impl implements MilitaryDao {

	@PersistenceContext(unitName = "dashPersistence")
	private EntityManager entityManager;

	@Override
	public List<MilitaryEntity> getMilitary(String orderByInsertionDate) {
		String sqlString = null;
		if (orderByInsertionDate != null) {
			sqlString = "SELECT o FROM MilitaryEntity o"
					+ " ORDER BY o.insertionDate " + orderByInsertionDate;
		} else {
			sqlString = "SELECT o FROM MilitaryEntity o";
		}
		TypedQuery<MilitaryEntity> query = entityManager.createQuery(
				sqlString, MilitaryEntity.class);

		return query.getResultList();
	}

	@Override
	public MilitaryEntity getMilitaryById(Long id) {

		try {
			String qlString = "SELECT u FROM MilitaryEntity u WHERE u.id = ?1";
			TypedQuery<MilitaryEntity> query = entityManager.createQuery(
					qlString, MilitaryEntity.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<MilitaryEntity> getMilitaryByAppId(Long appId) {
		
		try {
			String qlString = "SELECT u FROM MilitaryEntity u WHERE u.application_id = ?1";
			TypedQuery<MilitaryEntity> query = entityManager.createQuery(
					qlString, MilitaryEntity.class);
			query.setParameter(1, appId);

			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteMilitary(Military militaryPojo) {

		MilitaryEntity military = entityManager.find(
				MilitaryEntity.class, militaryPojo.getId());
		entityManager.remove(military);
	}

	@Override
	public Long createMilitary(MilitaryEntity military) {

		entityManager.persist(military);
		entityManager.flush();// force insert to receive the id of the
								// military

		// Give admin over new military to the user
		return military.getId();
	}

	@Override
	public void updateMilitary(MilitaryEntity military) {
		// TODO think about partial update and full update
		entityManager.merge(military);
	}

	@Override
	public void deleteMilitarys() {
		Query query = entityManager
				.createNativeQuery("TRUNCATE TABLE military");
		query.executeUpdate();
	}

}
