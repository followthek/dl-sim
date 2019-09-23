package tools.qwewqa.sim.adventurers

import tools.qwewqa.sim.data.Coabilities
import tools.qwewqa.sim.data.Dragons
import tools.qwewqa.sim.data.Weapons
import tools.qwewqa.sim.data.Wyrmprints
import tools.qwewqa.sim.extensions.acl
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.Element
import tools.qwewqa.sim.stage.skillAtk
import tools.qwewqa.sim.extensions.*

val aoi = AdventurerSetup {
    name = "Aoi"
    element = Element.FLAME
    str = 494
    ex = Coabilities["Str"]
    weapon = Weapons["Heaven's Acuity"]
    dragon = Dragons["Sakuya"]
    wp = Wyrmprints["RR", "CE"]

    s1(2630) {
        +skillAtk(878.percent, "s1")
        wait(1.85)
    }

    s2(5280) {
        +skillAtk(790.percent, "s2")
        wait(1.85)
    }

    acl {
        +s1 { +"x5" }
        +s2 { +"x5" }
        +s3 { +"x5" }
        +fsf { +"x5" }
    }
}