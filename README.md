## Alfresco Quick Rest Example
This is a very basic example of how to start developing a Rest API on the Alfresco Platform.

It uses:

1. [Maven Alfresco SDK](https://artifacts.alfresco.com/nexus/content/repositories/alfresco-docs/alfresco-lifecycle-aggregator/latest/index.html)
2. [Alfresco Community 4.2](http://www.alfresco.com/products/community/4-2)

#### Step by step instructions of everything I did to create this project
* Typed : `mvn archetype:generate -DarchetypeCatalog=https://artifacts.alfresco.com/nexus/content/groups/public/archetype-catalog.xml -Dfilter=org.alfresco.maven.archetype:` *(Make sure you include the trailing colon)*
* Answered to the archetype questions:
	- 1
	- 3
	- org.alfresco.labs
	- quickrest
	- Y
* Git init and added exclusions
* Imported as a Maven Project into Eclipse
* Added README.md
* Deleted unused files, see [commit](https://github.com/AlfrescoLabs/quickrest/commit/1304c456f7f239c552bc2428058d264e823fd931)
* Updated the pom.xml, changed the Alfresco version, eclipse quickfix, see [commit](https://github.com/AlfrescoLabs/quickrest/commit/4b9b49a761f6ee3850b77b128532422b8e2192f2)
* Updated the pom.xml, Updated the compiler to jdk 1.7, added an work around for a spring-context issue, see [commit](https://github.com/AlfrescoLabs/quickrest/commit/54e9fea3bbebc6cb2a5b9655ef7da79f474506ce)
* Updated the pom.xml, Made sure alfresco.client.war.version matches the Alfresco version, see [commit](https://github.com/AlfrescoLabs/quickrest/commit/32b2b24032c41e14800093a9aa356cea0ee9fd4f)
* Updated the logging and basic spring context, see [commit](https://github.com/AlfrescoLabs/quickrest/commit/37394707414b94b3b8fa060895edcb1fcfe341a7) 
* Updated the pom.xml, Added alfresco-remote-api as a "provided" dependency, see [commit](https://github.com/AlfrescoLabs/quickrest/commit/b242b8110ccc17bd5a5b60dccc60edb217a16fbe) 
* Ran `mvn integration-test -Pamp-to-war` [See Maven Amp Commands](https://artifacts.alfresco.com/nexus/content/repositories/alfresco-docs/alfresco-lifecycle-aggregator/latest/archetypes/alfresco-amp-archetype/usage.html#Commands)
* In a browser : [http:localhost:8080/quickrest/](http:localhost:8080/quickrest/)  **Alfresco is up and running :)**

### Groovy
Alfresco 4.2.e comes with support for Groovy 2.1.7 and invoke dynamic.

Some examples of statically compiled Groovy Rest Endpoints are:
[InfoEntityResource.groovy](https://github.com/AlfrescoLabs/quickrest/blob/groovy/src/main/groovy/org/alfresco/labs/quickrest/groovy/InfoEntityResource.groovy) and [VersionsResource.groovy](https://github.com/AlfrescoLabs/quickrest/blob/groovy/src/main/groovy/org/alfresco/labs/quickrest/groovy/VersionsResource.groovy)

### License
Copyright (C) 2013 Alfresco Software Limited

This file is part of an unsupported extension to Alfresco.

Alfresco Software Limited licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.