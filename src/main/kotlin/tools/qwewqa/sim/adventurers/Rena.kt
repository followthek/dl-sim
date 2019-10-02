package tools.qwewqa.sim.adventurers

import tools.qwewqa.sim.data.*
import tools.qwewqa.sim.extensions.acl
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.Element
import tools.qwewqa.sim.stage.skillAtk
import tools.qwewqa.sim.extensions.*
import tools.qwewqa.sim.status.burn

val rena = AdventurerSetup {
    name = "Rena"
    element = Element.Flame
    str = 471
    ex = Coabilities["Str"]
    weapon = Weapons.flameBlade5t3
    dragon = Dragons.sakuya
    wp = Wyrmprints.rr + Wyrmprints.ee

    s1(3303) {
        burn(skillAtk(97.percent, "s1", "burn").snapshot(), duration = 12.0, chance = 120.percent)
        yield() // no delay (lack framedata) but need punisher to proc
        val killer = if (s1phase == 3 && Debuffs.burn.on) 1.8 else 1.0
        val smallHit = skillAtk(72.percent * killer, "s1", "small")
        val bigHit = skillAtk(665.percent * killer, "s1", "big")
        +smallHit
        +smallHit
        +smallHit
        +smallHit
        +bigHit
        if (s1phase >= 2) Buffs.critRate(10.percent).selfBuff(15.0)
        s1phase++
        wait(2.45)
    }

    s2(6582) {
        wait(0.15)
        Buffs.critDamage(50.percent).selfBuff(20.0)
        sp.charge(100.percent, name = "s1", source = "s2")
        wait(0.9)
    }

    acl {
        +s1 { +"idle" || +"ui" || cancel }
        +s2 { +"s1" }
        +s3 { +"fs" }
        +fs { +"x5" }
    }
}