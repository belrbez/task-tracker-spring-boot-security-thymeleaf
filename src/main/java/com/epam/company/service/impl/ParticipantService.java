package com.epam.company.service.impl;



import com.epam.company.model.dao.Participant;
import com.epam.company.repository.ParticipantRepository;
import com.epam.company.service.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.company.util.resolvers.ErrorMessageResolver.*;


/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 09.05.2017
 */
@Service
public class ParticipantService extends PrimeModelService<Participant, Long> implements IParticipantService {

    private final ParticipantRepository repository;

    @Autowired
    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
        setup(repository);
    }

    @Override
    public Optional<List<Participant>> getParticipantByTask(Long taskId) {
        if(invalidId(taskId, GET_NULLABLE_ID + "TASK"))
            return Optional.empty();

        return repository.findAllByTaskId(taskId);
    }

    @Override
    public Optional<List<Participant>> getParticipantByUser(Long userId) {
        if(invalidId(userId, GET_NULLABLE_ID + "USER"))
            return Optional.empty();

        return repository.findAllByUserId(userId);
    }

    @Override
    public Optional<Participant> getSpecificParticipant(Long userId, Long taskId) {
        if(invalidId(taskId, GET_NULLABLE_ID + "TASK") || invalidId(userId, GET_NULLABLE_ID + "USER"))
            return Optional.empty();

        return repository.findByUserIdAndTaskId(userId, taskId);
    }

    @Transactional
    @Override
    public Optional<Participant> saveParticipant(Long userId, Long taskId) {
        if(invalidId(taskId, CREATE_NULLABLE_ID + "TASK") || invalidId(userId, CREATE_NULLABLE_ID + "USER"))
            return Optional.empty();
        Participant participant = new Participant(userId, taskId);
        return Optional.of(repository.save(participant));
    }

    @Transactional
    @Override
    public Optional<Participant> updateParticipant(Participant model) {
        if(invalidModel(model, UPDATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(repository.save(model));
    }

    @Transactional
    @Override
    public Optional<Long> removeParticipantsByTask(Long taskId) {
        if(invalidId(taskId, REMOVE_NULLABLE_ID + "TASK"))
            return Optional.empty();

        repository.removeByTaskId(taskId);
        return Optional.of(taskId);
    }

    @Transactional
    @Override
    public Optional<Long> removeParticipantsByUser(Long userId) {
        if(invalidId(userId, REMOVE_NULLABLE_ID + "USER"))
            return Optional.empty();

        repository.removeByUserId(userId);
        return Optional.of(userId);
    }

    @Transactional
    @Override
    public Optional<Long> removeSpecificParticipant(Long userId, Long taskId) {
        if(invalidId(taskId, REMOVE_NULLABLE_ID + "TASK") || invalidId(userId, REMOVE_NULLABLE_ID + "USER"))
            return Optional.empty();

        repository.removeByUserIdAndTaskId(userId, taskId);
        return Optional.of(taskId);
    }
}
