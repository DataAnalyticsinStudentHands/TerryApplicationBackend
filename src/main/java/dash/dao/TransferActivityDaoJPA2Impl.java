package dash.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import dash.pojo.TransferActivity;

@Component("transferActivityDao")
public class TransferActivityDaoJPA2Impl implements TransferActivityDao {

	@PersistenceContext(unitName = "dashPersistence")
	private EntityManager entityManager;

	@Override
	public List<TransferActivityEntity> getTransferActivity(String orderByInsertionDate) {
		String sqlString = null;
		if (orderByInsertionDate != null) {
			sqlString = "SELECT o FROM TransferActivityEntity o"
					+ " ORDER BY o.insertionDate " + orderByInsertionDate;
		} else {
			sqlString = "SELECT o FROM TransferActivityEntity o";
		}
		TypedQuery<TransferActivityEntity> query = entityManager.createQuery(
				sqlString, TransferActivityEntity.class);

		return query.getResultList();
	}

	@Override
	public TransferActivityEntity getTransferActivityById(Long id) {

		try {
			String qlString = "SELECT u FROM TransferActivityEntity u WHERE u.id = ?1";
			TypedQuery<TransferActivityEntity> query = entityManager.createQuery(
					qlString, TransferActivityEntity.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<TransferActivityEntity> getTransferActivityByAppId(Long appId) {
		
		try {
			String qlString = "SELECT u FROM TransferActivityEntity u WHERE u.application_id = ?1";
			TypedQuery<TransferActivityEntity> query = entityManager.createQuery(
					qlString, TransferActivityEntity.class);
			query.setParameter(1, appId);

			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteTransferActivity(TransferActivity transferActivityPojo) {

		TransferActivityEntity transferActivity = entityManager.find(
				TransferActivityEntity.class, transferActivityPojo.getId());
		entityManager.remove(transferActivity);
	}

	@Override
	public Long createTransferActivity(TransferActivityEntity transferActivity) {

		entityManager.persist(transferActivity);
		entityManager.flush();// force insert to receive the id of the
								// transferActivity

		// Give admin over new transferActivity to the user
		return transferActivity.getId();
	}

	@Override
	public void updateTransferActivity(TransferActivityEntity transferActivity) {
		// TODO think about partial update and full update
		entityManager.merge(transferActivity);
	}

	@Override
	public void deleteTransferActivitys() {
		Query query = entityManager
				.createNativeQuery("TRUNCATE TABLE transferActivity");
		query.executeUpdate();
	}

}
