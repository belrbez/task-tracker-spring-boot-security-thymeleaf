package com.epam.company.service.impl;

import com.epam.company.model.dao.User;
import com.epam.company.model.dto.UserDTO;
import com.epam.company.repository.IUserRepository;
import com.epam.company.service.IUserService;
import com.epam.company.util.ModelTranslator;
import com.epam.company.util.Validator;
import com.epam.company.util.resolvers.ErrorMessageResolver;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.company.util.resolvers.ErrorMessageResolver.*;


/**
 * Created by @belrbeZ
 */
@Service
public class UserService extends PrimeModelService<User, Long> implements IUserService {

    private final IUserRepository repository;

    @Autowired
    public UserService(IUserRepository repository) {
        this.repository = repository;
        this.primeRepository = repository;
    }

    @Override
    public User getEmpty() {
        return User.EMPTY;
    }

    @Override
    public boolean existsByEmail(String email) {
        if(Validator.isStrEmpty(email)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return false;
        }

        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        if(Validator.isStrEmpty(phone)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return false;
        }

        return repository.existsByPhone(phone);
    }

    @Override
    public Optional<User> getAuthorized() {
        return getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public Optional<User> getByEmail(String email) {
        if(Validator.isStrEmpty(email)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return Optional.empty();
        }

        return repository.findOneByEmail(email);
    }

    @Override
    public Optional<User> getByPhone(String phone) {
        if(Validator.isStrEmpty(phone)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return Optional.empty();
        }

        return repository.findOneByPhone(phone);
    }

    @Override
    public Optional<User> getBySsoId(String ssoId) {
        if(Validator.isStrEmpty(ssoId)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_LOGIN);
            return Optional.empty();
        }

        return repository.findOneBySsoId(ssoId);
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Optional<User> saveDTO(UserDTO model) {
        if(model == null) {
            logger.warn(CREATE_MODEL_NULLABLE);
            return Optional.empty();
        }

        return super.save(ModelTranslator.toDAO(model));
    }

    @Transactional
    @Override
    public Optional<User> updateDTO(UserDTO model) {
        if(model == null) {
            logger.warn(UPDATE_MODEL_NULLABLE);
            throw new NullPointerException("");
        }
        Optional<User> toSave = get(model.getId());

        if(!toSave.isPresent()) {
            logger.warn(UPDATE_NOT_FOUND);
            return Optional.empty();
        }

        return super.save(ModelTranslator.updateDAO(toSave.get(), model));
    }

    @Transactional
    @Override
    public Optional<User> removeByEmail(@NotEmpty String email) {
        Optional<User> user = repository.removeByEmail(email);
        return user;
    }

    @Transactional
    @Override
    public Optional<User> removeByPhone(@NotEmpty String phone) {
        Optional<User> user = repository.removeByEmail(phone);
        return user;
    }

    @Transactional
    @Override
    public void remove(Long id) {
        super.remove(id);
    }


}
