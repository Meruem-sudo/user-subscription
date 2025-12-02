package com.example.usersubscription.controllers;

import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.services.SubscriptionService;
import com.example.usersubscription.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/")
    public String root()
    {
        return "redirect:login";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication)
    {
        User user = (User) authentication.getPrincipal();
        List<Subscription> list = subscriptionService.getAllSubscriptions(user);
        model.addAttribute("subscriptions" ,list);
        return "home";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, Model model)
    {
        try {
            userService.register(user);
            return "redirect:/login";
        } catch (DuplicateKeyException e) {
            model.addAttribute("error", "Email già registrata");
            return "register";
        }
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
    public String addSubscription(@ModelAttribute Subscription subscription, Authentication authentication)
    {
        User user = (User) authentication.getPrincipal();
        subscription.setUser(user);
        subscriptionService.addSubscription(subscription);
        return "new_subscription";
    }
}
