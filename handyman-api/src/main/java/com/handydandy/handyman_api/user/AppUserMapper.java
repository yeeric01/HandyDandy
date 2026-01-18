package com.handydandy.handyman_api.user;

import com.handydandy.handyman_api.user.dto.AppUserCreateRequest;
import com.handydandy.handyman_api.user.dto.AppUserResponse;
import com.handydandy.handyman_api.user.dto.AppUserUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser toEntity(AppUserCreateRequest request) {
        AppUser user = new AppUser();
        user.setEmail(request.email());
        user.setRole(AppUser.Role.valueOf(request.role().toUpperCase()));
        return user;
    }

    public AppUserResponse toResponse(AppUser user) {
        return new AppUserResponse(
            user.getId(),
            user.getEmail(),
            user.getRole() != null ? user.getRole().name() : null
        );
    }

    public void updateEntity(AppUser user, AppUserUpdateRequest request) {
        if (request.email() != null) {
            user.setEmail(request.email());
        }
    }
}
