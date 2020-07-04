package com.studydesk.repository;

import com.studydesk.Model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Page<Profile> findByUserId(Long userId, Pageable pageable);
    Optional<Profile> findByIdAndUserId(Long id, Long userId);
}
