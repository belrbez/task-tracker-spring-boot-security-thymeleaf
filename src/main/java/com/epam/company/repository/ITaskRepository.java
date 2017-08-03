package com.epam.company.repository;

/*
 * Created by @belrbeZ
 */


import com.epam.company.model.dao.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findAllByTopicStarterId(Long userId);

    Optional<List<Task>> findAllByTheme(String theme);

    void removeByIdAndTopicStarterId(Long id, Long topicStarterId);
}
