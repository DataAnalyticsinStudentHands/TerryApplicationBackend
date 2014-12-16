package dash.dao;

import java.util.List;

import dash.pojo.TransferActivity;

/**
 *
 *
 * @see <a
 *      href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface TransferActivityDao {

	public List<TransferActivityEntity> getTransferActivity(String orderByInsertionDate);

	/**
	 * Returns a transferActivity given its id
	 *
	 * @param id
	 * @return
	 */
	public TransferActivityEntity getTransferActivityById(Long id);

	public List<TransferActivityEntity> getTransferActivityByAppId(Long appId);
	
	public void deleteTransferActivity(TransferActivity transferActivity);

	public Long createTransferActivity(TransferActivityEntity transferActivity);

	public void updateTransferActivity(TransferActivityEntity transferActivity);

	/** removes all transferActivity */
	public void deleteTransferActivitys();	

}
