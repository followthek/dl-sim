package tools.qwewqa.sim.data

import tools.qwewqa.sim.status.Condition
import tools.qwewqa.sim.extensions.percent
import tools.qwewqa.sim.stage.Element
import tools.qwewqa.sim.stage.EnemyClass
import tools.qwewqa.sim.stage.Phase

object Conditions : DataMap<Condition>() {
    operator fun get(vararg names: String) = names.map { this[it] }.reduce { a, v -> a + v }

    fun hp(amount: Double) = Condition("hp $amount", setOf("hp")) { hp >= amount }
    val hp70 = hp(70.percent)
    val hp100 = hp(100.percent)

    val flame = Condition("flame") { element == Element.Flame }
    val water = Condition("water") { element == Element.Water }
    val wind = Condition("wind") { element == Element.Wind }
    val light = Condition("light") { element == Element.Light }
    val shadow = Condition("shadow") { element == Element.Shadow }

    fun element(element: Element) = when(element) {
        Element.Flame -> flame
        Element.Water -> water
        Element.Wind -> wind
        Element.Light -> light
        Element.Shadow -> shadow
        else -> error("invalid element")
    }

    val axe = Condition("axe") { weaponType == tools.qwewqa.sim.wep.axe }
    val blade = Condition("blade") { weaponType == tools.qwewqa.sim.wep.blade }
    val bow = Condition("bow") { weaponType == tools.qwewqa.sim.wep.bow }
    val dagger = Condition("dagger") { weaponType == tools.qwewqa.sim.wep.dagger }
    val lance = Condition("lance") { weaponType == tools.qwewqa.sim.wep.lance }
    val staff = Condition("staff") { weaponType == tools.qwewqa.sim.wep.staff }
    val sword = Condition("sword") { weaponType == tools.qwewqa.sim.wep.sword }
    val wand = Condition("wand") { weaponType == tools.qwewqa.sim.wep.wand }

    fun combo(count: Int) = Condition("combo $count", "combo") { combo >= count }
    val combo15 = combo(15)
    fun everyCombo(count: Int) = Condition("combo $count", "combo") { combo >= count }
    val every15 = everyCombo(15)
    val every20 = everyCombo(20)
    val every25 = everyCombo(25)
    val every30 = everyCombo(30)

    val overdrive = Condition("overdrive") { enemy.phase == Phase.Overdrive }
    val broken = Condition("overdrive") { enemy.phase == Phase.Break }

    val bleeding = Condition("bleeding", "debuff", "debuff-end") { Debuffs.bleed.on }
    val burning = Condition("burning", "burn-start", "burn-end") { Debuffs.burn.on }
    val poisoned = Condition("poisoned", "poison-start", "poison-end") { Debuffs.poison.on }
    val paralyzed = Condition("paralyzed", "paralysis-start", "paralysis-end") { Debuffs.paralysis.on }
    val bogged = Condition("bogged", "bog-start", "bog-end") { Debuffs.bog.on }
    val blinded = Condition("blinded", "blind-start", "blind-end") { Debuffs.blind.on }
    val sleeping = Condition("sleeping", "sleep-start", "sleep-end") { Debuffs.sleep.on }
    val stunned = Condition("stunned", "stun-start", "stun-end") { Debuffs.stun.on }
    val frozen = Condition("frozen", "freeze-start", "freeze-end") { Debuffs.freeze.on }

    fun enemy(name: String) = Condition("enemy $name") { enemy.name == name || enemy.alias == name }

    val thaumian = Condition("thaumian") { enemy.enemyClass == EnemyClass.Thaumian }

    init {
        this["hp70"] = hp70
        this["hp100", "full hp"] = hp100
        this["flame", "fire"] = flame
        this["water"] = water
        this["wind"] = wind
        this["light"] = light
        this["shadow"] = shadow
        this["axe"] = axe
        this["blade"] = blade
        this["bow"] = bow
        this["dagger"] = dagger
        this["lance"] = lance
        this["staff"] = staff
        this["sword"] = sword
        this["wand"] = wand
        this["combo15", "hit15"] = combo15
        this["every15"] = every15
        this["every20"] = every20
        this["every25"] = every25
        this["every30"] = every30
        this["overdrive", "od"] = overdrive
        this["broken", "break", "bk"] = broken
        this["bleeding", "bleed"] = bleeding
        this["burning"] = burning
        this["poisoned"] = poisoned
        this["paralyzed"] = paralyzed
        this["bogged"] = bogged
        this["blinded"] = blinded
        this["sleeping"] = sleeping
        this["stunned"] = stunned
        this["frozen"] = frozen
        this["thaumian"] = thaumian
    }
}