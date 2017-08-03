package com.epam.company.model.dto;


import com.epam.company.model.types.EmployeeRole;
import com.epam.company.model.types.UserCityType;
import com.epam.company.model.types.UserProfileType;
import com.epam.company.model.types.UserState;

import javax.validation.constraints.NotNull;

/**
 * Created by @belrbeZ
 */
public class UserDTO {

    public static final UserDTO EMPTY = new UserDTO();

    @NotNull
    private Long id;

    private String ssoId;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String about;

    private String phone;

    private UserCityType city;

    private UserState state = UserState.ACTIVE;

    private UserProfileType userProfileType = UserProfileType.EMPTY;

    private EmployeeRole employeeRole = EmployeeRole.EMPTY;

    private UserDTO() {
        this.id         = UserProfileType.EMPTY.getUserProfileType();
        this.ssoId = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
        this.city = UserCityType.EMPTY;
        this.state = UserState.EMPTY;
        this.userProfileType = UserProfileType.EMPTY;
        this.employeeRole = EmployeeRole.EMPTY;
    }

    public UserDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public UserDTO(Long id, String ssoId, String password, String firstName,
                   String lastName, String email, String about, String phone, UserCityType city) {
        this(email, password);
        this.id = id;
        this.ssoId = ssoId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.about = about;
    }

    public UserDTO(Long id, String ssoId, String password, String firstName,
                   String lastName, String email, String about, String phone,
                   UserCityType city,UserState state,
                   UserProfileType userProfileType, EmployeeRole employeeRole) {
        this(id, ssoId, password, firstName, lastName, email, about, phone, city);
        this.state = state;
        this.userProfileType = userProfileType;
        this.employeeRole = employeeRole;
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO that = (UserDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
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
