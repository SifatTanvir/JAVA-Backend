package com.dbl.nsl.erp.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class Employee {

	public Employee() {
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "role")
	private String role;

	@Column(name = "email")
	private String email;

	@Column(name = "official_email")
	private String officialEmail;

	@Column(name = "active")
	private boolean active;

	@Column(name = "permanent_address")
	private String permanentAddress;

	@Column(name = "present_address")
	private String presentAddress;

	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "mother_name")
	private String motherName;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 120)
	private String password;
	
	@Column(name="profile_picture")
	private String profilePicPath;

	@Column(name = "birth_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@Column(name = "gender")
	private String gender;

	@Column(name = "nid_number")
	private Long nidNumber;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "religion")
	private String religion;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "status")
	private String employmentStatus;

	@Column(name = "official_conatact")
	private String officialContact;
	
	@Column(name = "personal_email")
	private String personalEmail;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date  probationStartDate;

	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date confirmationDate;
	
	@Column(name = "project_manager_id")
	private Long projectManagerId;

	@Column(name = "project_manager_name")
	private String projectManagerName;
	
	@Column(name = "is_project_manager")
	private Boolean IsProjectManager;
	
	@Column(name = "line_manager_id")
	private Long lineManagerId;
	
	@Column(name = "line_manager_name")
	private String lineManagerName;
	
	@Column(name = "is_line_manager")
	private Boolean IsLineManager;
	
	@Column(name = "team_leader_id")
	private Long teamLeaderId;
	
	@Column(name = "team_leader_name")
	private String teamLeaderName;
	
	@Column(name = "is_team_leader")
	private Boolean IsTeamLeader;
	
	@Column(name = "head_of_dept_id")
	private Long headOfDepartmentId;
	
	@Column(name = "head_of_dept_name")
	private String headOfDepartmentName;
	
	@Column(name = "is_head_of_dept")
	private Boolean IsHeadOfDepartment;
	
//	@Column(name = "annual_leave")
//	private Long annualLeave;
//	
//	@Column(name = "remaining_annual_leave")
//	private Long remainingAnnualLeave;
	
	@JsonIgnoreProperties( {"employee"} )
	@ManyToOne( fetch = FetchType.LAZY )
	private AttendanceRoaster attendanceRoaster;
	
	@JsonIgnoreProperties( {"employee"} )
	@ManyToOne( fetch = FetchType.LAZY )
	private LeavePolicy leavePolicy;
    
	@OneToOne( cascade = CascadeType.ALL )
	@JoinColumn(name = "bank_details_id")
	private BankDetails bankDetails;
	
	@OneToOne( cascade = CascadeType.ALL )
	@JoinColumn(name = "leave_id")
	private AnnualLeave annualLeaveModel;
	
	@OneToOne( cascade = CascadeType.ALL )
	@JoinColumn(name = "salary_benefit_id")
	private SalaryBenefit salaryBenefit;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private EmergencyContact econtact;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private LeaveConsumed leaveConsumed;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "employees_roles", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@JsonIgnoreProperties({"department", "location"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Company> company;
	
	@JsonIgnoreProperties({"designation"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Department> department;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Designation> designation;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Location> location;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<LeaveRequest> leaveRequest;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Experience> experience;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Education> education;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Certification> certification;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Nominee> nominee;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getOfficialContact() {
		return officialContact;
	}

	public void setOfficialContact(String officialContact) {
		this.officialContact = officialContact;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}
	
	public EmergencyContact getEcontact() {
		return econtact;
	}

	public void setEcontact(EmergencyContact econtact) {
		this.econtact = econtact;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getNidNumber() {
		return nidNumber;
	}

	public void setNidNumber(Long nidNumber) {
		this.nidNumber = nidNumber;
	}
	
	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	
	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(Long projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	
	public Long getLineManagerId() {
		return lineManagerId;
	}

	public void setLineManagerId(Long lineManagerId) {
		this.lineManagerId = lineManagerId;
	}

	public Long getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(Long teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public Long getHeadOfDepartmentId() {
		return headOfDepartmentId;
	}

	public void setHeadOfDepartmentId(Long headOfDepartmentId) {
		this.headOfDepartmentId = headOfDepartmentId;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public LeavePolicy getLeavePolicy() {
		return leavePolicy;
	}

	public void setLeavePolicy(LeavePolicy leavePolicy) {
		this.leavePolicy = leavePolicy;
	}

	public List<Certification> getCertification() {
		return certification;
	}

	public void setCertification(List<Certification> certification) {
		this.certification = certification;
	}

	public List<LeaveRequest> getLeaveRequest() {
		return leaveRequest;
	}

	public void setLeaveConsume(List<LeaveRequest> leaveRequest) {
		this.leaveRequest = leaveRequest;
	}

	public List<Experience> getExperience() {
		return experience;
	}

	public void setExperience(List<Experience> experience) {
		this.experience = experience;

	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public AttendanceRoaster getAttendanceRoaster() {
		return attendanceRoaster;
	}

	public void setAttendanceRoaster(AttendanceRoaster attendanceRoaster) {
		this.attendanceRoaster = attendanceRoaster;
	}
	
	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	public SalaryBenefit getSalaryBenefit() {
		return salaryBenefit;
	}

	public void setSalaryBenefit(SalaryBenefit salaryBenefit) {
		this.salaryBenefit = salaryBenefit;
	}	

	public void setLeaveRequest(List<LeaveRequest> leaveRequest) {
		this.leaveRequest = leaveRequest;
	}

	public LeaveConsumed getLeaveConsumed() {
		return leaveConsumed;
	}

	public void setLeaveConsumed(LeaveConsumed leaveConsumed) {
		this.leaveConsumed = leaveConsumed;
	}

	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	
	public Boolean getIsProjectManager() {
		return IsProjectManager;
	}

	public void setIsProjectManager(Boolean IsProjectManager) {
		this.IsProjectManager = IsProjectManager;
	}

	public String getLineManagerName() {
		return lineManagerName;
	}

	public void setLineManagerName(String lineManagerName) {
		this.lineManagerName = lineManagerName;
	}
	
	public Boolean getIsLineManager() {
		return IsLineManager;
	}

	public void setIsLineManager(Boolean IsLineManager) {
		this.IsLineManager = IsLineManager;
	}

	public String getTeamLeaderName() {
		return teamLeaderName;
	}

	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}
	
	public Boolean getIsTeamLeader() {
		return IsTeamLeader;
	}

	public void setIsTeamLeader(Boolean IsTeamLeader) {
		this.IsTeamLeader = IsTeamLeader;
	}


	public String getHeadOfDepartmentName() {
		return headOfDepartmentName;
	}

	public void setHeadOfDepartmentName(String headOfDepartmentName) {
		this.headOfDepartmentName = headOfDepartmentName;
	}
	
	public Boolean getIsHeadOfDepartment() {
		return IsHeadOfDepartment;
	}

	public void setIsHeadOfDepartment(Boolean IsHeadOfDepartment) {
		this.IsHeadOfDepartment = IsHeadOfDepartment;
	}

	public List<Company> getCompany() {
		return company;
	}

	public void setCompany(List<Company> company) {
		this.company = company;
	}

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}

	public List<Designation> getDesignation() {
		return designation;
	}

	public void setDesignation(List<Designation> designation) {
		this.designation = designation;
	}
	
	public Date getProbationStartDate() {
		return probationStartDate;
	}

	public void setProbationStartDate(Date probationStartDate) {
		this.probationStartDate = probationStartDate;
	}

	public List<Location> getLocation() {
		return location;
	}

	public void setLocation(List<Location> location) {
		this.location = location;
	}
	
	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public void setNominee(List<Nominee> nominee) {
		this.nominee = nominee;
	}

	public List<Nominee> getNominee() {
		return nominee;
	}
	
	public void setAnnualLeaveModel(AnnualLeave annualLeaveModel) {
		this.annualLeaveModel = annualLeaveModel;
	}

	public AnnualLeave getAnnualLeaveModel() {
		return annualLeaveModel;
	}	
	
}
