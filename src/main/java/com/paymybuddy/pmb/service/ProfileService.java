package com.paymybuddy.pmb.service;

import com.paymybuddy.pmb.dto.ProfileDTO;

/**
 * Service interface managing operations related to profile.
 */
public interface ProfileService {

    ProfileDTO getProfile(String username);
    void updatePassword(String username, String password);
}
