package tools.qwewqa.sim.wep

import tools.qwewqa.sim.extensions.frames
import tools.qwewqa.sim.extensions.noMove
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.doAuto
import tools.qwewqa.sim.stage.doFs

val sword = WeaponType(
    name = "sword",
    x = weaponCombo {
        doing = "x1"
        wait(9.frames)
        doAuto(75.percent, 150, "x1")
        doing = "x2"
        wait(26.frames)
        doAuto(80.percent, 150, "x2")

        doing = "x3"
        wait(23.frames)
        doAuto(95.percent, 196, "x3")

        doing = "x4"
        wait(36.frames)
        doAuto(100.percent, 265, "x4")

        doing = "x5"
        wait(37.frames)
        doAuto(150.percent, 391, "x5")
        wait(42.frames)
    },
    fs =  forcestrike {
        doing = "fs"
        when(trigger) {
            "x1" -> wait(39.frames)
            else -> wait(19.frames)
        }
        doFs(115.percent, 8.0, 345, "fs")
        wait(21.frames)
    },
    fsf = noMove
)