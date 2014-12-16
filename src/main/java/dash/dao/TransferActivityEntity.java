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

import dash.pojo.TransferActivity;

/**
 * TransferActivity entity
 * @author plindner
 *
 */
@Entity
@Table(name="transfer_activity")
public class TransferActivityEntity implements Serializable {

	/** id of the object */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "application_id")
	private Long application_id;
	
	@Column(name = "transfer")
	private String transfer;
	
	@Column(name = "activity")
	private String activity;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "hours_week")
	private Long hours_week;
	
	@Column(name = "hours_total")
	private Long hours_total;
	
	@Column(name = "date_from")
	private Date date_from;
	
	@Column(name = "date_from_na")
	private String date_from_na;
	
	@Column(name = "date_to")
	private Date date_to;
	
	@Column(name = "date_to_na")
	private String date_to_na;
	
	public TransferActivityEntity(){}

	public TransferActivityEntity(TransferActivity transferActivity) {
		try {
			BeanUtils.copyProperties(this, transferActivity);
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
