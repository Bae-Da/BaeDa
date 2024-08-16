package com.baeda.baeda.user;

import com.baeda.baeda.user.dto.CreateUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> check(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("createUserRequest",
            new CreateUserRequest(null, null, null, null));
        return "user/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute CreateUserRequest createUserRequest,
        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/signup";
        }

        userService.createUser(createUserRequest);
        return "redirect:auth/login";
    }
}
