package com.epam.company.controllers.web;

import com.epam.company.model.dao.User;
import com.epam.company.service.IUserService;
import com.epam.company.service.impl.UserService;
import com.epam.company.util.resolvers.TemplateResolver;
import com.epam.company.util.resolvers.WebResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AdminController {

	final IUserService userService;
	
	@Autowired
    MessageSource messageSource;

	@Autowired
	public AdminController(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = WebResolver.ADMIN, method = RequestMethod.GET)
	public ModelAndView listUsers(ModelMap model) {
		ModelAndView modelAndView = new ModelAndView(WebResolver.ADMIN);

		User user = User.EMPTY;

		List<User> users = userService.findAllUsers();
		modelAndView.addObject("users", users);
		modelAndView.addObject("new_user", user);
		modelAndView.addObject("edit", false);

		return new ModelAndView(WebResolver.ADMIN);
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = WebResolver.ADMIN_NEW_USER, method = RequestMethod.GET)
	public ModelAndView newUser(ModelMap model) {
		ModelAndView modelAndView = new ModelAndView(WebResolver.ADMIN);
		User user = User.EMPTY;
		modelAndView.addObject("new_user", user);
		modelAndView.addObject("edit", true);
		return modelAndView;
	}

//	/**
//	 * This method will be called on form submission, handling POST request for
//	 * saving user in database. It also validates the user input
//	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
//	public String saveUser(@Valid User user, BindingResult result,
//			ModelMap model) {
//
//		if (result.hasErrors()) {
//			return "registration";
//		}
//
//		*//*
//		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
//		 * and applying it on field [sso] of Model class [User].
//		 *
//		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
//		 * framework as well while still using internationalized messages.
//		 *
//		 *//*
//		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
//			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
//		    result.addError(ssoError);
//			return "registration";
//		}
//
//		userService.saveUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
//		//return "success";
//		return "registrationsuccess";
//	}
//
//
//	*//**
//	 * This method will provide the medium to update an existing user.
//	 *//*
//	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
//	public String editUser(@PathVariable String ssoId, ModelMap model) {
//		User user = userService.findBySSO(ssoId);
//		model.addAttribute("user", user);
//		model.addAttribute("edit", true);
//		return "registration";
//	}
//
//	*//**
//	 * This method will be called on form submission, handling POST request for
//	 * updating user in database. It also validates the user input
//	 *//*
//	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
//	public String updateUser(@Valid User user, BindingResult result,
//                             ModelMap model, @PathVariable String ssoId) {
//
//		if (result.hasErrors()) {
//			return "registration";
//		}
//
//		*//*//*/Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
//		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
//			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
//		    result.addError(ssoError);
//			return "registration";
//		}*//*
//
//
//		userService.updateUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
//		return "registrationsuccess";
//	}
//
//
//	*//**
//	 * This method will delete an user by it's SSOID value.
//	 *//*
//	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
//	public String deleteUser(@PathVariable String ssoId) {
//		userService.deleteUserBySSO(ssoId);
//		return "redirect:/list";
//	}
//	*/

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("users")
	public List<User> initializeProfiles() {
		return userService.findAllUsers();
	}

}
