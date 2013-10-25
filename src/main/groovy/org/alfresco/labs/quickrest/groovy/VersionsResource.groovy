package org.alfresco.labs.quickrest.groovy

import groovy.transform.CompileStatic

import org.alfresco.rest.api.Nodes
import org.alfresco.rest.api.nodes.NodesEntityResource
import org.alfresco.rest.framework.core.exceptions.RelationshipResourceNotFoundException;
import org.alfresco.rest.framework.resource.RelationshipResource
import org.alfresco.rest.framework.resource.actions.interfaces.RelationshipResourceAction
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo
import org.alfresco.rest.framework.resource.parameters.Parameters
import org.alfresco.service.ServiceRegistry
import org.alfresco.service.cmr.repository.NodeRef
import org.alfresco.service.cmr.version.Version
import org.springframework.beans.factory.annotation.Autowired

/**
 * This represents a url endpoint in the api, /private/mycompany/versions/1/nodes/{nodeId}/versions, see package-info.groovy
 *
 * Lists the versions on a node
 * 
 * @author Gethin James
 */
@CompileStatic
@RelationshipResource(name = "versions",  entityResource = NodesEntityResource.class, title = "Versions on a node")
class VersionsResource implements RelationshipResourceAction.Read<Version>, RelationshipResourceAction.ReadById<Version>
{
    @Autowired
    private ServiceRegistry registry
	
	@Autowired
	private Nodes nodes

    @Override
    public CollectionWithPagingInfo<Version> readAll(String nodeId, Parameters params)
    {
		final NodeRef nodeRef = nodes.validateNode(nodeId)
		CollectionWithPagingInfo.asPaged(params.paging, registry.versionService.getVersionHistory(nodeRef)?.allVersions)
    }

	@Override
	public Version readById(String nodeId, String versionLabel, Parameters parameters) throws RelationshipResourceNotFoundException {
		final NodeRef nodeRef = nodes.validateNode(nodeId)
		registry.versionService.getVersionHistory(nodeRef)?.getVersion(versionLabel)
	}

}
