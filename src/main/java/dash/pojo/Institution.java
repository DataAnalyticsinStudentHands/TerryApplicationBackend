package dash.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;

import dash.dao.InstitutionEntity;
import dash.security.IAclObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Institution implements  Serializable, IAclObject{

	@XmlElement(name = "id")
	private Long id;
	
	@XmlElement(name = "application_id")
	private Long application_id;
	
	@XmlElement(name = "transfer")
	private String transfer;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "online")
	private String online;
	
	@XmlElement(name = "city")
	private String city;
	
	@XmlElement(name = "semesters")
	private String semesters;
	
	@XmlElement(name = "hours_attempted")
	private String hours_attempted;
	
	@XmlElement(name = "hours_completed")
	private String hours_completed;
	
	@XmlElement(name = "cum_gpa")
	private Float cum_gpa;
	
	@XmlElement(name = "cum_gpa_na")
	private String cum_gpa_na;
	
	@XmlElement(name = "rank")
	private Long rank;
	
	public Institution(){}
	
	public Institution(InstitutionEntity institutionEntity) {
		try {
			BeanUtils.copyProperties(this, institutionEntity);			
		} catch ( IllegalAccessException e) {

			e.printStackTrace();
		} catch ( InvocationTargetException e) {

			e.printStackTrace();
		}
	}
	
	public Long getId() {
		return id;
	}	

	public void setId(Long id) {
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
