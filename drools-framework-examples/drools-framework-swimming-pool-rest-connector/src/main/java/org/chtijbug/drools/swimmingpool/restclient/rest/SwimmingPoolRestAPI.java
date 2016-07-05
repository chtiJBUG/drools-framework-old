package org.chtijbug.drools.swimmingpool.restclient.rest;


import org.chtijbug.example.swimmingpool.Quote;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface SwimmingPoolRestAPI {

    @GET
    @Path("/kie-server/services/rest/server/containers/instances/loyalty/run/{id}")
    @Produces("application/json")
    @Consumes(value = MediaType.APPLICATION_JSON)
    Quote runSession(@PathParam("id") String id,
                     Quote quoteRequest);

}
