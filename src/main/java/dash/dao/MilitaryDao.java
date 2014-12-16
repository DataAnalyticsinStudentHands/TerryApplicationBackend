package dash.dao;

import java.util.List;

import dash.pojo.Military;

/**
 *
 *
 * @see <a
 *      href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface MilitaryDao {

	public List<MilitaryEntity> getMilitary(String orderByInsertionDate);

	/**
	 * Returns a military given its id
	 *
	 * @param id
	 * @return
	 */
	public MilitaryEntity getMilitaryById(Long id);
	
	public List<MilitaryEntity> getMilitaryByAppId(Long appId);
	
	public void deleteMilitary(Military military);

	public Long createMilitary(MilitaryEntity military);

	public void updateMilitary(MilitaryEntity military);

	/** removes all military */
	public void deleteMilitarys();	

}
