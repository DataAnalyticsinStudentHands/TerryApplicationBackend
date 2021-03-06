package dash.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import dash.pojo.Employment;

/**
 * Employment entity
 * @author plindner
 *
 */
@Entity
@Table(name="employment")
public class EmploymentEntity implements Serializable {

	/** id of the object */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "application_id")
	private Long application_id;
	
	@Column(name = "transfer")
	private String transfer;
	
	@Column(name = "position")
	private String position;

	@Column(name = "employer")
	private String employer;	
	
	@Column(name = "hours")
	private Long hours;
	
	@Column(name = "date_from")
	private Date date_from;
	
	@Column(name = "date_from_na")
	private Boolean date_from_na;
	
	@Column(name = "date_to")
	private Date date_to;
	
	@Column(name = "date_to_na")
	private Boolean date_to_na;
	
	public EmploymentEntity(){}

	public EmploymentEntity(Employment employment) {
		try {
			BeanUtils.copyProperties(this, employment);
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Boolean getDate_from_na() {
		return date_from_na;
	}

	public void setDate_from_na(Boolean date_from_na) {
		this.date_from_na = date_from_na;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public Boolean getDate_to_na() {
		return date_to_na;
	}

	public void setDate_to_na(Boolean date_to_na) {
		this.date_to_na = date_to_na;
	}
}
