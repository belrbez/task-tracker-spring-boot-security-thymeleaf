package com.epam.company.controllers.web;

import com.epam.company.util.resolvers.TemplateResolver;
import com.epam.company.util.resolvers.WebResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by @belrbeZ
 */
@Controller
public class WelcomeWebController {

    @RequestMapping(value = { "/", WebResolver.WELCOME}, method = RequestMethod.GET)
    public ModelAndView welcomeGet(Model model) {
        return new ModelAndView(TemplateResolver.WELCOME);
    }
}
