package com.epam.company.service;

import com.epam.company.model.dao.User;
import com.epam.company.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by @belrbeZ
 */
public interface IUserService extends IModelDTOService<User, UserDTO> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<User> getAuthorized();

    Optional<User> getByEmail(String email);
    Optional<User> getByPhone(String phone);
    Optional<User> getBySsoId(String ssoId);

    List<User> findAllUsers();

    /** TRANSACTIONAL */
    Optional<User> removeByEmail(String email);

    /** TRANSACTIONAL */
    Optional<User> removeByPhone(String phone);
}
