package dash.dao;

import java.util.List;

import dash.pojo.TransferApplication;

/**
 *
 *
 * @see <a
 *      href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface TransferApplicationDao {

	public List<TransferApplicationEntity> getTransferApplications(String orderByInsertionDate);

	/**
	 * Returns a transferApplication given its id
	 *
	 * @param id
	 * @return
	 */
	public TransferApplicationEntity getTransferApplicationById(Long id);
	
	/**
	 * Find application by uh_id
	 *
	 * @param object
	 * @return the application with the uh_id specified or null if not existent
	 */
	public TransferApplicationEntity getTransferApplicationByUh_id(String uh_id);

	public void deleteTransferApplication(TransferApplication transferApplication);

	public Long createTransferApplication(TransferApplicationEntity transferApplication);

	public void updateTransferApplication(TransferApplicationEntity transferApplication);

	/** removes all transferApplications */
	public void deleteTransferApplications();	

}
