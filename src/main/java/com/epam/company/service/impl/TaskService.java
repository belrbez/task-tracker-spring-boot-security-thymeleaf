package com.epam.company.service.impl;

import com.epam.company.model.dao.Participant;
import com.epam.company.model.dao.Task;
import com.epam.company.model.dao.User;
import com.epam.company.model.dto.TaskDTO;
import com.epam.company.repository.ITaskRepository;
import com.epam.company.service.ITaskService;
import com.epam.company.service.IUserService;
import com.epam.company.util.ModelTranslator;
import com.epam.company.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.company.util.resolvers.ErrorMessageResolver.*;

/**
 * Created by @belrbeZ
 *
 * Repository Service to work with Tasks
 */
@Service
public class TaskService extends PrimeModelService<Task, Long>
        implements ITaskService {

    private final ITaskRepository repository;
    private final IUserService userService;

    @Autowired
    public TaskService(ITaskRepository repository,
                       IUserService userService) {

        this.repository = repository;
        this.primeRepository = repository;
        this.userService = userService;
    }

    @Override
    public Optional<List<TaskDTO>> findAll() {
        return Optional.of(ModelTranslator.tasksToDTO(repository.findAll()));
    }

    @Override
    public Optional<List<Task>> getByTheme(String theme) {
        if(Validator.isStrEmpty(theme)) {
            logger.warn("Theme to Search is NULL or EMPTY");
            return Optional.empty();
        }

        return  repository.findAllByTheme(theme);
    }

    @Override
    public Optional<List<Task>> getByTags(List<String> tags) {
        if(tags == null || tags.isEmpty()) {
            logger.warn("Tags to Search is NULL or EMPTY");
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<Task>> getByUserId(Long userId) {
        if(invalidId(userId, GET_NULLABLE_ID + "USER"))
            return Optional.empty();

        return repository.findAllByTopicStarterId(userId);
    }

    @Override
    public Optional<List<Task>> getByIds(Set<Long> ids) {
        List<Task> tasks = new ArrayList<>();
        for (Long id :
                ids) {
            tasks.add(get(id).get());
        }
        return Optional.of(tasks);
    }

    @Transactional
    @Override
    public Optional<Task> saveDTO(TaskDTO model) {
        if(model == null) {
            logger.warn(CREATE_MODEL_NULLABLE);
            return Optional.empty();
        }

        return save(ModelTranslator.toDAO(model));
    }

    @Transactional
    @Override
    public Optional<Task> updateDTO(TaskDTO model) {
        if(model == null) {
            logger.warn(UPDATE_MODEL_NULLABLE);
            return Optional.empty();
        }

        Optional<User> user = userService.getAuthorized();
        if(!user.isPresent() || !user.get().getId().equals(model.getTopicStarterId()))
            throw new NullPointerException("USER ID NOT MATCH TOPIC STARTER");

        Optional<Task> toSave = get(model.getId());

        // OR SAVE AS A RECENT ONE, THAT IS A QUESTION
        if(!toSave.isPresent()) {
            logger.warn(UPDATE_NOT_FOUND);
            return Optional.empty();
        }

        return save(ModelTranslator.updateDAO(toSave.get(), model));
    }

    @Transactional
    @Override
    public Optional<Task> save(Task model) {
        if(model == null) {
            logger.warn(CREATE_MODEL_NULLABLE);
            return Optional.empty();
        }

        Optional<User> user = userService.getAuthorized();
        if(!user.isPresent() || !user.get().getId().equals(model.getTopicStarterId()))
            throw new NullPointerException("USER ID NOT MATCH TOPIC STARTER");

        model.setCreateDate((model.getCreateDate() == null)
                ? Timestamp.valueOf(LocalDateTime.now())
                : model.getCreateDate());

        model.setLastModifyDate((model.getLastModifyDate() == null || model.getLastModifyDate().before(Timestamp.valueOf(LocalDateTime.now())))
                ? Timestamp.valueOf(LocalDateTime.now())
                : model.getLastModifyDate());

        Optional<Task> task = super.save(model);

        return task;
    }

    @Transactional
    @Override
    public Optional<Task> update(Task model) {
        if(model == null) {
            logger.warn(UPDATE_MODEL_NULLABLE);
            return Optional.empty();
        }

        Optional<User> user = userService.getAuthorized();
        if(!user.isPresent() || !user.get().getId().equals(model.getTopicStarterId()))
            throw new NullPointerException("USER ID NOT MATCH TOPIC STARTER");

        Optional<Task> toSave = get(model.getId());

        // OR SAVE AS A RECENT ONE, THAT IS A QUESTION
        if(!toSave.isPresent()) {
            logger.warn(UPDATE_NOT_FOUND);
            return Optional.empty();
        }

        model.setLastModifyDate(Timestamp.valueOf(LocalDateTime.now()));

        return save(model);
    }

    @Transactional
    @Override
    public void removeByCheckUserId(Long id, Long userId) {
        repository.removeByIdAndTopicStarterId(id, userId);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        Optional<User> user = userService.getAuthorized();

        if(user.isPresent())
            removeByCheckUserId(id, user.get().getId());
        else
            throw new NullPointerException("USER ID NOT MATCH TOPIC STARTER ID");
    }
}
