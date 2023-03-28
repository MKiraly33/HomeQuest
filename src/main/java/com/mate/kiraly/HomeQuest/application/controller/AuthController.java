package com.mate.kiraly.HomeQuest.application.controller;

import com.mate.kiraly.HomeQuest.userhandling.appuser.AppUser;
import com.mate.kiraly.HomeQuest.userhandling.appuser.AppUserService;
import com.mate.kiraly.HomeQuest.userhandling.registration.RegistrationRequest;
import com.mate.kiraly.HomeQuest.userhandling.registration.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final AppUserService userService;

    @GetMapping(path = "register")
    public String registerPage(Model model){
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    @PostMapping(path = "register")
    public String register(HttpServletRequest request){
        RegistrationRequest registrationRequest = new RegistrationRequest(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("password"));
        registrationService.register(registrationRequest);
        return "login";
    }

    @GetMapping(path = "login")
    public String login(){
        return "login";
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, Model model){
        registrationService.confirmToken(token);
        model.addAttribute("confirmed", true);
        return "login";
    }

    @GetMapping(path = "profile")
    public String profilePage(Model model){
        model.addAttribute("currentUser", ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return "profile";
    }

    @PostMapping(path = "profile")
    public String profile(Model model, HttpServletRequest request){
        AppUser user = new AppUser();
        user.setId(Long.parseLong(request.getParameter("id")));
        user.setEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setPassword(request.getParameter("password"));
        userService.updateUser(user);
        return "redirect:/logout";
    }
}
