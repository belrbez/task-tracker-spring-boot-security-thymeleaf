package com.epam.company.model.dto;

import com.epam.company.model.dao.User;
import com.epam.company.util.annotation.Login;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by @belrbeZ
 */
public class UserFormDTO {


    @Login
    private String ssoId;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
//    @NotEmpty private String passwordRepeted;

    //<editor-fold desc="GetterAndSetter">

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String name) {
        this.ssoId = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //</editor-fold>
}
