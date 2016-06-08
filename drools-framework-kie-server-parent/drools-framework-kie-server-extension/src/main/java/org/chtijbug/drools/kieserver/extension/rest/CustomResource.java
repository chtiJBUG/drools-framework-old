package org.chtijbug.drools.kieserver.extension.rest;

import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.impl.RuleBaseCommandSession;
import org.chtijbug.drools.runtime.impl.RuleBaseCommandSingleton;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.drools.RulesExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static org.kie.server.remote.rest.common.util.RestUtils.*;

@Path("server/containers/instances/{id}/ksession")
public class CustomResource {

    private static final Logger logger = LoggerFactory.getLogger(CustomResource.class);

    private KieCommands commandsFactory = KieServices.Factory.get().getCommands();

    private RulesExecutionService rulesExecutionService;
    private KieServerRegistry registry;

    public CustomResource() {

    }

    public CustomResource(RulesExecutionService rulesExecutionService, KieServerRegistry registry) {
        this.rulesExecutionService = rulesExecutionService;
        this.registry = registry;
    }

    @POST
    @Path("/{ksessionId}/{processId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response insertFireReturn(@Context HttpHeaders headers,
                                     @PathParam("id") String id,
                                     @PathParam("ksessionId") String ksessionId,
                                     @PathParam("ksessionId") String processId,
                                     String cmdPayload) {

        Variant v = getVariant(headers);
        String contentType = getContentType(headers);

        MarshallingFormat format = MarshallingFormat.fromType(contentType);
        if (format == null) {
            format = MarshallingFormat.valueOf(contentType);
        }
        try {
            KieContainerInstance kci = registry.getContainer(id);

            Marshaller marshaller = kci.getMarshaller(format);

            List<?> listOfFacts = marshaller.unmarshall(cmdPayload, List.class);

            RuleBasePackage ruleBasePackage = new RuleBaseCommandSingleton(2000);

            RuleBaseCommandSession session = (RuleBaseCommandSession) ruleBasePackage.createRuleBaseSession();

            session.fireAllRulesAndStartProcess(listOfFacts.get(0), processId);
            List<Command<?>> commands = session.getCommands();
            BatchExecutionCommand executionCommand = commandsFactory.newBatchExecution(commands, ksessionId);


            ExecutionResults results = rulesExecutionService.call(kci, executionCommand);

            String result = marshaller.marshall(results);


            logger.debug("Returning OK response with content '{}'", result);
            return createResponse(result, v, Response.Status.OK);
        } catch (Exception e) {
            // in case marshalling failed return the call container response to keep backward compatibility
            String response = "Execution failed with error : " + e.getMessage();
            logger.debug("Returning Failure response with content '{}'", response);
            return createResponse(response, v, Response.Status.INTERNAL_SERVER_ERROR);
        }

    }
}
