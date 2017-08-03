package com.epam.company.service.impl;

import com.epam.company.model.dao.Task;
import com.epam.company.model.dto.TaskDTO;
import com.epam.company.model.types.FeedType;
import com.epam.company.service.IFeed;
import com.epam.company.util.ModelTranslator;
import com.epam.company.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by @belrbeZ
 *
 * Aggregates Feed based on Tasks
 */
@Service
@Primary
public class FeedService implements IFeed {

    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);

    private final int CHART_FEED_SIZE = 20;
    private final int RECENT_FEED_SIZE = 30;

    protected final SubscriptionService subsService;
    protected final TaskService taskService;


    @Autowired
    public FeedService(SubscriptionService subsService, TaskService taskService ) {
        this.subsService = subsService;
        this.taskService = taskService;
    }

    /*@Override
    public Optional<List<TaskDTO>> recent(Long userId) {
        if(userId == null)
            return Optional.empty();

        return Optional.of(subsService.fillSubs(userId, );
    }*/

    @Override
    public Optional<List<TaskDTO>> mine(Long userId) {
        if(userId == null)
            return Optional.empty();

//        return Optional.of(subsService.fillSubs(userId, ModelTranslator.tasksToDTO(tasks.entrySet()
//                .stream().filter(task -> task.getValue().getTopicStarterId().equals(userId))
//                .map(Map.Entry::getValue).collect(Collectors.toList()))));
        return Optional.of(ModelTranslator.tasksToDTO(taskService.getByUserId(userId).get()));
    }

    @Override
    public Optional<List<TaskDTO>> all(Long userId) {
        if(userId == null)
            return Optional.empty();

//        return Optional.of(subsService.fillSubs(userId, ModelTranslator.tasksToDTO(tasks.entrySet().stream()
//                .map(Map.Entry::getValue).collect(Collectors.toList()))));
        return Optional.of(ModelTranslator.tasksToDTO(taskService.getAll().get()));
    }

    @Override
    public Optional<List<TaskDTO>> subscribed(Long userId) {
        Optional<Set<Long>> subs = subsService.getUserSubscriptions(userId);

        if(subs.isPresent()) {
//            return Optional.of(subsService.fillSubs(userId, ModelTranslator.tasksToDTO(tasks.entrySet()
//                    .stream().filter(t -> subs.get().contains(t.getKey()))
//                    .map(Map.Entry::getValue).collect(Collectors.toList()))));
            return Optional.of(ModelTranslator.tasksToDTO(taskService.getByIds(subs.get()).get()));
        } else
            return Optional.empty();

    }

    @Override
    public Optional<List<TaskDTO>> searchByTheme(Long userId, String theme, FeedType feedType) {
        Optional<List<TaskDTO>>  tasksToProceed = getByFeedType(userId, feedType);

        if(Validator.isStrEmpty(theme))
            return tasksToProceed;

        //!WARN it will work with internet
//        return tasksToProceed.map(taskDTOS -> Optional.of(taskDTOS.stream()
//                .filter(task -> satisfyThemeSearch(task.getTheme(), theme))
//                .collect(Collectors.toList()))).orElse(tasksToProceed);
        return tasksToProceed.map(taskDTOS -> Optional.of(taskDTOS.stream()
                .filter(task -> task.getTheme().equals(theme))
                .collect(Collectors.toList()))).orElse(tasksToProceed);
    }
/*
    @Override
    public Optional<List<TaskDTO>> searchByTags(Long userId, List<String> tags, FeedType feedType) {
        Optional<List<TaskDTO>>  tasksToProceed = getByFeedType(userId, feedType);

        if(tags == null || tags.isEmpty())
            return tasksToProceed;

        final Set<String> tagSet = new HashSet<>(tags);

        return tasksToProceed.map(taskDTOS -> Optional.of(taskDTOS.stream()
                .filter(task -> satisfyTagSearch(task.getTagsAsString(), tagSet))
                .collect(Collectors.toList()))).orElse(tasksToProceed);
    }*/


    /**
     * Search private util/support methods
     */
    private Optional<List<TaskDTO>> getByFeedType(Long userId, FeedType type) {
        switch (type) {
            case SUBSCRIBED: return subscribed(userId);
            case OWNER:      return mine(userId);
            case RECENT:
            case LOCAL:
            case CHART:
            case ALL:
            default:         return all(userId);
        }
    }

    /*private boolean satisfyThemeSearch(String target, String desired) {
        return FuzzySearch.partialRatio(target, desired) > 85;
    }*/

}
