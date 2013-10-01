package org.alfresco.labs.quickrest;

import org.alfresco.rest.framework.WebApiDescription;
import org.alfresco.rest.framework.WebApiParam;
import org.alfresco.rest.framework.core.exceptions.EntityNotFoundException;
import org.alfresco.rest.framework.resource.EntityResource;
import org.alfresco.rest.framework.resource.actions.interfaces.EntityResourceAction;
import org.alfresco.rest.framework.resource.parameters.Parameters;

/**
 * Very basic example of a simple Rest endpoint
 * /api/-default-/private/mycompany/versions/1/hello/sometext
 */
@EntityResource(name="hello", title = "Hello to you.")
public class HelloEntityResource implements EntityResourceAction.ReadById<String>
{

    @Override
    @WebApiDescription(title = "Hello world", description = "You need to pass some text in the URL path")
    @WebApiParam(name = "hello", title="Hello param",description="Pass in the text and it will be returned to you in JSON.")
    public String readById(String hello, Parameters parameters) throws EntityNotFoundException
    {
        return "Hello "+hello;
    }

}
