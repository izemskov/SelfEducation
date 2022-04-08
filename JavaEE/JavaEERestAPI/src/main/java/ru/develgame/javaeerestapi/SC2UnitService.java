package ru.develgame.javaeerestapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Ilya Zemskov
 */
@Path("sc2units")
public class SC2UnitService {
    @GET
    @Produces("text/xml")
    public String getSC2Units() {
        return "<unit>\n"
                + "<id>1</id>\n"
                + "<name>Zerling</name>\n"
                + "</unit>";
    }
}
