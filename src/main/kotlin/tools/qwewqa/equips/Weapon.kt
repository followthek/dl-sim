package tools.qwewqa.equips

import tools.qwewqa.core.Action
import tools.qwewqa.core.Move
import tools.qwewqa.core.noMove
import tools.qwewqa.scripting.*

class Weapon(
    val name: String,
    val skill: Move,
    val combo: Move,
    val fs: Move
)

fun noWeapon() = Weapon("none", noMove(), noMove(), noMove())

fun forcestrike(action: Action) = move {
    name = "fs"
    condition { doing in listOf("idle", "x1", "x2", "x3", "x4", "x5") }
    this.action = action
}

fun combo(action: Action) = move {
    name = "combo"
    condition { doing == "idle" }
    this.action = action
}