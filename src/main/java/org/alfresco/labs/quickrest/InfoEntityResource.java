package org.alfresco.labs.quickrest;

import java.util.Map;

import org.alfresco.rest.framework.Api;
import org.alfresco.rest.framework.WebApiDescription;
import org.alfresco.rest.framework.core.ResourceDictionary;
import org.alfresco.rest.framework.core.ResourceLookupDictionary;
import org.alfresco.rest.framework.core.ResourceWithMetadata;
import org.alfresco.rest.framework.resource.EntityResource;
import org.alfresco.rest.framework.resource.actions.interfaces.EntityResourceAction;
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This represents a url endpoint in the api, /mycompany/private/1/info, see package-info.java
 * 
 * @author Gethin James
 */
@EntityResource(name="info", title = "Info on all the apis")
public class InfoEntityResource implements EntityResourceAction.Read<ResourceWithMetadata>
{   
    @Autowired
    private ResourceLookupDictionary lookupDictionary;
    
    @Override
    @WebApiDescription(title = "Gets details of my api", description = "A list of all possible api calls for an api.")
    public CollectionWithPagingInfo<ResourceWithMetadata> readAll(Parameters params)
    {
        ResourceDictionary resourceDic = lookupDictionary.getDictionary();
        final Map<String, ResourceWithMetadata> apiResources = resourceDic.getAllResources().get(Api.ALFRESCO_PUBLIC);
        return CollectionWithPagingInfo.asPaged(params.getPaging(), apiResources.values());
    }

}
