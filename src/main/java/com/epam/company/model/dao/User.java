package com.epam.company.model.dao;


import com.epam.company.model.types.EmployeeRole;
import com.epam.company.model.types.UserCityType;
import com.epam.company.model.types.UserProfileType;
import com.epam.company.model.types.UserState;
import com.epam.company.util.resolvers.DatabaseResolver;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = DatabaseResolver.TABLE_USERS, schema = DatabaseResolver.SCHEMA
)
public class User {
/*,
	indexes = {
		@Index(name = "ssoId",  columnList="ssoId", unique = true),
		@Index(name = "email",  columnList="email", unique = true),
		@Index(name = "phone",  columnList="phone", unique = true)
	}*/
	public static final User EMPTY = new User();
    public static final int MIN_LENGTH_LOGIN = 6;
	public static final int MAX_LENGTH_LOGIN = 15;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name="sso_id", unique=true, nullable=false)
	private String ssoId;
	
	@Column(name="password", nullable=false)
	private String password;
		
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="email", nullable=false)
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "city")
	private UserCityType city;

	@Column(name = "about")
	private String about;

	@Column(name="state", nullable=false)
	private UserState state = UserState.ACTIVE;

	@Column(name="type")
	private UserProfileType userProfileType = UserProfileType.EMPTY;

	@Column(name="employee_type")
	private EmployeeRole employeeRole = EmployeeRole.EMPTY;

	@Column(name = "registerDate", nullable = false)
	private Timestamp registerDate;

	private User() {
		this.id = UserProfileType.EMPTY.getUserProfileType();
		this.state = UserState.UNKNOWN;
		this.userProfileType = UserProfileType.EMPTY;
		this.employeeRole = EmployeeRole.EMPTY;
		this.ssoId = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phone = "";
		this.password = "";
		this.about = "";
		this.registerDate = Timestamp.valueOf(LocalDateTime.now());
	}

	public User(String ssoId, String password, String email) {
		this.ssoId = ssoId;
		this.password = password;
		this.email = email;
		this.phone = "";
		this.about = "";
		this.registerDate = Timestamp.valueOf(LocalDateTime.now());
	}

	public User(UserProfileType userProfileType, String ssoId, String password, String email) {
		this(ssoId, password, email);
		this.userProfileType = userProfileType;
	}

	public User(String ssoId, String password, String firstName, String lastName,
				String email, String phone, UserCityType city, UserState state,
				UserProfileType userProfileType, EmployeeRole employeeRole) {
		this(ssoId, password, email);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.city = city;
		this.state = state;
		this.userProfileType = userProfileType;
		this.employeeRole = employeeRole;
	}


	public Long getId() {
		return id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserCityType getCity() {
		return city;
	}

	public void setCity(UserCityType city) {
		this.city = city;
	}

	public UserState getState() {
		return state;
	}

	public void setState(UserState state) {
		this.state = state;
	}

	public UserProfileType getUserProfileType() {
		return userProfileType;
	}

	public void setUserProfileType(UserProfileType userProfileType) {
		this.userProfileType = userProfileType;
	}

	public EmployeeRole getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(EmployeeRole employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ssoId == null) {
			if (other.ssoId != null)
				return false;
		} else if (!ssoId.equals(other.ssoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", ssoId='" + ssoId + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", city=" + city +
				", state=" + state +
				", userProfileType=" + userProfileType +
				", employeeRole=" + employeeRole +
				'}';
	}
}
