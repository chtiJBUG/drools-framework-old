package org.chtijbug.drools.kieserver.loyalty.rest;

import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.example.swimmingpool.Quote;
import org.chtijbug.kieserver.services.drools.DroolsFrameworkRulesExecutionService;
import org.kie.api.KieServices;
import org.kie.api.command.KieCommands;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("server/containers/instances/swimmingpool/")
public class swimmingpoolResource {

    private static final Logger logger = LoggerFactory.getLogger(swimmingpoolResource.class);

    private KieCommands commandsFactory = KieServices.Factory.get().getCommands();

    private DroolsFrameworkRulesExecutionService rulesExecutionService;
    private KieServerRegistry registry;
    private RuleBasePackage ruleBasePackage = null;

    public swimmingpoolResource() {

    }

    public swimmingpoolResource(DroolsFrameworkRulesExecutionService rulesExecutionService, KieServerRegistry registry) {
        this.rulesExecutionService = rulesExecutionService;
        this.registry = registry;
    }


    @GET
    @Path("/test")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String toto() {
        return "toto ";
    }

    @POST
    @Path("/run/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Quote runSession(@PathParam("id") String id,
                            Quote quoteRequest) {
        try {
            KieContainerInstance kci = registry.getContainer(id);
            quoteRequest = (Quote) rulesExecutionService.FireAllRulesAndStartProcess(kci, quoteRequest, "swimmingpool.P000");
            logger.debug("Returning OK response with content '{}'", quoteRequest);
            return quoteRequest;
        } catch (Exception e) {
            // in case marshalling failed return the FireAllRulesAndStartProcess container response to keep backward compatibility
            String response = "Execution failed with error : " + e.getMessage();
            logger.debug("Returning Failure response with content '{}'", response);
            return quoteRequest;
        }

    }
}
