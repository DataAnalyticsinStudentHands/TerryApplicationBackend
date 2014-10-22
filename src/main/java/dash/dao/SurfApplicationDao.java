package dash.dao;

import java.util.List;

import dash.pojo.SurfApplication;

/**
 *
 *
 * @see <a
 *      href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface SurfApplicationDao {

	public List<SurfApplicationEntity> getApplications(String orderByInsertionDate);

	/**
	 * Returns a application given its id
	 *
	 * @param id
	 * @return
	 */
	public SurfApplicationEntity getApplicationById(Long id);
	
	/**
	 * Find object by name
	 *
	 * @param object
	 * @return the object with the name specified or null if not existent
	 */
	public SurfApplicationEntity getApplicationByName(String name);

	public void deleteApplication(SurfApplication application);

	public Long createApplication(SurfApplicationEntity application);

	public void updateApplication(SurfApplicationEntity application);

	/** removes all applications */
	public void deleteApplications();	

}
