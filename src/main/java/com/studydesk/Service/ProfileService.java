package com.studydesk.Service;

import com.studydesk.Model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    Profile createProfile(Long userId, Profile profile);
    Profile updateProfile(Long userId, Long profileId, Profile profileRequest);
    ResponseEntity<?> deleteProfile(Long userId, Long profileId);
    Profile getProfileById(Long profileId);
    Page<Profile> getAllProfiles(Pageable pageable);
    Page<Profile> getAllProfilesByUserId(Long userId, Pageable pageable);
}
