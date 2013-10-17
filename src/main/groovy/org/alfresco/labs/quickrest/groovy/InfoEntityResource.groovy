package org.alfresco.labs.quickrest.groovy

import groovy.transform.CompileStatic

import org.alfresco.rest.framework.Api
import org.alfresco.rest.framework.core.ResourceLookupDictionary
import org.alfresco.rest.framework.core.ResourceWithMetadata
import org.alfresco.rest.framework.core.ResourceMetadata.RESOURCE_TYPE
import org.alfresco.rest.framework.resource.EntityResource
import org.alfresco.rest.framework.resource.actions.interfaces.EntityResourceAction
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo
import org.alfresco.rest.framework.resource.parameters.Parameters
import org.springframework.beans.factory.annotation.Autowired

/**
 * This represents a url endpoint in the api, /private/mycompany/versions/1/info, see package-info.groovy
 *
 * Lists the public api end points.
 * 
 * @author Gethin James
 */
@CompileStatic
@EntityResource(name="info", title = "Info on all the apis")
class InfoEntityResource implements EntityResourceAction.Read<ResourceWithMetadata>
{
    @Autowired
    private ResourceLookupDictionary lookup

    @Override
    public CollectionWithPagingInfo<ResourceWithMetadata> readAll(Parameters params)
    {
        Collection<ResourceWithMetadata> apiResources = lookup.dictionary.allResources[Api.ALFRESCO_PUBLIC].values()
        apiResources = apiResources.findAll { ResourceWithMetadata res ->
            RESOURCE_TYPE.ENTITY.equals(res.metaData.type) 
        }
        return CollectionWithPagingInfo.asPaged(params.paging, apiResources)
    }

}
