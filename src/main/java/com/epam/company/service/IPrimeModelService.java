package com.epam.company.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 14.05.2017
 */
public interface IPrimeModelService<T, ID extends Serializable> {
    boolean exists(ID id);

    Optional<T> get(ID id);

    Optional<List<T>> getAll();

    Optional<List<T>> getAllByIds(List<ID> ids);

    /** TRANSACTIONAL */
    Optional<T> save(T model);

    /** TRANSACTIONAL */
    Optional<List<T>> save(List<T> model);

    /** TRANSACTIONAL */
    Optional<T> update(T model);

    /** TRANSACTIONAL */
    void remove(ID id);
}
