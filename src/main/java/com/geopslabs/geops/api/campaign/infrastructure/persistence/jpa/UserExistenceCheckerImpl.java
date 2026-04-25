package com.geopslabs.geops.api.campaign.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.campaign.domain.services.UserExistenceChecker;
import com.geopslabs.geops.api.identity.domain.model.queries.GetUserByIdQuery;
import com.geopslabs.geops.api.identity.domain.services.UserQueryService;
import org.springframework.stereotype.Component;

@Component
public class UserExistenceCheckerImpl implements UserExistenceChecker {

    private final UserQueryService userQueryService;

    public UserExistenceCheckerImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public boolean existsById(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId)).isPresent();
    }
}
