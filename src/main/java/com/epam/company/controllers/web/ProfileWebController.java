package com.epam.company.controllers.web;

import com.epam.company.model.dao.User;
import com.epam.company.model.dto.UserDTO;
import com.epam.company.service.IUserService;
import com.epam.company.util.ModelTranslator;
import com.epam.company.util.resolvers.TemplateResolver;
import com.epam.company.util.resolvers.WebResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by @belrbeZ
 */
@Controller
public class ProfileWebController {

    private final IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProfileWebController.class);

    @Autowired
    public ProfileWebController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = WebResolver.PROFILE, method = RequestMethod.GET)
    public ModelAndView profileGet(Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.PROFILE);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()) {
            UserDTO userDTO = ModelTranslator.toDTO(user.get());
            userDTO.setEmail(user.get().getEmail());
            modelAndView.addObject("user", userDTO);
        }
        else
            modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.HOME));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.PROFILE, method = RequestMethod.POST)
    public ModelAndView profileUpdate(@Valid UserDTO model, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.PROFILE);
        if (!bindingResult.hasErrors()) {
            try {
                userService.updateDTO(model);
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("message", "Error");
            }
        } else {
//            modelAndView.setViewName(TemplateResolver.PROFILE);
        }
//        return new ModelAndView(TemplateResolver.PROFILE);
        return modelAndView;
    }
}
