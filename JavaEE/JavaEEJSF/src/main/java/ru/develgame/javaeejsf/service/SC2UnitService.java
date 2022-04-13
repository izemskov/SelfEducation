package ru.develgame.javaeejsf.service;

import ru.develgame.javaeecommon.entity.SC2Unit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Ilya Zemskov
 */
@Named
@ApplicationScoped
public class SC2UnitService {
    private Client client;

    private static final String REST_API_ADDRESS = "http://localhost:60845/JavaEERestAPI-1.0-SNAPSHOT/";
    private static final String RESOURCES_ADDRESS = "resources/sc2units";

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    public List<SC2Unit> getSc2UnitList() {
        List<SC2Unit> sc2Units = client
                .target(REST_API_ADDRESS)
                .path(RESOURCES_ADDRESS)
                .request(MediaType.TEXT_XML)
                .get(new GenericType<List<SC2Unit>>() {});
        return sc2Units;
    }

    public Response createSc2Unit(SC2Unit sc2Unit) {
        return client
                .target(REST_API_ADDRESS)
                .path(RESOURCES_ADDRESS)
                .request(MediaType.TEXT_XML)
                .post(Entity.entity(sc2Unit, MediaType.TEXT_XML));
    }
}
