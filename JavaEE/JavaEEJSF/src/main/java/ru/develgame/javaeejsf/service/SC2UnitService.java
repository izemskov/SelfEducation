package ru.develgame.javaeejsf.service;

import ru.develgame.javaeejsf.entity.SC2Unit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Ilya Zemskov
 */
@Named
@ApplicationScoped
public class SC2UnitService {
    private Client client;
    private WebTarget webTarget;
    private WebTarget sc2UnitsWebTarget;
    private Invocation.Builder invocationBuilder;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        webTarget = client.target("http://localhost:60845/JavaEERestAPI-1.0-SNAPSHOT/");
        sc2UnitsWebTarget = webTarget.path("resources/sc2units");
        invocationBuilder = sc2UnitsWebTarget.request(MediaType.TEXT_XML);
    }

    public List<SC2Unit> getSc2UnitList() {
        List<SC2Unit> sc2Units = invocationBuilder.get(new GenericType<List<SC2Unit>>() {});
        return sc2Units;
    }
}
