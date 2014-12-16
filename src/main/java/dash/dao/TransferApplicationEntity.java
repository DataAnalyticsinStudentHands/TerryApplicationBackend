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

import dash.pojo.TransferApplication;

/**
 * TransferApplication entity
 * 
 * @author plindner
 *
 */
@Entity
@Table(name = "transfer_Applications")
public class TransferApplicationEntity implements Serializable {

	/** id of the object */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/** path to stored documents for this object */
	@Column(name = "document_folder")
	private String document_folder;

	/** insertion date in the database */
	@Column(name = "creation_timestamp")
	private Date creation_timestamp;

	@Column(name = "uh_id")
	private String uh_id;

	@Column(name = "first_name")
	private String first_name;

	@Column(name = "last_name")
	private String last_name;

	@Column(name = "middle_name")
	private String middle_name;

	@Column(name = "preferred_name")
	private String preferred_name;

	@Column(name = "ssn")
	private String ssn;

	@Column(name = "permanent_address")
	private String permanent_address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "dob")
	private Date dob;

	@Column(name = "zip_code")
	private Long zip_code;

	@Column(name = "county")
	private String county;

	@Column(name = "home_phone")
	private String home_phone;

	@Column(name = "alt_cell_phone")
	private String alt_cell_phone;

	@Column(name = "gender")
	private String gender;

	@Column(name = "email")
	private String email;

	@Column(name = "citizen")
	private String citizen;

	@Column(name = "permanent_resident")
	private String permanent_resident;

	@Column(name = "texas_resident")
	private String texas_resident;

	@Column(name = "permanent_resident_card")
	private String permanent_resident_card;

	@Column(name = "birthplace")
	private String birthplace;

	@Column(name = "ethnic_background")
	private String ethnic_background;

	@Column(name = "anticipated_major")
	private String anticipated_major;

	@Column(name = "highschool_name")
	private String highschool_name;

	@Column(name = "highschool_city")
	private String highschool_city;

	@Column(name = "highschool_diploma")
	private String highschool_diploma;

	@Column(name = "highschool_ged")
	private String highschool_ged;

	@Column(name = "highschool_graduation_date")
	private Date highschool_graduation_date;
	
	@Column(name = "highschool_graduation_date_na")
	private String highschool_graduation_date_na;

	@Column(name = "highschool_ged_date")
	private Date highschool_ged_date;

	@Column(name = "highschool_ged_date_na")
	private String highschool_ged_date_na;

	@Column(name = "college_hours_progress")
	private String college_hours_progress;

	@Column(name = "cum_gpa")
	private Float cum_gpa;

	@Column(name = "cum_gpa_na")
	private String cum_gpa_na;

	@Column(name = "highschool_ptk_member")
	private String highschool_ptk_member;
	
	@Column(name = "currently_employed")
	private String currently_employed;
	
	@Column(name = "currently_serving")
	private String currently_serving;
	
	@Column(name = "unit_location")
	private String unit_location;
	
	@Column(name = "previously_served")
	private String previously_served;
	
	@Column(name = "mos")
	private String mos;
	
	@Column(name = "additional_specialized_training")
	private String additional_specialized_training;
	
	@Column(name = "medals")
	private String medals;
	
	@Column(name = "wounded_warrior")
	private String wounded_warrior;
	
	@Column(name = "education_benefits")
	private String education_benefits;
	
	@Column(name = "education_benefits_remaining")
	private String education_benefits_remaining;
	
	@Column(name = "details_gibill")
	private String details_gibill;
	
	@Column(name = "hazlewood_act_benefits")
	private String hazlewood_act_benefits;
	
	@Column(name = "hazlewood_act_benefits_remaining")
	private String hazlewood_act_benefits_remaining;
	
	@Column(name = "details_hazlewoodact")
	private String details_hazlewoodact;
	
	@Column(name = "marital_status")
	private String marital_status;
	
	@Column(name = "occupation")
	private String occupation;
	
	@Column(name = "partner_occupation")
	private String partner_occupation;
	
	@Column(name = "employer")
	private String employer;
	
	@Column(name = "partner_employer")
	private String partner_employer;
	
	@Column(name = "agi_2014")
	private String agi_2014;
	
	@Column(name = "partner_agi_2014")
	private String partner_agi_2014;
	
	@Column(name = "edu_debt")
	private String edu_debt;
	
	@Column(name = "partner_edu_debt")
	private String partner_edu_debt;
	
	@Column(name = "childsupport_received")
	private String childsupport_received;
	
	@Column(name = "partner_childsupport_received")
	private String partner_childsupport_received;
	
	@Column(name = "childsupport_payment")
	private String childsupport_payment;
	
	@Column(name = "partner_childsupport_payment")
	private String partner_childsupport_payment;
	
	@Column(name = "childsupport_expenses")
	private String childsupport_expenses;
	
	@Column(name = "partner_childsupport_expenses")
	private String partner_childsupport_expenses;
	
	@Column(name = "other_financial_resources")
	private String other_financial_resources;
	
	@Column(name = "financialsupport_otherfamiliy")
	private String financialsupport_otherfamiliy;
	
	@Column(name = "description_special_circumstances")
	private String description_special_circumstances;
	
	@Column(name = "dependent_taxreturn_2014")
	private String dependent_taxreturn_2014;
	
	@Column(name = "dependent_taxreturn_2015")
	private String dependent_taxreturn_2015;
	
	@Column(name = "claimant")
	private String claimant;
	
	@Column(name = "father_occupation")
	private String father_occupation;
	
	@Column(name = "mother_occupation")
	private String mother_occupation;
	
	@Column(name = "adjusted_cross_income")
	private String adjusted_cross_income;
	
	@Column(name = "family_support")
	private String family_support;
	
	@Column(name = "additional_factors")
	private String additional_factors;
	
	@Column(name = "first_graduate")
	private String first_graduate;

	@Column(name = "why_apply")
	private String why_apply;

	@Column(name = "why_major")
	private String why_major;

	@Column(name = "educational_plans")
	private String educational_plans;

	@Column(name = "life_goals")
	private String life_goals;
	
	@Column(name = "influential_member")
	private String influential_member;
	
	@Column(name = "successful_lessons")
	private String successful_lessons;
	
	@Column(name = "scholarship_consideration")
	private String scholarship_consideration;

	@Column(name = "status")
	private String status;

	@Column(name = "app_uh_method")
	private String app_uh_method;

	@Column(name = "app_uh_date_sub")
	private Date app_uh_date_sub;
	
	@Column(name = "app_uh_date_sub_na")
	private String app_uh_date_sub_na;

	@Column(name = "app_uh_date_int_sub")
	private Date app_uh_date_int_sub;
	
	@Column(name = "app_uh_date_int_sub_na")
	private String app_uh_date_int_sub_na;

	@Column(name = "transcript_method")
	private String transcript_method;

	@Column(name = "transcript_date_sub")
	private Date transcript_date_sub;
	
	@Column(name = "transcript_date_sub_na")
	private String transcript_date_sub_na;

	@Column(name = "transcript_date_int_sub")
	private Date transcript_date_int_sub;
	
	@Column(name = "transcript_date_int_sub_na")
	private String transcript_date_int_sub_na;

	@Column(name = "fafsa_method")
	private String fafsa_method;

	@Column(name = "fafsa_date_sub")
	private Date fafsa_date_sub;
	
	@Column(name = "fafsa_date_sub_na")
	private String fafsa_date_sub_na;

	@Column(name = "fafsa_date_int_sub")
	private Date fafsa_date_int_sub;
	
	@Column(name = "fafsa_date_int_sub_na")
	private String fafsa_date_int_sub_na;

	@Column(name = "housing_method")
	private String housing_method;

	@Column(name = "housing_date_sub")
	private Date housing_date_sub;
	
	@Column(name = "housing_date_sub_na")
	private String housing_date_sub_na;

	@Column(name = "housing_date_int_sub")
	private Date housing_date_int_sub;
	
	@Column(name = "housing_date_int_sub_na")
	private String housing_date_int_sub_na;

	public TransferApplicationEntity() {
	}

	public TransferApplicationEntity(TransferApplication transferApplication) {
		try {
			BeanUtils.copyProperties(this, transferApplication);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocument_folder() {
		return document_folder;
	}

	public void setDocument_folder(String document_folder) {
		this.document_folder = document_folder;
	}

	public Date getCreation_timestamp() {
		return creation_timestamp;
	}

	public void setCreation_timestamp(Date creation_timestamp) {
		this.creation_timestamp = creation_timestamp;
	}

	public String getUh_id() {
		return uh_id;
	}

	public void setUh_id(String uh_id) {
		this.uh_id = uh_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getPreferred_name() {
		return preferred_name;
	}

	public void setPreferred_name(String preferred_name) {
		this.preferred_name = preferred_name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getAlt_cell_phone() {
		return alt_cell_phone;
	}

	public void setAlt_cell_phone(String alt_cell_phone) {
		this.alt_cell_phone = alt_cell_phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCitizen() {
		return citizen;
	}

	public void setCitizen(String citizen) {
		this.citizen = citizen;
	}

	public String getPermanent_resident() {
		return permanent_resident;
	}

	public void setPermanent_resident(String permanent_resident) {
		this.permanent_resident = permanent_resident;
	}

	public String getTexas_resident() {
		return texas_resident;
	}

	public void setTexas_resident(String texas_resident) {
		this.texas_resident = texas_resident;
	}

	public String getPermanent_resident_card() {
		return permanent_resident_card;
	}

	public void setPermanent_resident_card(String permanent_resident_card) {
		this.permanent_resident_card = permanent_resident_card;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getEthnic_background() {
		return ethnic_background;
	}

	public void setEthnic_background(String ethnic_background) {
		this.ethnic_background = ethnic_background;
	}

	public String getAnticipated_major() {
		return anticipated_major;
	}

	public void setAnticipated_major(String anticipated_major) {
		this.anticipated_major = anticipated_major;
	}

	public String getHighschool_name() {
		return highschool_name;
	}

	public void setHighschool_name(String highschool_name) {
		this.highschool_name = highschool_name;
	}

	public String getHighschool_city() {
		return highschool_city;
	}

	public void setHighschool_city(String highschool_city) {
		this.highschool_city = highschool_city;
	}

	public String getHighschool_diploma() {
		return highschool_diploma;
	}

	public void setHighschool_diploma(String highschool_diploma) {
		this.highschool_diploma = highschool_diploma;
	}

	public String getHighschool_ged() {
		return highschool_ged;
	}

	public void setHighschool_ged(String highschool_ged) {
		this.highschool_ged = highschool_ged;
	}

	public Date getHighschool_graduation_date() {
		return highschool_graduation_date;
	}

	public void setHighschool_graduation_date(Date highschool_graduation_date) {
		this.highschool_graduation_date = highschool_graduation_date;
	}

	public String getHighschool_graduation_date_na() {
		return highschool_graduation_date_na;
	}

	public void setHighschool_graduation_date_na(
			String highschool_graduation_date_na) {
		this.highschool_graduation_date_na = highschool_graduation_date_na;
	}

	public Date getHighschool_ged_date() {
		return highschool_ged_date;
	}

	public void setHighschool_ged_date(Date highschool_ged_date) {
		this.highschool_ged_date = highschool_ged_date;
	}

	public String getHighschool_ged_date_na() {
		return highschool_ged_date_na;
	}

	public void setHighschool_ged_date_na(String highschool_ged_date_na) {
		this.highschool_ged_date_na = highschool_ged_date_na;
	}

	public String getCollege_hours_progress() {
		return college_hours_progress;
	}

	public void setCollege_hours_progress(String college_hours_progress) {
		this.college_hours_progress = college_hours_progress;
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

	public String getHighschool_ptk_member() {
		return highschool_ptk_member;
	}

	public void setHighschool_ptk_member(String highschool_ptk_member) {
		this.highschool_ptk_member = highschool_ptk_member;
	}

	public String getCurrently_employed() {
		return currently_employed;
	}

	public void setCurrently_employed(String currently_employed) {
		this.currently_employed = currently_employed;
	}

	public String getCurrently_serving() {
		return currently_serving;
	}

	public void setCurrently_serving(String currently_serving) {
		this.currently_serving = currently_serving;
	}

	public String getUnit_location() {
		return unit_location;
	}

	public void setUnit_location(String unit_location) {
		this.unit_location = unit_location;
	}

	public String getPreviously_served() {
		return previously_served;
	}

	public void setPreviously_served(String previously_served) {
		this.previously_served = previously_served;
	}

	public String getMos() {
		return mos;
	}

	public void setMos(String mos) {
		this.mos = mos;
	}

	public String getAdditional_specialized_training() {
		return additional_specialized_training;
	}

	public void setAdditional_specialized_training(
			String additional_specialized_training) {
		this.additional_specialized_training = additional_specialized_training;
	}

	public String getMedals() {
		return medals;
	}

	public void setMedals(String medals) {
		this.medals = medals;
	}

	public String getWounded_warrior() {
		return wounded_warrior;
	}

	public void setWounded_warrior(String wounded_warrior) {
		this.wounded_warrior = wounded_warrior;
	}

	public String getEducation_benefits() {
		return education_benefits;
	}

	public void setEducation_benefits(String education_benefits) {
		this.education_benefits = education_benefits;
	}

	public String getEducation_benefits_remaining() {
		return education_benefits_remaining;
	}

	public void setEducation_benefits_remaining(String education_benefits_remaining) {
		this.education_benefits_remaining = education_benefits_remaining;
	}

	public String getDetails_gibill() {
		return details_gibill;
	}

	public void setDetails_gibill(String details_gibill) {
		this.details_gibill = details_gibill;
	}

	public String getHazlewood_act_benefits() {
		return hazlewood_act_benefits;
	}

	public void setHazlewood_act_benefits(String hazlewood_act_benefits) {
		this.hazlewood_act_benefits = hazlewood_act_benefits;
	}

	public String getHazlewood_act_benefits_remaining() {
		return hazlewood_act_benefits_remaining;
	}

	public void setHazlewood_act_benefits_remaining(
			String hazlewood_act_benefits_remaining) {
		this.hazlewood_act_benefits_remaining = hazlewood_act_benefits_remaining;
	}

	public String getDetails_hazlewoodact() {
		return details_hazlewoodact;
	}

	public void setDetails_hazlewoodact(String details_hazlewoodact) {
		this.details_hazlewoodact = details_hazlewoodact;
	}

	public String getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPartner_occupation() {
		return partner_occupation;
	}

	public void setPartner_occupation(String partner_occupation) {
		this.partner_occupation = partner_occupation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getPartner_employer() {
		return partner_employer;
	}

	public void setPartner_employer(String partner_employer) {
		this.partner_employer = partner_employer;
	}

	public String getAgi_2014() {
		return agi_2014;
	}

	public void setAgi_2014(String agi_2014) {
		this.agi_2014 = agi_2014;
	}

	public String getPartner_agi_2014() {
		return partner_agi_2014;
	}

	public void setPartner_agi_2014(String partner_agi_2014) {
		this.partner_agi_2014 = partner_agi_2014;
	}

	public String getEdu_debt() {
		return edu_debt;
	}

	public void setEdu_debt(String edu_debt) {
		this.edu_debt = edu_debt;
	}

	public String getPartner_edu_debt() {
		return partner_edu_debt;
	}

	public void setPartner_edu_debt(String partner_edu_debt) {
		this.partner_edu_debt = partner_edu_debt;
	}

	public String getChildsupport_received() {
		return childsupport_received;
	}

	public void setChildsupport_received(String childsupport_received) {
		this.childsupport_received = childsupport_received;
	}

	public String getPartner_childsupport_received() {
		return partner_childsupport_received;
	}

	public void setPartner_childsupport_received(
			String partner_childsupport_received) {
		this.partner_childsupport_received = partner_childsupport_received;
	}

	public String getChildsupport_payment() {
		return childsupport_payment;
	}

	public void setChildsupport_payment(String childsupport_payment) {
		this.childsupport_payment = childsupport_payment;
	}

	public String getPartner_childsupport_payment() {
		return partner_childsupport_payment;
	}

	public void setPartner_childsupport_payment(String partner_childsupport_payment) {
		this.partner_childsupport_payment = partner_childsupport_payment;
	}

	public String getChildsupport_expenses() {
		return childsupport_expenses;
	}

	public void setChildsupport_expenses(String childsupport_expenses) {
		this.childsupport_expenses = childsupport_expenses;
	}

	public String getPartner_childsupport_expenses() {
		return partner_childsupport_expenses;
	}

	public void setPartner_childsupport_expenses(
			String partner_childsupport_expenses) {
		this.partner_childsupport_expenses = partner_childsupport_expenses;
	}

	public String getOther_financial_resources() {
		return other_financial_resources;
	}

	public void setOther_financial_resources(String other_financial_resources) {
		this.other_financial_resources = other_financial_resources;
	}

	public String getFinancialsupport_otherfamiliy() {
		return financialsupport_otherfamiliy;
	}

	public void setFinancialsupport_otherfamiliy(
			String financialsupport_otherfamiliy) {
		this.financialsupport_otherfamiliy = financialsupport_otherfamiliy;
	}

	public String getDescription_special_circumstances() {
		return description_special_circumstances;
	}

	public void setDescription_special_circumstances(
			String description_special_circumstances) {
		this.description_special_circumstances = description_special_circumstances;
	}

	public String getDependent_taxreturn_2014() {
		return dependent_taxreturn_2014;
	}

	public void setDependent_taxreturn_2014(String dependent_taxreturn_2014) {
		this.dependent_taxreturn_2014 = dependent_taxreturn_2014;
	}

	public String getDependent_taxreturn_2015() {
		return dependent_taxreturn_2015;
	}

	public void setDependent_taxreturn_2015(String dependent_taxreturn_2015) {
		this.dependent_taxreturn_2015 = dependent_taxreturn_2015;
	}

	public String getClaimant() {
		return claimant;
	}

	public void setClaimant(String claimant) {
		this.claimant = claimant;
	}

	public String getFather_occupation() {
		return father_occupation;
	}

	public void setFather_occupation(String father_occupation) {
		this.father_occupation = father_occupation;
	}

	public String getMother_occupation() {
		return mother_occupation;
	}

	public void setMother_occupation(String mother_occupation) {
		this.mother_occupation = mother_occupation;
	}

	public String getAdjusted_cross_income() {
		return adjusted_cross_income;
	}

	public void setAdjusted_cross_income(String adjusted_cross_income) {
		this.adjusted_cross_income = adjusted_cross_income;
	}

	public String getFamily_support() {
		return family_support;
	}

	public void setFamily_support(String family_support) {
		this.family_support = family_support;
	}

	public String getAdditional_factors() {
		return additional_factors;
	}

	public void setAdditional_factors(String additional_factors) {
		this.additional_factors = additional_factors;
	}

	public String getFirst_graduate() {
		return first_graduate;
	}

	public void setFirst_graduate(String first_graduate) {
		this.first_graduate = first_graduate;
	}

	public String getWhy_apply() {
		return why_apply;
	}

	public void setWhy_apply(String why_apply) {
		this.why_apply = why_apply;
	}

	public String getWhy_major() {
		return why_major;
	}

	public void setWhy_major(String why_major) {
		this.why_major = why_major;
	}

	public String getEducational_plans() {
		return educational_plans;
	}

	public void setEducational_plans(String educational_plans) {
		this.educational_plans = educational_plans;
	}

	public String getLife_goals() {
		return life_goals;
	}

	public void setLife_goals(String life_goals) {
		this.life_goals = life_goals;
	}

	public String getInfluential_member() {
		return influential_member;
	}

	public void setInfluential_member(String influential_member) {
		this.influential_member = influential_member;
	}

	public String getSuccessful_lessons() {
		return successful_lessons;
	}

	public void setSuccessful_lessons(String successful_lessons) {
		this.successful_lessons = successful_lessons;
	}

	public String getScholarship_consideration() {
		return scholarship_consideration;
	}

	public void setScholarship_consideration(String scholarship_consideration) {
		this.scholarship_consideration = scholarship_consideration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApp_uh_method() {
		return app_uh_method;
	}

	public void setApp_uh_method(String app_uh_method) {
		this.app_uh_method = app_uh_method;
	}

	public Date getApp_uh_date_sub() {
		return app_uh_date_sub;
	}

	public void setApp_uh_date_sub(Date app_uh_date_sub) {
		this.app_uh_date_sub = app_uh_date_sub;
	}

	public String getApp_uh_date_sub_na() {
		return app_uh_date_sub_na;
	}

	public void setApp_uh_date_sub_na(String app_uh_date_sub_na) {
		this.app_uh_date_sub_na = app_uh_date_sub_na;
	}

	public Date getApp_uh_date_int_sub() {
		return app_uh_date_int_sub;
	}

	public void setApp_uh_date_int_sub(Date app_uh_date_int_sub) {
		this.app_uh_date_int_sub = app_uh_date_int_sub;
	}

	public String getApp_uh_date_int_sub_na() {
		return app_uh_date_int_sub_na;
	}

	public void setApp_uh_date_int_sub_na(String app_uh_date_int_sub_na) {
		this.app_uh_date_int_sub_na = app_uh_date_int_sub_na;
	}

	public String getTranscript_method() {
		return transcript_method;
	}

	public void setTranscript_method(String transcript_method) {
		this.transcript_method = transcript_method;
	}

	public Date getTranscript_date_sub() {
		return transcript_date_sub;
	}

	public void setTranscript_date_sub(Date transcript_date_sub) {
		this.transcript_date_sub = transcript_date_sub;
	}

	public String getTranscript_date_sub_na() {
		return transcript_date_sub_na;
	}

	public void setTranscript_date_sub_na(String transcript_date_sub_na) {
		this.transcript_date_sub_na = transcript_date_sub_na;
	}

	public Date getTranscript_date_int_sub() {
		return transcript_date_int_sub;
	}

	public void setTranscript_date_int_sub(Date transcript_date_int_sub) {
		this.transcript_date_int_sub = transcript_date_int_sub;
	}

	public String getTranscript_date_int_sub_na() {
		return transcript_date_int_sub_na;
	}

	public void setTranscript_date_int_sub_na(String transcript_date_int_sub_na) {
		this.transcript_date_int_sub_na = transcript_date_int_sub_na;
	}

	public String getFafsa_method() {
		return fafsa_method;
	}

	public void setFafsa_method(String fafsa_method) {
		this.fafsa_method = fafsa_method;
	}

	public Date getFafsa_date_sub() {
		return fafsa_date_sub;
	}

	public void setFafsa_date_sub(Date fafsa_date_sub) {
		this.fafsa_date_sub = fafsa_date_sub;
	}

	public String getFafsa_date_sub_na() {
		return fafsa_date_sub_na;
	}

	public void setFafsa_date_sub_na(String fafsa_date_sub_na) {
		this.fafsa_date_sub_na = fafsa_date_sub_na;
	}

	public Date getFafsa_date_int_sub() {
		return fafsa_date_int_sub;
	}

	public void setFafsa_date_int_sub(Date fafsa_date_int_sub) {
		this.fafsa_date_int_sub = fafsa_date_int_sub;
	}

	public String getFafsa_date_int_sub_na() {
		return fafsa_date_int_sub_na;
	}

	public void setFafsa_date_int_sub_na(String fafsa_date_int_sub_na) {
		this.fafsa_date_int_sub_na = fafsa_date_int_sub_na;
	}

	public String getHousing_method() {
		return housing_method;
	}

	public void setHousing_method(String housing_method) {
		this.housing_method = housing_method;
	}

	public Date getHousing_date_sub() {
		return housing_date_sub;
	}

	public void setHousing_date_sub(Date housing_date_sub) {
		this.housing_date_sub = housing_date_sub;
	}

	public String getHousing_date_sub_na() {
		return housing_date_sub_na;
	}

	public void setHousing_date_sub_na(String housing_date_sub_na) {
		this.housing_date_sub_na = housing_date_sub_na;
	}

	public Date getHousing_date_int_sub() {
		return housing_date_int_sub;
	}

	public void setHousing_date_int_sub(Date housing_date_int_sub) {
		this.housing_date_int_sub = housing_date_int_sub;
	}

	public String getHousing_date_int_sub_na() {
		return housing_date_int_sub_na;
	}

	public void setHousing_date_int_sub_na(String housing_date_int_sub_na) {
		this.housing_date_int_sub_na = housing_date_int_sub_na;
	}
}
