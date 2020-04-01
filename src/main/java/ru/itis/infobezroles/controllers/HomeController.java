package ru.itis.infobezroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.infobezroles.models.User;
import ru.itis.infobezroles.repository.DocumentRepository;
import ru.itis.infobezroles.security.UserDetailsImpl;

@Controller
public class HomeController {

    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/")
    public String getIndexPage(Authentication authentication, ModelMap modelMap) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("documents", documentRepository.findAll());
        return "home";
    }
}