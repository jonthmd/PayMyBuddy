package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.ProfileDTO;
import com.paymybuddy.pmb.exceptions.InvalidPasswordException;
import com.paymybuddy.pmb.service.ProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Displays the profile page.
     *
     * @param model     Model used to transmit data.
     * @param principal The current logged user.
     * @return The profile view.
     */
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        ProfileDTO profileDTO = profileService.getProfile(principal.getName());
        model.addAttribute("profileDTO", profileDTO);

        log.info("Profile view");

        return "profile";
    }

    /**
     * Validates the profile update.
     *
     * @param profileDTO         DTO used to represent a user profile.
     * @param bindingResult      The data validation, displays error message if any.
     * @param model              Model used to transmit data.
     * @param principal          The current logged user.
     * @param redirectAttributes Attributes used to display message after redirect.
     * @return A redirect to the profile view.
     */
    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute ProfileDTO profileDTO, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());

            return "redirect:/profile";
        }

        try {
            profileService.updatePassword(principal.getName(), profileDTO.getPassword());
            redirectAttributes.addFlashAttribute("message", "Mot de passe modifié !");
        } catch (InvalidPasswordException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("profileDTO", new ProfileDTO());
        }

        return "redirect:/profile";
    }
}
