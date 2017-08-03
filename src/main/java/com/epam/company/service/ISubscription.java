package com.epam.company.service;

import com.epam.company.model.dto.TaskDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by @belrbeZ
 *
 * Public Contracts To Work With SubscriptionService
 */
public interface ISubscription {

    Optional<List<Long>> getTaskSubscribers(Long taskId);

    Optional<Set<Long>> getUserSubscriptions(Long userId);

    /** TRANSACTIONAL */
    Optional<Long> viewTask(Long userId, Long taskId);

    /** TRANSACTIONAL */
    Optional<Long> modifyTask(Long taskId);

    /** TRANSACTIONAL */
//    TaskDTO fillSubs(Long userId, TaskDTO tasks);

    /** TRANSACTIONAL */
//    List<TaskDTO> fillSubs(Long userId, List<TaskDTO> tasks);

    /** TRANSACTIONAL */
    Optional<Long> subscribe(Long userId, Long taskId);

    /** TRANSACTIONAL */
    Optional<Long> unSubscribe(Long userId, Long taskId);
}
