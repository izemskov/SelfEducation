package ru.develgame.javaeecommon.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author Ilya Zemskov
 */
@Entity
@Table(name = "sc2units")
class SC2Unit : Comparable<SC2Unit>, Serializable
{
    @Id
    var id: Long? = null
    var name: String? = null
    var attack: Double? = null
    var defense: Double? = null

    constructor()

    constructor(id: Long?, name: String?, attack: Double?, defense: Double?) {
        this.id = id
        this.name = name
        this.attack = attack
        this.defense = defense
    }


    override fun compareTo(other: SC2Unit): Int {
        return java.lang.Double.compare(this.defense!! - other.attack!!,
                        other.defense!! - this.attack!!)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SC2Unit

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}