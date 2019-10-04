package tools.qwewqa.sim.adventurers

import tools.qwewqa.sim.data.*
import tools.qwewqa.sim.stage.acl
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.Element
import tools.qwewqa.sim.stage.skillAtk
import tools.qwewqa.sim.extensions.*
import tools.qwewqa.sim.status.paralysis
import tools.qwewqa.sim.status.paralyzed

val galaEuden = AdventurerSetup {
    name = "Gala Euden"
    element = Element.Light
    str = 502
    // ex unimplemented
    weapon = Weapons.lightSword5t3
    dragon = Dragons.cupid
    wyrmprints = Wyrmprints.tso + Wyrmprints.sdo

    s1(3817) {
        +skillAtk(413.percent, "s1")
        Buffs.str(20.percent).teamBuff(10.0, buffTime = false)
        wait(2.52)
    }

    s2(999999) {
        val s2hit = skillAtk(210.percent, "s2", "hit")
        repeat(10) { +s2hit }
        Buffs.str(15.percent).teamBuff(15.0)
        Buffs.def(15.percent).teamBuff(15.0)
        // location after buffs & mods are based on b1ues rather than wiki
        paralysis(skillAtk(97.percent, "s2", "paralysis").snapshot(), duration = 13.0, chance = 120.percent)
        wait(2.73)
    }

    autocharge("s2", 15873)

    acl {
        +s1 { +"fs" }
        +s2 { +"fs" }
        +fs { +"x2" }
    }

    prerun {
        Buffs.str(9.percent).selfBuff() // same as b1ues
    }
}