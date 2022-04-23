package ru.develgame.javaeejsf.service;

import ru.develgame.javaeecommon.entity.SC2Unit;

import javax.annotation.PostConstruct;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
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
@CacheDefaults(cacheName = "SC2UnitCache")
public class SC2UnitService {
    private Client client;

    private static final String REST_API_ADDRESS = "http://localhost:60845/JavaEERestAPI-1.0-SNAPSHOT/";
    private static final String RESOURCES_ADDRESS = "resources/sc2units";

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @CacheResult
    public List<SC2Unit> getSc2UnitList() {
        List<SC2Unit> sc2Units = client
                .target(REST_API_ADDRESS)
                .path(RESOURCES_ADDRESS)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<SC2Unit>>() {});
        return sc2Units;
    }

    @CacheRemoveAll
    public Response createSc2Unit(SC2Unit sc2Unit) {
        return client
                .target(REST_API_ADDRESS)
                .path(RESOURCES_ADDRESS)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(sc2Unit, MediaType.APPLICATION_JSON));
    }
}
