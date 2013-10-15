package org.alfresco.labs.quickrest;

import org.alfresco.rest.api.Nodes;
import org.alfresco.rest.api.nodes.NodesEntityResource;
import org.alfresco.rest.framework.WebApiDescription;
import org.alfresco.rest.framework.resource.RelationshipResource;
import org.alfresco.rest.framework.resource.actions.interfaces.RelationshipResourceAction;
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Lists folders, url is /private/mycompany/versions/1/nodes/{nodeId}/folders
 *
 * @author Gethin James
 */
@RelationshipResource(name = "folders",  entityResource = NodesEntityResource.class, title = "Folders on a node")
public class FolderRelationshipResource implements RelationshipResourceAction.Read<FileInfo>
{

    @Autowired
    private ServiceRegistry registry;
    
    @Autowired
    private Nodes nodes;
    
    /*
     * @see org.alfresco.rest.framework.resource.actions.interfaces.RelationshipResourceAction.Read#readAll(java.lang.String, org.alfresco.rest.framework.resource.parameters.Parameters)
     */
    @Override
    @WebApiDescription(title = "Returns a list of folders for the nodeId")
    public CollectionWithPagingInfo<FileInfo> readAll(String nodeId, Parameters params)
    {
        final NodeRef nodeRef = nodes.validateNode(nodeId);
        return CollectionWithPagingInfo.asPaged(params.getPaging(), registry.getFileFolderService().listFolders(nodeRef));
    }

}
