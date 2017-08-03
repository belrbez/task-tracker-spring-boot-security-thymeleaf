package com.epam.company.controllers.web;

import com.epam.company.model.dao.Task;
import com.epam.company.model.dao.User;
import com.epam.company.model.dto.TaskDTO;
import com.epam.company.service.ISubscription;
import com.epam.company.service.ITaskService;
import com.epam.company.service.IUserService;
import com.epam.company.service.impl.SubscriptionService;
import com.epam.company.service.impl.TaskService;
import com.epam.company.service.impl.UserService;
import com.epam.company.util.ModelTranslator;
import com.epam.company.util.Validator;
import com.epam.company.util.resolvers.TemplateResolver;
import com.epam.company.util.resolvers.WebResolver;
import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by @belrbeZ
 */
@Controller
public class TaskWebController {

    private static final Logger logger = LoggerFactory.getLogger(TaskWebController.class);

    private final TaskService taskService;
    private final UserService userService;
    private final ISubscription subsService;

    private final String MSG = "message";

    @Autowired
    public TaskWebController(TaskService taskService,
                             UserService userService,
                             SubscriptionService subsService) {
        this.taskService = taskService;
        this.userService = userService;
        this.subsService = subsService;
    }

    /**
     * Task View Template
     */
    @RequestMapping(value = WebResolver.TASK + "/{id}", method = RequestMethod.GET)
    public ModelAndView taskGet(@PathVariable(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()){
            if(Validator.isIdValid(taskId)) {
                Optional<Task> daoTask = taskService.get(taskId);
                if(daoTask.isPresent()) {
                    User creator = (daoTask.get().getTopicStarterId().equals(user.get().getId())) ? user.get() : userService.get(daoTask.get().getTopicStarterId()).get();
                    modelAndView.addObject("creator", creator);
                    modelAndView.addObject("user", ModelTranslator.toDTO(user.get()));
                    TaskDTO taskDTO = ModelTranslator.toDTO(daoTask.get());

                    Optional<List<User>> subscribers = userService.getAllByIds(
                            subsService.getTaskSubscribers(daoTask.get().getId()).get());

                    if(subscribers.isPresent()) {
                        modelAndView.addObject("subscribers", subscribers.get());
                        if(subscribers.get().stream().anyMatch(userTmp -> userTmp.getEmail().equals(user.get().getEmail()))) {
                            taskDTO.setSubscribed(true);
                        } else {
                            taskDTO.setSubscribed(false);
                        }
                    } else {
                        modelAndView.addObject("subscribers", Collections.emptyList());
                    }

                    modelAndView.addObject("task", taskDTO);
                    return modelAndView;
                }
                else modelAndView.addObject(MSG, "No Such Task!");
            }
            else modelAndView.addObject(MSG, "Invalid Task ID!");
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.FEED));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK + "/{id}", method = RequestMethod.DELETE)
    public ModelAndView taskDelete(@PathVariable(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(WebResolver.FEED));

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent()) {
            if(Validator.isIdValid(taskId)) {
                try {
                    taskService.remove(taskId);
                } catch (Exception e) {
                    modelAndView.addObject(MSG, "No Such Task!");
                }
            }
            else modelAndView.addObject(MSG, "Invalid Task ID!");
        }
        else modelAndView.addObject(MSG, "ReLogin First");

        return modelAndView;
    }


    /**
     * Task Update/Create Form Template
     */
    @RequestMapping(value = WebResolver.TASK_FORM, method = RequestMethod.GET)
    public ModelAndView taskCreateForm(@RequestParam(value = "id", required = false) Long taskId,
//                                       @RequestParam(value = "lat", required = false) String latitude,
//                                       @RequestParam(value = "lng", required = false) String longitude,
//                                       @RequestParam(value = "radius", required = false) Integer radius,
                                       Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK_FORM);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()) {
            TaskDTO dto = new TaskDTO();
            dto.setTopicStarterId(user.get().getId());


            if(Validator.isIdValid(taskId)) {
                Optional<Task> updateTask = taskService.get(taskId);

                if(updateTask.isPresent()) {
                    Optional<List<User>> subscribers = userService.getAllByIds(
                            subsService.getTaskSubscribers(updateTask.get().getId()).get());

                    if(subscribers.isPresent()) {
                        modelAndView.addObject("subscribers", subscribers.get());
                    } else {
                        modelAndView.addObject("subscribers", Collections.emptyList());
                    }

                    dto = ModelTranslator.toDTO(updateTask.get());

                }
            }
//            dto = ModelTranslator.toDTO(taskService.saveDTO(dto).get());

            modelAndView.addObject("user", user.get());
            modelAndView.addObject("task", dto);
            return modelAndView;
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.FEED));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK_FORM, method = RequestMethod.POST)
    public ModelAndView taskUpdateOrCreate(@Valid TaskDTO task, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> user = userService.getAuthorized();
//        task.setGeo(new SimpleGeoPoint(task.getLatitude(), task.getLongitude(), task.getRadius()));

        if(user.isPresent()) {

//            if (Validator.isSimpleGeoValid(task.getGeo())) {

                try {
                    Optional<Task> savedTask;

                    if (Validator.isIdValid(task.getId()))
                        savedTask = taskService.updateDTO(task);
                    else {
                        savedTask = taskService.saveDTO(task);
                        if(!savedTask.isPresent())
                            throw new Exception("Can't save Task!");
                    }

                    modelAndView.addObject("user", user.get());
                    modelAndView.addObject("task", ModelTranslator.toDTO(savedTask.get()));
                    modelAndView.setViewName(TemplateResolver.redirect(WebResolver.TASK + "/" + savedTask.get().getId()));
                    return modelAndView;

                } catch (Exception e) {
                    modelAndView.addObject(MSG, "No Such Task!");
                    modelAndView.addObject("user", user.get());
                    modelAndView.addObject("task", task);
                    modelAndView.setViewName(TemplateResolver.TASK_FORM);
                    logger.error("Can't create Task!", e);
                    return modelAndView;
                }
//            } else {
//                modelAndView.addObject(MSG, "No Geo Point!");
//                return modelAndView;
//            }
//            modelAndView.setViewName(TemplateResolver.redirect(WebResolver.FEED));
//            return modelAndView;
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(WebResolver.LOGIN));
        return modelAndView;
    }
/*
    @RequestMapping(value = WebResolver.TASK + "/{id}/" + WebResolver.TASK_COMMENT, method = RequestMethod.POST)
    public ModelAndView taskAddComment(@PathVariable(value = "id") Long taskId,
                                       CommentDTO comment,
                                       Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(WebResolver.TASK + "/" + taskId));

        try {
            Optional<User> user;
            if((user = userService.getAuthorized()).isPresent()) {
                comment.setId(null);
                comment.setTaskId(taskId);
                comment.setUserId(user.get().getId());
                commentService.saveDTO(comment);
            }
        } catch (Exception e) {
            logger.warn("ERROR ON SAVING COMMENT");
        }

        return modelAndView;
    }

    *//**
     * UI GEO
     *//*
    @RequestMapping(value = WebResolver.TASK_FORM + "/withGeo", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView taskCreateForm(@Valid @RequestBody GeoPointDTO geoPointDTO, BindingResult result, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK_FORM);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()) {
            TaskDTO dto = new TaskDTO();
            dto.setTopicStarterId(user.get().getId());
            dto.setGeo(new SimpleGeoPoint(geoPointDTO.getLatitude().toString(),
                    geoPointDTO.getLongitude().toString(),
                    geoPointDTO.getRadius()));
            dto.setLatitude(geoPointDTO.getLatitude());
            dto.setLongitude( geoPointDTO.getLongitude());
            dto.setRadius(geoPointDTO.getRadius());

            modelAndView.addObject("user", user.get());
            modelAndView.addObject("task", dto);
            return modelAndView;
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.MAP));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK_FORM + "/withSimpleGeo", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView taskSimpleCreateForm(@Valid SimpleGeoPoint simpleGeoPoint, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK_FORM);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()) {
            TaskDTO dto = new TaskDTO();
            dto.setTopicStarterId(user.get().getId());

            dto.setGeo(new SimpleGeoPoint(simpleGeoPoint.getLatitude().toString(),
                    simpleGeoPoint.getLongitude().toString(),
                    simpleGeoPoint.getRadius()));
            dto.setLatitude(simpleGeoPoint.getLatitude());
            dto.setLongitude( simpleGeoPoint.getLongitude());
            dto.setRadius(simpleGeoPoint.getRadius());

            modelAndView.addObject("user", user.get());
            modelAndView.addObject("task", dto);
            return modelAndView;
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.MAP));

        return modelAndView;
    }*/


    /**
     * Task Subscribe Mapping
     */
    @RequestMapping(value = WebResolver.TASK_SUBS, method = RequestMethod.POST)
    public ModelAndView taskSubscribe(@RequestParam(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent() && Validator.isIdValid(taskId)) {
            try {
                subsService.subscribe(user.get().getId(), taskId);
                modelAndView.setViewName(TemplateResolver.redirect(WebResolver.TASK + "/" + taskId));

            } catch (Exception e) {
                logger.warn(e.getMessage() + " | " + "You can't Subscribe to " + taskId);
                modelAndView.addObject(MSG, "You can't Subscribe");
                modelAndView.setViewName(TemplateResolver.redirect(WebResolver.FEED));
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK_UNSUBS, method = {RequestMethod.DELETE, RequestMethod.POST})
    public ModelAndView taskUnSubscribe(@RequestParam(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent() && Validator.isIdValid(taskId)) {
            try {
                subsService.unSubscribe(user.get().getId(), taskId);
                modelAndView.setViewName(TemplateResolver.redirect(WebResolver.TASK + "/" + taskId));

            } catch (Exception e) {
                logger.warn(e.getMessage() + " | " + "You are not Subscribed to " + taskId);
                modelAndView.addObject(MSG, "You are not Subscribed to " + taskId);
                modelAndView.setViewName(TemplateResolver.redirect(WebResolver.FEED));
            }
        }

        return modelAndView;
    }
}
