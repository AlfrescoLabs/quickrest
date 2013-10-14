package org.alfresco.labs.quickrest;

import org.alfresco.rest.framework.WebApiDescription;
import org.alfresco.rest.framework.core.exceptions.EntityNotFoundException;
import org.alfresco.rest.framework.resource.EntityResource;
import org.alfresco.rest.framework.resource.actions.interfaces.EntityResourceAction;
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.alfresco.service.cmr.module.ModuleDetails;
import org.alfresco.service.cmr.module.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This represents a url endpoint in the api, /private/mycompany/versions/1/modules, see package-info.java
 * 
 * The @EntityResource defines the url endpoint -> "modules"
 * EntityResourceAction."INTERFACE" specifies what CRUD actions this endpoint implements.
 * 
 * @author Gethin James
 */

@EntityResource(name="modules", title = "Handles module information.")
public class ModuleEntityResouce implements EntityResourceAction.Read<ModuleDetails>, EntityResourceAction.ReadById<ModuleDetails>
{
    @Autowired
    private ModuleService moduleService;

    @Override
    @WebApiDescription(title = "Gets all modules", description = "Gets a list of all modules currently installed.")
    public CollectionWithPagingInfo<ModuleDetails> readAll(Parameters params)
    {
        return CollectionWithPagingInfo.asPaged(params.getPaging(), moduleService.getAllModules());
    }

    @Override
    @WebApiDescription(title = "Gets a module id", description = "Gets a module by its unique id.")
    public ModuleDetails readById(String id, Parameters parameters) throws EntityNotFoundException
    {     
        ModuleDetails details = moduleService.getModule(id);
        if (details != null) return details;
        throw new EntityNotFoundException(id);  //defaults to throw not found
    }

}
