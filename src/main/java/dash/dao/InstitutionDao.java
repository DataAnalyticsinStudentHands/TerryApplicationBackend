package dash.dao;

import java.util.List;

import dash.pojo.Institution;

/**
 *
 *
 * @see <a
 *      href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface InstitutionDao {

	public List<InstitutionEntity> getInstitution(String orderByInsertionDate);

	/**
	 * Returns a institution given its id
	 *
	 * @param id
	 * @return
	 */
	public InstitutionEntity getInstitutionById(Long id);
	
	public List<InstitutionEntity> getInstitutionByAppId(Long appId);
	
	public void deleteInstitution(Institution institution);

	public Long createInstitution(InstitutionEntity institution);

	public void updateInstitution(InstitutionEntity institution);

	/** removes all institution */
	public void deleteInstitutions();	

}
