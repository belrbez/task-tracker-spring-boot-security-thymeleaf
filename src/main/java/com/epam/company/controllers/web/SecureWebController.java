package com.epam.company.controllers.web;

/*
 * Created by @belrbeZ
 */


import com.epam.company.model.dao.User;
import com.epam.company.model.dto.UserFormDTO;
import com.epam.company.service.IUserService;
import com.epam.company.service.impl.UserService;
import com.epam.company.util.ModelTranslator;
import com.epam.company.util.resolvers.TemplateResolver;
import com.epam.company.util.resolvers.WebResolver;
import com.epam.company.util.secure.UserFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Control Web & Api OAUTH and Registration forms
 */
@Controller
public class SecureWebController {

    private static final Logger logger = LoggerFactory.getLogger(SecureWebController.class);

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

//    @Autowired
//    private UserFormValidator userFormValidator;

    @Autowired
    public SecureWebController(IUserService userService, AuthenticationManager authenticationManager) {
        this.userService = (UserService)userService;
        this.authenticationManager = authenticationManager;
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @RequestMapping(value = WebResolver.LOGIN, method = RequestMethod.GET)
    public ModelAndView loginGet(){
        return new ModelAndView(TemplateResolver.LOGIN);
    }

    @RequestMapping(value = WebResolver.DENIED, method = RequestMethod.GET)
    public ModelAndView accessDenied(){
        return new ModelAndView(TemplateResolver.DENIED);
    }

    @RequestMapping(value = WebResolver.LOGOUT, method = RequestMethod.GET)
    public ModelAndView logoutGet (HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(TemplateResolver.LOGIN + "?logout=true"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null)
            new SecurityContextLogoutHandler().logout(request, response, auth);

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.REGISTER, method = RequestMethod.GET)
    public ModelAndView registrationGet(){
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.REGISTER);

        modelAndView.addObject("user", new UserFormDTO());

        return modelAndView;
    }


    @RequestMapping(value = WebResolver.REGISTER, method = RequestMethod.POST)
    public ModelAndView registrationPost(@ModelAttribute("user") @Valid UserFormDTO user, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        if(userService.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
//            modelAndView.setViewName(TemplateResolver.REGISTER);
        }

//        userFormValidator.validate(user, bindingResult.get());

        if (!bindingResult.hasErrors()) {
            try {
                Optional<User> usr = userService.save(ModelTranslator.toDAO(user));

                if(!usr.isPresent())
                    throw new NullPointerException();

                authenticateUserAndSetSession(usr.get(), request);

                modelAndView.setViewName(TemplateResolver.redirect(WebResolver.FEED));
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("message", "Error");
            }
        } else {
            modelAndView.setViewName(TemplateResolver.REGISTER);
        }

        return modelAndView;
    }
}
