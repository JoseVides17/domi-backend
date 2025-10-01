package com.vides.domi_backend.controller;

import com.vides.domi_backend.dto.UserProfileDto;
import com.vides.domi_backend.dto.request.UpdateProfileRequest;
import com.vides.domi_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserProfileDto getMyProfile() {
        return userService.getCurrentUserProfile();
    }

    @PutMapping
    public UserProfileDto updateMyProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        return userService.updateCurrentUserProfile(updateProfileRequest);
    }

}
