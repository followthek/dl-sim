package tools.qwewqa.sim.status

import tools.qwewqa.sim.extensions.plus
import tools.qwewqa.sim.stage.AdventurerCondition

/**
 * Contains information about a condition and necessary listeners
 *
 * @property name the display name of the condition
 * @property listeners the names of the events that must be listened on for the conditions
 * @property condition the condition
 */
data class Condition(
    val name: String,
    val listeners: Set<String> = emptySet(),
    val condition: AdventurerCondition
    ) {
    constructor(name: String, vararg listeners: String, condition: AdventurerCondition) : this(name, listeners.toSet(), condition)
    operator fun plus(other: Condition) =
        Condition(
            "$name ${other.name}",
            listeners + other.listeners,
            condition + other.condition
        )
}

val noCondition = Condition("", emptySet()) { true }