package dash.dao;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import dash.pojo.TransferApplication;

@Component("transferApplicationDao")
public class TransferApplicationDaoJPA2Impl implements TransferApplicationDao {

	@PersistenceContext(unitName = "dashPersistence")
	private EntityManager entityManager;

	@Override
	public List<TransferApplicationEntity> getTransferApplications(String orderByInsertionDate) {
		String sqlString = null;
		if (orderByInsertionDate != null) {
			sqlString = "SELECT o FROM TransferApplicationEntity o"
					+ " ORDER BY o.insertionDate " + orderByInsertionDate;
		} else {
			sqlString = "SELECT o FROM TransferApplicationEntity o";
		}
		TypedQuery<TransferApplicationEntity> query = entityManager.createQuery(
				sqlString, TransferApplicationEntity.class);

		return query.getResultList();
	}

	@Override
	public TransferApplicationEntity getTransferApplicationById(Long id) {

		try {
			String qlString = "SELECT u FROM TransferApplicationEntity u WHERE u.id = ?1";
			TypedQuery<TransferApplicationEntity> query = entityManager.createQuery(
					qlString, TransferApplicationEntity.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public TransferApplicationEntity getTransferApplicationByUh_id(String uh_id) {

		try {
			String qlString = "SELECT o FROM TransferApplicationEntity o WHERE o.uh_id = ?1";
			TypedQuery<TransferApplicationEntity> query = entityManager.createQuery(
					qlString, TransferApplicationEntity.class);
			query.setParameter(1, uh_id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteTransferApplication(TransferApplication transferApplicationPojo) {

		TransferApplicationEntity transferApplication = entityManager.find(
				TransferApplicationEntity.class, transferApplicationPojo.getId());
		entityManager.remove(transferApplication);
	}

	@Override
	public Long createTransferApplication(TransferApplicationEntity transferApplication) {

		transferApplication.setCreation_timestamp(new Date());
		transferApplication.setStatus("in progress");
		
		entityManager.persist(transferApplication);
		entityManager.flush();// force insert to receive the id of the
								// transferApplication
		// create hashed folder name for documents
		String fileName = transferApplication.getUh_id().toString();
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
		transferApplication.setDocument_folder(path.toString());
		entityManager.merge(transferApplication);
	
		// Give admin over new transferApplication to the new user
		return transferApplication.getId();
	}
		

	@Override
	public void updateTransferApplication(TransferApplicationEntity transferApplication) {
		// TODO think about partial update and full update
		entityManager.merge(transferApplication);
	}

	@Override
	public void deleteTransferApplications() {
		Query query = entityManager
				.createNativeQuery("TRUNCATE TABLE transferApplications");
		query.executeUpdate();
	}

}
