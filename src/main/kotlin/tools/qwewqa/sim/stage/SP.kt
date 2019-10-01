package tools.qwewqa.sim.stage

import kotlin.math.ceil

class SP(val adventurer: Adventurer) {
    private val charges = mutableMapOf<String, Int>()
    private val costs = mutableMapOf<String, Int>()

    /**
     * Increases the sp accounting for haste on all skills
     */
    operator fun invoke(amount: Int, source: String = adventurer.doing) {
        adventurer.log(Logger.Level.MORE, "sp", "charged $amount sp by $source")
        charge(amount, source)
        logCharges()
    }

    operator fun get(name: String) = charges[name] ?: throw IllegalArgumentException("Unknown skill [$name]")

    fun remaining(name: String) = -this[name] + costs[name]!!

    fun ready(name: String) =
        (charges[name] ?: throw IllegalArgumentException("Unknown skill [$name]")) >= costs[name]!!

    fun logCharges() =
        adventurer.log(Logger.Level.VERBOSE, "sp", charges.keys.map { "$it: ${charges[it]}/${costs[it]}" }.toString())

    fun charge(amount: Int, source: String = adventurer.doing) {
        charges.keys.forEach {
            charge(amount, it, source)
        }
    }

    fun charge(fraction: Double, source: String = adventurer.doing) {
        charges.keys.forEach {
            charge(fraction, it, source)
        }
    }

    fun charge(fraction: Double, name: String, source: String = adventurer.doing) {
        charge(ceil(fraction * costs[name]!!).toInt(), name, source)
    }

    fun charge(amount: Int, name: String, source: String = adventurer.doing) {
        require(charges[name] != null) { "Unknown skill [$name]" }
        if (charges[name] == costs[name]) return
        charges[name] = charges[name]!! + amount
        if (charges[name]!! >= costs[name]!!) {
            charges[name] = costs[name]!!
            adventurer.listeners.raise("$name-charged")
        }
        adventurer.log(
            Logger.Level.VERBOSIEST,
            "sp",
            "$name charged $amount sp by $source (${charges[name]}/${costs[name]})"
        )
    }

    fun use(name: String) {
        charges[name] = 0
    }

    fun setCost(name: String, max: Int) {
        charges[name] = 0
        costs[name] = max
    }
}