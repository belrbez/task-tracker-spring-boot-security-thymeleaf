package com.epam.company.service;

/*
 * Created by @belrbeZ
 */


import com.epam.company.model.dao.Task;
import com.epam.company.model.dto.TaskDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITaskService extends IModelDTOService<Task, TaskDTO> {
    Optional<List<TaskDTO>> findAll();

    Optional<List<Task>> getByTheme(String theme);
    Optional<List<Task>> getByTags(List<String> tags);
    Optional<List<Task>> getByUserId(Long userId);
    Optional<List<Task>> getByIds(Set<Long> ids);

    void removeByCheckUserId(Long id, Long userId);
}
