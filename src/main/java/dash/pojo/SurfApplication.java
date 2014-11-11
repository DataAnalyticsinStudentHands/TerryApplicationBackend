package dash.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.beanutils.BeanUtils;

import dash.dao.SurfApplicationEntity;
import dash.security.IAclObject;
import dash.helpers.SimpleDateAdapter;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SurfApplication implements  Serializable, IAclObject{

	@XmlElement(name = "id")
	private Long id;	

	@XmlElement(name = "creation_timestamp")
	private Date creation_timestamp;
	
	@XmlElement(name = "uh_id")
	private Long uh_id;
	
	@XmlElement(name = "last_name")
	private String last_name;
	
	@XmlElement(name = "first_name")
	private String first_name;
	
	@XmlElement(name = "permanent_address")
	private String permanent_address;
	
	@XmlElement(name = "city")
	private String city;
	
	@XmlElement(name = "state")
	private String state;
	
	@XmlElement(name = "zip_code")
	private Long zip_code;
	
	@XmlElement(name = "county")
	private String county;
	
	@XmlElement(name = "phone")
	private String home_phone;
	
	@XmlElement(name = "email")
	private String email;
	
	@XmlElement(name = "re_email")
	private String re_email;
	
	@XmlElement(name = "permanent_resident")
	private String permanent_resident;
	
	@XmlElement(name = "college")
	private String college;
	
	@XmlElement(name = "major")
	private String major;
	
	@XmlElement(name = "completed_hours")
	private String completed_hours;
	
	@XmlJavaTypeAdapter(SimpleDateAdapter.class)
	@XmlElement(name = "anticipated_grad_date")
	private Date anticipated_grad_date;
		
	@XmlElement(name = "sponsor_first_name")
	private String sponsor_first_name;
	
	@XmlElement(name = "sponsor_last_name")
	private String sponsor_last_name;
	
	@XmlElement(name = "sponsor_email")
	private String sponsor_email;
	
	@XmlElement(name = "mentor_college")
	private String mentor_college;
	
	@XmlElement(name = "mentor_department")
	private String mentor_department;
	
	@XmlElement(name = "gpa")
	private Float gpa;
	
	@XmlElement(name = "major_jpa")
	private Float major_jpa;
	
	@XmlElement(name = "national_merrit")
	private String national_merrit;
	
	@XmlElement(name = "activities")
	private String activities;
	
	@XmlElement(name = "previous_experience")
	private String previous_experience;
	
	@XmlElement(name = "statement")
	private String statement;
	
	  
	public SurfApplication(){}
	
	public SurfApplication(Long id, Date creation_timestamp) {
		super();
		this.id = id;
		this.creation_timestamp = creation_timestamp;
	}
	
	public SurfApplication(SurfApplicationEntity surfApplicationEntity) {
		try {
			BeanUtils.copyProperties(this, surfApplicationEntity);			
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

	public Date getCreation_timestamp() {
		return creation_timestamp;
	}

	public void setCreation_timestamp(Date creation_timestamp) {
		this.creation_timestamp = creation_timestamp;
	}

	public Long getUh_id() {
		return uh_id;
	}

	public void setUh_id(Long uh_id) {
		this.uh_id = uh_id;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getPermanent_address() {
		return permanent_address;
	}

	public void setPermanent_address(String permanent_address) {
		this.permanent_address = permanent_address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getZip_code() {
		return zip_code;
	}

	public void setZip_code(Long zip_code) {
		this.zip_code = zip_code;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getHome_phone() {
		return home_phone;
	}

	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRe_email() {
		return re_email;
	}

	public void setRe_email(String re_email) {
		this.re_email = re_email;
	}

	public String getPermanent_resident() {
		return permanent_resident;
	}

	public void setPermanent_resident(String permanent_resident) {
		this.permanent_resident = permanent_resident;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCompleted_hours() {
		return completed_hours;
	}

	public void setCompleted_hours(String completed_hours) {
		this.completed_hours = completed_hours;
	}

	public Date getAnticipated_grad_date() {
		return anticipated_grad_date;
	}

	public void setAnticipated_grad_date(Date anticipated_grad_date) {
		this.anticipated_grad_date = anticipated_grad_date;
	}

	public String getSponsor_first_name() {
		return sponsor_first_name;
	}

	public void setSponsor_first_name(String sponsor_first_name) {
		this.sponsor_first_name = sponsor_first_name;
	}

	public String getSponsor_last_name() {
		return sponsor_last_name;
	}

	public void setSponsor_last_name(String sponsor_last_name) {
		this.sponsor_last_name = sponsor_last_name;
	}

	public String getSponsor_email() {
		return sponsor_email;
	}

	public void setSponsor_email(String sponsor_email) {
		this.sponsor_email = sponsor_email;
	}

	public String getMentor_college() {
		return mentor_college;
	}

	public void setMentor_college(String mentor_college) {
		this.mentor_college = mentor_college;
	}

	public String getMentor_department() {
		return mentor_department;
	}

	public void setMentor_department(String mentor_department) {
		this.mentor_department = mentor_department;
	}

	public Float getGpa() {
		return gpa;
	}

	public void setGpa(Float gpa) {
		this.gpa = gpa;
	}

	public Float getMajor_jpa() {
		return major_jpa;
	}

	public void setMajor_jpa(Float major_jpa) {
		this.major_jpa = major_jpa;
	}

	public String getNational_merrit() {
		return national_merrit;
	}

	public void setNational_merrit(String national_merrit) {
		this.national_merrit = national_merrit;
	}

	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}

	public String getPrevious_experience() {
		return previous_experience;
	}

	public void setPrevious_experience(String previous_experience) {
		this.previous_experience = previous_experience;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	
}
