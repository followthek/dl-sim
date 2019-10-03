package tools.qwewqa.sim.adventurers

import tools.qwewqa.sim.data.*
import tools.qwewqa.sim.stage.acl
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.Element
import tools.qwewqa.sim.stage.skillAtk
import tools.qwewqa.sim.extensions.*

val serena = AdventurerSetup {
    name = "Serena"
    element = Element.Flame
    str = 443
    ex = Coabilities["Sword"]
    weapon = Weapons["Levatein"]
    dragon = Dragons["Arctos"]
    wp = Wyrmprints["RR"] + Wyrmprints["CE"]

    a1 = Abilities["barrage obliteration"](6.percent)
    a3 = Abilities["barrage devastation"](3.percent)

    s1(2500) {
        Buffs["crit rate"](10.percent).selfBuff(5.0)
        val s1hit = skillAtk(350.percent, "s1")
        +s1hit
        +s1hit
        wait(1.55)
    }

    s2(4593) {
        val s2hit = skillAtk(169.percent, "s2")
        +s2hit
        +s2hit
        +s2hit
        +s2hit
        wait(2.2)
    }

    acl {
        +s1
        +s2 { +"fs" }
        +s3 { +"fs" }
        +fs { +"x3" }
    }
}