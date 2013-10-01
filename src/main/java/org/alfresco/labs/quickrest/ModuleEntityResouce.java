package org.alfresco.labs.quickrest;

import org.alfresco.rest.framework.resource.EntityResource;
import org.alfresco.rest.framework.resource.actions.interfaces.EntityResourceAction;
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.alfresco.service.cmr.module.ModuleDetails;
import org.alfresco.service.cmr.module.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This represents a url endpoint in the api, /mycompany/private/1/modules, see package-info.java
 * 
 * The @EntityResource defines the url endpoint -> "modules"
 * EntityResourceAction."INTERFACE" specifies what CRUD actions this endpoint implements.
 * 
 * @author Gethin James
 */

@EntityResource(name="modules", title = "Handles module information.")
public class ModuleEntityResouce implements EntityResourceAction.Read<ModuleDetails>
{
    @Autowired
    private ModuleService moduleService;

    @Override
    public CollectionWithPagingInfo<ModuleDetails> readAll(Parameters params)
    {
        return CollectionWithPagingInfo.asPaged(params.getPaging(), moduleService.getAllModules());
    }

}
