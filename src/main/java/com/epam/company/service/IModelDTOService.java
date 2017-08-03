package com.epam.company.service;

import com.epam.company.model.dao.Task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by @belrbeZ
 */
public interface IModelDTOService<DAO, DTO> {

    /** TRANSACTIONAL */
    Optional<DAO> saveDTO(DTO model);

    /** TRANSACTIONAL */
    Optional<DAO> updateDTO(DTO model);

}
