package ru.develgame.javaeerestapi.service;

import ru.develgame.javaeecommon.entity.SC2Unit;

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
        } finally {
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

    public boolean createSC2Unit(SC2Unit sc2Unit, StringBuilder err) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO sc2units(id, name, attack, defense) VALUES ((SELECT nextval('sc2units_sequence')),?,?,?)"))
            {
                statement.setString(1, sc2Unit.getName());
                statement.setDouble(2, sc2Unit.getAttack());
                statement.setDouble(3, sc2Unit.getDefense());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            err.append(e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                }
            }
        }

        return true;
    }
}
