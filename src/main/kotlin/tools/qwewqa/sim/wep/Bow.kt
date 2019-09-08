package tools.qwewqa.sim.wep

import tools.qwewqa.sim.extensions.frames
import tools.qwewqa.sim.extensions.hit
import tools.qwewqa.sim.extensions.percent

private val fs = forcestrike {
    doing = "fs"
    wait(63.frames)
    hit("fs") {
        hit(0.5, "fs-a") {
            damage(31.percent, fs = true)
            sp(460, fs = true)
        }
        hit(0.5, "fs-b") { damage(31.percent, fs = true) }
        hit(0.5, "fs-c") { damage(31.percent, fs = true) }
        hit(0.5, "fs-d") { damage(31.percent, fs = true) }
        hit(0.5, "fs-e") { damage(31.percent, fs = true) }
        hit(0.5, "fs-f") { damage(31.percent, fs = true) }
        hit(0.5, "fs-g") { damage(31.percent, fs = true) }
        hit(0.5, "fs-h") { damage(31.percent, fs = true) }
    }
    wait(34.frames)
}

private val combo = combo {
    doing = "x1"
    wait(23.frames)
    hit("x1") {
        auto("x1-a1", 29.percent, 184)
        auto("x1-a2", 29.percent)
        auto("x1-a3", 29.percent)
    }

    doing = "x2"
    wait(35.frames)
    hit("x2") {
        auto("x2-a", 37.percent, 92)
        auto("x2-b", 37.percent)
    }

    doing = "x3"
    wait(33.frames)
    hit("x3") {
        auto("x3-a1", 42.percent, 276)
        auto("x3-a2", 42.percent)
        auto("x3-a3", 42.percent)
    }

    doing = "x4"
    wait(51.frames)
    hit("x4") {
        auto("x4-a", 63.percent, 414)
        auto("x4-b", 63.percent)
    }

    doing = "x5"
    wait(66.frames)
    hit("x5") {
        auto("x5-a1", 35.percent, 529)
        auto("x5-a2", 35.percent)
        auto("x5-a3", 35.percent)
        auto("x5-a4", 35.percent)
        auto("x5-a5", 35.percent)
    }
    wait(24.frames)
}

private val fsf = fsf(32.frames)

val bow = WeaponType(
    name = "bow",
    combo = combo,
    fs = fs,
    fsf = fsf
)