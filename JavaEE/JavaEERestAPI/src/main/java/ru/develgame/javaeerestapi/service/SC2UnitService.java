package ru.develgame.javaeerestapi.service;

import ru.develgame.javaeerestapi.entity.SC2Unit;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Zemskov
 */
@Named
public class SC2UnitService {
    @Resource(lookup = "jdbc/javaeedb")
    private DataSource dataSource;

    public List<SC2Unit> getSC2Units() {
        List<SC2Unit> sc2UnitList = new ArrayList<>();

        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            try (PreparedStatement statement = connection.prepareStatement("SELECT id, attack, defense, name FROM sc2units")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    sc2UnitList.add(new SC2Unit(resultSet.getLong(1),
                            resultSet.getString(4),
                            resultSet.getDouble(2),
                            resultSet.getDouble(3)));
                }
            }
        } catch (SQLException e) {
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                }
            }
        }

        return sc2UnitList;
    }
}
