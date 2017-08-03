package com.epam.company.service.impl;

import com.epam.company.service.IPrimeModelService;
import com.epam.company.util.resolvers.ErrorMessageResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by @belrbeZ
 *
 * Basic model with ID as a LONG type PARAMETER!
 */
@Service
public class PrimeModelService<T, ID extends Serializable>
                                extends PrimeModelUtilService<T, ID>
                                implements IPrimeModelService<T, ID> {

    protected JpaRepository<T, ID> primeRepository;

    protected void setup(JpaRepository<T, ID> repo) {
        this.primeRepository = repo;
    }

    @Override
    public boolean exists(ID id) {
        return (id != null && primeRepository.exists(id));
    }

    @Override
    public Optional<T> get(ID id) {
        if(invalidId(id, ErrorMessageResolver.GET_NULLABLE_ID))
            return Optional.empty();

        return Optional.of(primeRepository.findOne(id));
    }

    @Override
    public Optional<List<T>> getAll() {
        return Optional.of(primeRepository.findAll());
    }

    @Override
    public Optional<List<T>> getAllByIds(List<ID> ids) {
        return Optional.of(primeRepository.findAll(ids));
    }

    // Ask what to return, exception or empty/null in case of NULL
    @Transactional
    @Override
    public Optional<T> save(T model) {
        if(invalidModel(model, ErrorMessageResolver.CREATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(primeRepository.save(model));
    }

    @Transactional
    @Override
    public Optional<List<T>> save(List<T> model) {
        if(invalidListModel(model, ErrorMessageResolver.CREATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(primeRepository.save(model));
    }

    // Ask what to return, exception or empty/null in case of NULL
    @Transactional
    @Override
    public Optional<T> update(T model) {
        if(invalidModel(model, ErrorMessageResolver.UPDATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(primeRepository.save(model));
    }

    @Transactional
    @Override
    public void remove(ID id) {
        if(invalidId(id, ErrorMessageResolver.REMOVE_NULLABLE_ID))
            throw new NullPointerException(ErrorMessageResolver.NULLABLE_MODEL + "DELETE");

        primeRepository.delete(id);

        System.out.println("Removed? "+id.toString());

    }
}
