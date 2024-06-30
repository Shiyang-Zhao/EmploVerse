package com.emploverse.backend.repository.user;

import com.emploverse.backend.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByUsernameContaining(String username);

    List<User> findByEnabled(boolean enabled);

    Page<User> findAll(Pageable pageable);
}
