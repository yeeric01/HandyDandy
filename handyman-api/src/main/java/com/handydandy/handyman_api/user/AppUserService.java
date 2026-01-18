package com.handydandy.handyman_api.user;

import com.handydandy.handyman_api.exception.DuplicateResourceException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.user.dto.AppUserCreateRequest;
import com.handydandy.handyman_api.user.dto.AppUserResponse;
import com.handydandy.handyman_api.user.dto.AppUserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AppUserService {

    private final AppUserRepository userRepository;
    private final AppUserMapper mapper;

    public AppUserService(AppUserRepository userRepository, AppUserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public AppUserResponse createUser(AppUserCreateRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("User with email '" + request.email() + "' already exists");
        }
        AppUser user = mapper.toEntity(request);
        user.setPasswordHash(hashPassword(request.password()));
        return mapper.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Page<AppUserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public AppUserResponse getUserById(UUID id) {
        AppUser user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public AppUserResponse getUserByEmail(String email) {
        AppUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return mapper.toResponse(user);
    }

    public AppUserResponse updateUser(UUID id, AppUserUpdateRequest request) {
        AppUser user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.findByEmail(request.email()).isPresent()) {
                throw new DuplicateResourceException("User with email '" + request.email() + "' already exists");
            }
        }

        mapper.updateEntity(user, request);
        if (request.password() != null) {
            user.setPasswordHash(hashPassword(request.password()));
        }
        return mapper.toResponse(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }

    // Internal method to get entity (for other services)
    @Transactional(readOnly = true)
    public AppUser getEntityById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    private String hashPassword(String password) {
        // TODO: Use BCryptPasswordEncoder when Spring Security is added
        return password;
    }
}
