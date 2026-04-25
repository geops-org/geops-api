package com.geopslabs.geops.api.notifications.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.identity.domain.model.queries.GetUserByIdQuery;
import com.geopslabs.geops.api.identity.domain.services.UserQueryService;
import com.geopslabs.geops.api.notifications.domain.services.UserValidationPort;
import org.springframework.stereotype.Component;

@Component("notificationsUserValidationPortImpl")
public class UserValidationPortImpl implements UserValidationPort {

    private final UserQueryService userQueryService;

    public UserValidationPortImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public boolean existsById(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId)).isPresent();
    }
}
