package ru.develgame.javaeerestapi.service

import ru.develgame.javaeecommon.entity.SC2Unit
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.annotation.Resource
import javax.inject.Named
import javax.sql.DataSource

/**
 * @author Ilya Zemskov
 */
@Named
class SC2UnitService {
    @Resource(lookup = "jdbc/javaeedb")
    private lateinit var dataSource: DataSource

    fun getSC2Units(): List<SC2Unit> {
        var sc2UnitList = mutableListOf<SC2Unit>()
        var connection: Connection? = null

        try {
            connection = dataSource.connection
            var prepareStatement: PreparedStatement? = null
            try {
                prepareStatement = connection?.prepareStatement("SELECT id, attack, defense, name FROM sc2units")
                var resultSet: ResultSet? = null
                try {
                    resultSet = prepareStatement?.executeQuery()
                    while (resultSet?.next() == true) {
                        sc2UnitList.add(SC2Unit(resultSet.getLong(1),
                                resultSet.getString(4),
                                resultSet.getDouble(2),
                                resultSet.getDouble(3)))
                    }
                }
                finally {
                    resultSet?.close()
                }
            }
            finally {
                prepareStatement?.close()
            }

        } catch (e: SQLException) {
            // TODO - log exception
        } finally {
            connection?.close()
        }

        return sc2UnitList
    }

    fun createSC2Unit(sc2Unit: SC2Unit, err: StringBuilder): Boolean {
        var connection: Connection? = null

        try {
            connection = dataSource.connection
            var statement: PreparedStatement? = null
            try {
                statement = connection?.prepareStatement("INSERT INTO sc2units(id, name, attack, defense) VALUES ((SELECT nextval('sc2units_sequence')),?,?,?)")
                statement?.setString(1, sc2Unit.name)
                statement?.setDouble(2, sc2Unit?.attack ?: 0.0)
                statement?.setDouble(3, sc2Unit?.defense ?: 0.0)
                statement?.executeUpdate();
            }
            finally {
                statement?.close()
            }

        } catch (e: SQLException) {
            err.append(e.message)
            return false
        } finally {
            connection?.close()
        }

        return true
    }
}