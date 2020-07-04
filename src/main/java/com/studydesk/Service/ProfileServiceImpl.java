package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Profile;
import com.studydesk.repository.ProfileRepository;
import com.studydesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Profile createProfile(Long userId, Profile profile) {
        return userRepository.findById(userId).map(user -> {
            profile.setUser(user);
            return profileRepository.save(profile);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public Profile updateProfile(Long userId, Long profileId, Profile profileRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }

        return profileRepository.findById(profileId).map(profile -> {
            profile.setFirstName(profileRequest.getFirstName());
            profile.setLastName(profileRequest.getLastName());
            return profileRepository.save(profile);
        }).orElseThrow(() -> new ResourceNotFoundException("Profile", "Id", profileId));

    }

    @Override
    public ResponseEntity<?> deleteProfile(Long userId, Long profileId) {
        return profileRepository.findByIdAndUserId(profileId, userId).map(profile -> {
            profileRepository.delete(profile);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Profile", "Id", profileId));

    }

    @Override
    public Profile getProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "Id", profileId));
    }

    @Override
    public Page<Profile> getAllProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    public Page<Profile> getAllProfilesByUserId(Long userId, Pageable pageable) {
        return profileRepository.findByUserId(userId,  pageable);
    }
}
