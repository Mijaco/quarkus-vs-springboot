package com.redhat.example.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.example.entity.Calculator;
import com.redhat.example.service.CalculateService;
import com.redhat.example.service.DatabaseService;
import org.hibernate.dialect.Database;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     com.redhat.example.controller.ApiController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Nov 2022 15:13
 */
@Path("/api")
public class ApiController {
    private static final Logger LOG = Logger.getLogger(ApiController.class);

    @Inject
    CalculateService calculateService;

    @Inject
    DatabaseService databaseService;

    @GET
    @Path("/calculate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(@QueryParam("value1") Long value1, @QueryParam("method") String method, @QueryParam("value2") Long value2) {
        LOG.debug(String.format("Calculating %s %s %s ", value1, method, value2));
        return Response.ok(new HashMap(){{
                    put("result", calculateService.calculate(value1, method, value2));
                }})
                .build();
    }

    @GET
    @Path("/results")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Calculator> results() {
        LOG.debug(String.format("getting result"));
        return databaseService.getResult();
    }

    @GET
    @Path("/prime")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getNthPrime(@QueryParam("n") int n) {
        long start = System.currentTimeMillis();
        long prime = calculateService.findNthPrime(n);
        long duration = System.currentTimeMillis() - start;

        Map<String, Object> response = new HashMap<>();
        response.put("nthPrime", prime);
        response.put("position", n);
        response.put("durationInMillis", duration);
        return response;
    }
}
