package com.example.usersubscription.controllers;

import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String root()
    {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @GetMapping("/login")
    public String login()
    {

        return "login";
    }

    @PostMapping("/login")
    public String login(User u, HttpServletRequest request)
    {
        User logging_user;
        logging_user = userService.login(u);
        if (logging_user != null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(logging_user,null,new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            request.getSession().setAttribute(
                    "SPRING_SECURITY_CONTEXT",
                    SecurityContextHolder.getContext()
            );
            return "redirect:/home";
        } else {
            return "redirect:/login?error=true";
        }
    }


    @GetMapping("/new-subscription")
    public String newSubscription()
    {
        return "new_subscription";
    }
    @PostMapping("/add-subscription")
    public String addSubscription(@ModelAttribute Subscription subscription)
    {
        System.out.println(subscription);
        return "new_subscription";
    }
}
