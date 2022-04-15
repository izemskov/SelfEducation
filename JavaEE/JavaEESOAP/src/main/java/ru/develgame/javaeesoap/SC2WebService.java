package ru.develgame.javaeesoap;

import ru.develgame.javaeecommon.entity.SC2Unit;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Ilya Zemskov
 */
@WebService
public class SC2WebService {
    @WebMethod
    public int fight(SC2Unit unit1, SC2Unit unit2) {
        return unit1.compareTo(unit2);
    }

    @WebMethod
    public int sum(int a, int b) {
        return a + b;
    }
}
