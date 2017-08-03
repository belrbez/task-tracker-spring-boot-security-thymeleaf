package com.epam.company.controllers.web;

import com.epam.company.model.dao.User;
import com.epam.company.model.dto.TaskDTO;
import com.epam.company.model.types.FeedType;
import com.epam.company.service.IFeed;
import com.epam.company.service.impl.FeedService;
import com.epam.company.service.impl.UserService;
import com.epam.company.util.resolvers.TemplateResolver;
import com.epam.company.util.resolvers.WebResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by @belrbeZ
 */
@Controller
public class FeedWebController {

    private final IFeed feedService;
    private final UserService userService;

    private final String TASKS_OBJ = "tasks";
    private final String USER_ID_OBJ = "userId";
    private final String MSG = "msg";

    private final String ATTR_SEARCH = "search";
    private final String ATTR_FILTER = "filter";

    @Autowired
    public FeedWebController(UserService userService,
                             FeedService feedService) {
        this.userService = userService;
        this.feedService = feedService;
    }

    @RequestMapping(value = WebResolver.FEED, method = RequestMethod.GET)
    public ModelAndView feedGet(Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.FEED);

        Optional<User> user = userService.getAuthorized();
        Optional<List<TaskDTO>> tasks = Optional.empty();

        if(!user.isPresent())
            modelAndView.addObject(MSG, "There no tasks for you.. Sorry..");
        else if(!(tasks = feedService.subscribed(user.get().getId())).isPresent()) {
            modelAndView.addObject(MSG, "There no tasks for you.. Sorry..");
        } else {
            tasks = feedService.all(user.get().getId());
        }


        modelAndView.addObject(USER_ID_OBJ, (user.isPresent()) ? user.get().getId() : 0);
        modelAndView.addObject(TASKS_OBJ, (tasks.isPresent()) ? tasks.get() : Collections.emptyList());

        return modelAndView;
    }

    /**
     * Feed Search By Theme
     */
    @RequestMapping(value = WebResolver.FEED_SEARCH, method = RequestMethod.POST)
    public ModelAndView feedSearch(@RequestParam(value = ATTR_SEARCH) String theme, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.FEED);

        Optional<List<TaskDTO>> tasks = Optional.empty();
        Optional<User> user = userService.getAuthorized();

        if(user.isPresent())
            tasks = feedService.searchByTheme(user.get().getId(), theme, FeedType.ALL);
        else
            modelAndView.addObject(MSG, "ReLogin First!");

        if(!tasks.isPresent() || tasks.get().isEmpty())
            modelAndView.addObject(MSG, "There no tasks for you.. Sorry..");

        modelAndView.addObject(USER_ID_OBJ, (user.isPresent()) ? user.get().getId() : 0);
        modelAndView.addObject(TASKS_OBJ, (tasks.isPresent()) ? tasks.get() : Collections.emptyList());

        return modelAndView;
    }

    /**
     * Feed Filter
     */
    @RequestMapping(value = WebResolver.FEED_FILTER, method = RequestMethod.GET)
    public ModelAndView feedFilter(@RequestParam(value = ATTR_FILTER, required = false) Integer type,
                                   @RequestParam(value = ATTR_SEARCH, required = false) String search,
                                   Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.FEED);

        Optional<User> user = userService.getAuthorized();
        Optional<List<TaskDTO>> tasks = Optional.empty();

        if(user.isPresent())
            if((search != null&& search!="") && type == null)
                tasks = feedService.searchByTheme(user.get().getId(), search, FeedType.ALL);
            else
                tasks = feedService.searchByTheme(user.get().getId(), search, FeedType.calc(type));
        else
            modelAndView.addObject(MSG, "ReLogin First!");

        if(!tasks.isPresent() || tasks.get().isEmpty())
            modelAndView.addObject(MSG, "There no tasks for you.. Sorry..");

        modelAndView.addObject(USER_ID_OBJ, (user.isPresent()) ? user.get().getId() : 0);
        modelAndView.addObject(TASKS_OBJ, (tasks.isPresent()) ? tasks.get() : Collections.emptyList());

        return modelAndView;
    }
}
