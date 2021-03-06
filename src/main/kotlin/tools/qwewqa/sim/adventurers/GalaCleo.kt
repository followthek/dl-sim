package tools.qwewqa.sim.adventurers

import tools.qwewqa.sim.data.*
import tools.qwewqa.sim.acl.acl
import tools.qwewqa.sim.acl.rotation
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.Element
import tools.qwewqa.sim.extensions.*
import tools.qwewqa.sim.stage.Stat
import tools.qwewqa.sim.stage.doSkill
import tools.qwewqa.sim.wep.forcestrike
import tools.qwewqa.sim.wep.wand

val galaCleo = AdventurerSetup {
    name = "Gala Cleo"
    element = Element.Shadow
    str = 489
    ex = Coabilities.skillDamage(15.percent)
    weapon = Weapons.shadowWand5t3
    dragon = Dragons.shinobi
    wyrmprints = Wyrmprints.rr + Wyrmprints.jots

    a1 = Abilities.magicalModification(25.percent)
    a3 = Abilities.skillPrep(100.percent)

    fs = forcestrike {
        if (altFs > 0) {
            when (trigger) {
                "x5" -> wait(57.frames)
                else -> wait(43.frames)
            }
            stage.adventurers.forEach { adv ->
                Buffs.str(Abilities.magicalModification.value).apply(adv, 10.0)
            }
            altFs--
            think("fs")
            wait(67.frames)
        } else {
            wand.fs.action(this, it)
        }
    }

    s1(2814) {
        val hits = s1Phase + 2
        schedule {
            wait(30.frames)
            repeat(hits) {
                wait(12.frames)
                doSkill(88.percent, "s1", "ray")
                doSkill(265.percent, "s1", "explosion")
            }
        }
        s1Phase++
        wait(1.45)
    }

    s2(6000) {
        damage(460.percent, "s2", skill = true)
        Debuffs.def(10.percent).apply(20.0)
        wait(1.45)
    }

    prerun {
        val haste = stats[Stat.SKILL_HASTE].value
        if (logic != null) return@prerun
        if (haste >= 8.percent) {
            rotation {
                init = "s2 s1 c5 c4fs s1"
                loop = "c5 c4fs s1 c5 s2 c5fs s1"
            }
        } else {
            acl {
                +fs { altFs > 0 && (sp.ready("s1") || sp.ready("s2")) }
                +s2
                +s1
            }
        }
    }
}