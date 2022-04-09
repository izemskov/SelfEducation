package ru.develgame.javaeerestapi.controller;

import ru.develgame.javaeerestapi.entity.SC2Unit;
import ru.develgame.javaeerestapi.service.SC2UnitService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
