package com.studydesk.Controller;

import com.studydesk.Model.Profile;
import com.studydesk.Resource.ProfileResource;
import com.studydesk.Resource.SaveProfileResource;
import com.studydesk.Service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProfileService profileService;

    //Get all profiles
    @GetMapping("/profiles")
    public Page<ProfileResource> getAllProfiles(Pageable pageable) {
        Page<Profile> profilesPage = profileService.getAllProfiles(pageable);
        List<ProfileResource> resources = profilesPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    //Get profiles by id
    @GetMapping("/profiles/{id}")
    public ProfileResource getProfileById(@PathVariable(name = "id") Long profileId) {
        return convertToResource(profileService.getProfileById(profileId));
    }

    //Get by UserId
    @GetMapping("users/{userId}/profiles")
    public Page<ProfileResource> getProfileByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<Profile> profilePage = profileService.getAllProfilesByUserId(userId, pageable);
        List<ProfileResource> resources = profilePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    //Create
    @PostMapping("/users/{userId}/profiles")
    public ProfileResource createProfile(@PathVariable(name = "userId") Long userId, @Valid @RequestBody SaveProfileResource resource) {
        Profile profile = convertToEntity(resource);
        return convertToResource(profileService.createProfile(userId, profile));
    }

    //Update
    @PutMapping("users/{userId}/profiles/{id}")
    public ProfileResource updateProfile(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "id") Long profileId,
                                         @Valid @RequestBody SaveProfileResource resource) {
        return convertToResource(profileService.updateProfile(userId, profileId, convertToEntity(resource)));
    }

    //Eliminar
    @DeleteMapping("users/{userId}/profiles/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "id") Long profileId) {
        return profileService.deleteProfile(userId, profileId);
    }

    private Profile convertToEntity(SaveProfileResource resource) {
        return mapper.map(resource, Profile.class);
    }

    private ProfileResource convertToResource(Profile entity) {
        return mapper.map(entity, ProfileResource.class);
    }

}
