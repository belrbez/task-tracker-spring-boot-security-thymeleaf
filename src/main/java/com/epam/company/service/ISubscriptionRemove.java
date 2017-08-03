package com.epam.company.service;

import java.util.Optional;

/**
 * !!! Private Contracts !!!
 *
 * @author @belrbeZ
 * @since 13.05.2017
 */
public interface ISubscriptionRemove {

    /** TRANSACTIONAL */
    Optional<Long> removeUserSubscriptions(Long userId);

    /** TRANSACTIONAL */
    Optional<Long> removeTaskSubscribers(Long userId);
}
