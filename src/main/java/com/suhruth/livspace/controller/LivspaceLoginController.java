package com.suhruth.livspace.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

//@Controller
public class LivspaceLoginController {
	
	@GetMapping("/login")
	public String login() {
		checkLoggedIn();
		return "login";
	}
	
    public String checkLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() &&
            !authentication.getAuthorities().isEmpty()) {

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
           

            return "redirect:/"+ (isAdmin ? "/spaces/dashboard" : "/spaces");
        }
        
        return "/login"; 
    } 

}
