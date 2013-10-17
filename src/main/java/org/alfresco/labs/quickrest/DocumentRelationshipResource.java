package org.alfresco.labs.quickrest;


import java.util.ArrayList;
import java.util.List;

import org.alfresco.rest.api.Nodes;
import org.alfresco.rest.api.model.Document;
import org.alfresco.rest.api.nodes.NodesEntityResource;
import org.alfresco.rest.framework.WebApiDescription;
import org.alfresco.rest.framework.core.exceptions.InvalidArgumentException;
import org.alfresco.rest.framework.resource.RelationshipResource;
import org.alfresco.rest.framework.resource.actions.interfaces.RelationshipResourceAction;
import org.alfresco.rest.framework.resource.parameters.CollectionWithPagingInfo;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Lists documents, url is /private/mycompany/versions/1/nodes/{nodeId}/documents
 *
 * @author Gethin James
 */
@RelationshipResource(name = "documents",  entityResource = NodesEntityResource.class, title = "Documents on a node")
public class DocumentRelationshipResource implements RelationshipResourceAction.Read<Document>
{
    private static Log logger = LogFactory.getLog(DocumentRelationshipResource.class);
    
    @Autowired
    private ServiceRegistry registry;
    
    @Autowired
    private Nodes nodes;
    
    /*
     * @see org.alfresco.rest.framework.resource.actions.interfaces.RelationshipResourceAction.Read#readAll(java.lang.String, org.alfresco.rest.framework.resource.parameters.Parameters)
     */
    @Override
    @WebApiDescription(title = "Returns a list of documents for the nodeId")
    public CollectionWithPagingInfo<Document> readAll(String nodeId, Parameters params)
    {
        final NodeRef nodeRef = nodes.validateNode(nodeId);
        
        String paramXPath = params.getParameter("xpath");
        String xpath = paramXPath!=null?paramXPath:"*";
        
        List<NodeRef> nodeRefs = registry.getSearchService().selectNodes(nodeRef, xpath, null, registry.getNamespaceService(), false);
        return CollectionWithPagingInfo.asPaged(params.getPaging(), nodesToDocuments(nodeRefs));
    }
    
    
    /**
     * Converts a list of noderefs to a list of Documents
     * @param nodeRefs
     * @return List<Document>
     */
    private List<Document> nodesToDocuments (List<NodeRef> nodeRefs)
    {
        List<Document> documents = new ArrayList<>();
        
        for (NodeRef nodeRef : nodeRefs)
        {
            try
            {
                Document document = nodes.getDocument(nodeRef);
                documents.add(document);
            }
            catch (InvalidArgumentException iae)
            {
                logger.warn("Not a Document: "+nodeRef);
            }   
        }
        
        return documents;
    }

}
