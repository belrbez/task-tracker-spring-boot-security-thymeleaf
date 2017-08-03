package com.epam.company.repository;


import com.epam.company.model.dao.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for resolving and finding Participants.
 *
 * @author @belrbeZ
 * @since 10.05.2017
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUserIdAndTaskId(Long userId, Long taskId);

    Optional<List<Participant>> findAllByTaskId(Long taskId);

    Optional<List<Participant>> findAllByUserId(Long userId);

    void removeByUserIdAndTaskId(Long userId, Long taskId);

    void removeByUserId(Long userId);

    void removeByTaskId(Long taskId);

}
