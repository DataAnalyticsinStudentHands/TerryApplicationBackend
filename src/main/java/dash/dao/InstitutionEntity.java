package dash.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import dash.pojo.Institution;

/**
 * Institution entity
 * @author plindner
 *
 */
@Entity
@Table(name="institution")
public class InstitutionEntity implements Serializable {

	/** id of the object */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "application_id")
	private Long application_id;
	
	@Column(name = "transfer")
	private String transfer;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "online")
	private String online;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "semesters")
	private String semesters;
	
	@Column(name = "hours_attempted")
	private String hours_attempted;
	
	@Column(name = "hours_completed")
	private String hours_completed;
	
	@Column(name = "cum_gpa")
	private Float cum_gpa;
	
	@Column(name = "cum_gpa_na")
	private String cum_gpa_na;
	
	@Column(name = "rank")
	private Long rank;
	
	public InstitutionEntity(){}

	public InstitutionEntity(Institution institution) {
		try {
			BeanUtils.copyProperties(this, institution);
		} catch ( IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId( Long id) {
		this.id = id;
	}

	public Long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Long application_id) {
		this.application_id = application_id;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSemesters() {
		return semesters;
	}

	public void setSemesters(String semesters) {
		this.semesters = semesters;
	}

	public String getHours_attempted() {
		return hours_attempted;
	}

	public void setHours_attempted(String hours_attempted) {
		this.hours_attempted = hours_attempted;
	}

	public String getHours_completed() {
		return hours_completed;
	}

	public void setHours_completed(String hours_completed) {
		this.hours_completed = hours_completed;
	}

	public Float getCum_gpa() {
		return cum_gpa;
	}

	public void setCum_gpa(Float cum_gpa) {
		this.cum_gpa = cum_gpa;
	}

	public String getCum_gpa_na() {
		return cum_gpa_na;
	}

	public void setCum_gpa_na(String cum_gpa_na) {
		this.cum_gpa_na = cum_gpa_na;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
}
