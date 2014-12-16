package dash.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.beanutils.BeanUtils;

import dash.dao.TransferActivityEntity;
import dash.helpers.SimpleDateAdapter;
import dash.security.IAclObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferActivity implements  Serializable, IAclObject{

	@XmlElement(name = "id")
	private Long id;
	
	@XmlElement(name = "application_id")
	private Long application_id;
	
	@XmlElement(name = "transfer")
	private String transfer;
	
	@XmlElement(name = "activity")
	private String activity;

	@XmlElement(name = "position")
	private String position;

	@XmlElement(name = "hours_week")
	private Long hours_week;
	
	@XmlElement(name = "hours_total")
	private Long hours_total;
	
	@XmlElement(name = "date_from")
	@XmlJavaTypeAdapter(SimpleDateAdapter.class)
	private Date date_from;
	
	@XmlElement(name = "date_from_na")
	private String date_from_na;
	
	@XmlElement(name = "date_to")
	@XmlJavaTypeAdapter(SimpleDateAdapter.class)
	private Date date_to;
	
	@XmlElement(name = "date_to_na")
	private String date_to_na;
	
	public TransferActivity(){}
	
	public TransferActivity(Long id, String place) {
		super();
		this.id = id;
		this.activity = place;
	}
	
	public TransferActivity(TransferActivityEntity transferActivityEntity) {
		try {
			BeanUtils.copyProperties(this, transferActivityEntity);			
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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getHours_week() {
		return hours_week;
	}

	public void setHours_week(Long hours_week) {
		this.hours_week = hours_week;
	}

	public Long getHours_total() {
		return hours_total;
	}

	public void setHours_total(Long hours_total) {
		this.hours_total = hours_total;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public String getDate_from_na() {
		return date_from_na;
	}

	public void setDate_from_na(String date_from_na) {
		this.date_from_na = date_from_na;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public String getDate_to_na() {
		return date_to_na;
	}

	public void setDate_to_na(String date_to_na) {
		this.date_to_na = date_to_na;
	}
}
