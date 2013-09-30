package org.alfresco.labs.groovy.quickrest

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import groovyx.net.http.HTTPBuilder

import org.junit.BeforeClass
import org.junit.Test

/**
 * Some basic tests on the public api using the Groovy HTTPBuilder
 *
 * @author Gethin James
 */
@CompileStatic
class BasicRestTest {

    private static final String TENANT = "-default-"
    private static final String API_v1 = "/quickrest/api/${TENANT}/public/alfresco/versions/1"
    private static final String FAVOURITES_URL = "${API_v1}/people/-me-/favorites"

    private static HTTPBuilder alfresco

    @BeforeClass
    public static void oneTimeSetUp() {
        alfresco = new HTTPBuilder( 'http://localhost:8080' )
        alfresco.auth.basic 'admin', 'admin'
    }
    
    @Test
    @CompileStatic(TypeCheckingMode.SKIP)
    public void testCheckAdminUser() {
        
        def myself = alfresco.get( path:"${API_v1}/people/-me-/" )
        debug myself
        myself.entry.with {
            assert ("admin".equals(id))
            assert (enabled)
        }
    }
    
    @Test
    @CompileStatic(TypeCheckingMode.SKIP)
    public void testSites() {
        
        //Gets all sites
        def sites = alfresco.get( path:"${API_v1}/sites/" )        
        debug sites.list.entries.entry*.title
        assert sites.list.pagination.count > 0
        def firstSite = sites.list.entries[0].entry
        
        //Get a Site
        def aSite = alfresco.get( path:"${API_v1}/sites/${firstSite.id}" )
        debug aSite
        aSite.entry.with {
           assert (firstSite.title.equals(title))
           assert (firstSite.guid.equals(guid))
        }
         
    }
    
    
    @Test
    @CompileStatic(TypeCheckingMode.SKIP)
    public void testFavouriteSites() {
        
        //Gets favourite sites
        def favs = alfresco.get( path:FAVOURITES_URL, query : [where:'(EXISTS(target/site))'])
        debug favs.list
        
        //Get a site quid so I can add it to the favorites
        def sites = alfresco.get( path:"${API_v1}/sites/" )
        def firstSiteGuid = sites.list.entries[0].entry.guid
        
        //Create POST JSON data
        def jsonFav = new JsonBuilder()
        jsonFav.target {
           site {
               guid firstSiteGuid
           } 
        }
        debug jsonFav.toString()

        //POST a new favourite to the Server
        alfresco.post( path:FAVOURITES_URL, body: jsonFav.toString()) { resp ->
          debug "Response status: ${resp.statusLine}"
          assert resp.statusLine.statusCode == 201
        }
        
        //Check it is now in the list
        favs = alfresco.get( path:"${API_v1}/people/-me-/favorites", query : [where:'(EXISTS(target/site))'])
        def favSite = favs.list.entries.find {
            it.entry.targetGuid == firstSiteGuid
        }.entry
        assert (favSite) //its not null
        debug favSite.target.site.title+" favourited on ${favSite.createdAt}"
         
    }
    
    private void debug(Object debugForOutput) {
       // println debugForOutput
    }
}
