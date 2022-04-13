package ru.develgame.javaeerestapi.controller;

import ru.develgame.javaeecommon.entity.SC2Unit;
import ru.develgame.javaeerestapi.service.SC2UnitService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Ilya Zemskov
 */
@Path("sc2units")
public class SC2UnitController {
    @Inject
    private SC2UnitService sc2UnitService;

    @GET
    @Produces("text/xml")
    public List<SC2Unit> getSC2Units() {
        return sc2UnitService.getSC2Units();
    }

    @POST
    @Consumes("text/xml")
    public Response createSC2Unit(SC2Unit sc2Unit) {
        StringBuilder err = new StringBuilder();
        boolean created = sc2UnitService.createSC2Unit(sc2Unit, err);
        if (created)
            return Response.status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(err.toString())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
    }
}
