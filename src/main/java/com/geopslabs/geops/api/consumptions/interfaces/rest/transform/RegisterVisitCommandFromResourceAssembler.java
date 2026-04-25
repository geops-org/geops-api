package com.geopslabs.geops.api.consumptions.interfaces.rest.transform;

import com.geopslabs.geops.api.consumptions.domain.model.commands.RegisterVisitCommand;
import com.geopslabs.geops.api.consumptions.interfaces.rest.resources.RegisterVisitResource;

public class RegisterVisitCommandFromResourceAssembler {

    public static RegisterVisitCommand toCommandFromResource(RegisterVisitResource resource) {
        return new RegisterVisitCommand(resource.userId(), resource.offerId(), resource.notes());
    }
}
