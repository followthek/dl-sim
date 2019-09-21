package tools.qwewqa.sim.buffs

import tools.qwewqa.sim.core.Timeline
import tools.qwewqa.sim.core.Timer
import tools.qwewqa.sim.core.getTimer
import tools.qwewqa.sim.stage.Adventurer
import kotlin.reflect.KClass

/**
 * Data on an buff without any behavior
 */
class BuffInstance<T, U>(
    val name: String,
    val value: T,
    val behavior: BuffBehavior<T, U>
) {
    fun apply(adventurer: Adventurer, duration: Double? = null) : Timer? {
        val stack = behavior.getStack(adventurer)
        if (stack.count >= behavior.stackCap) return null
        behavior.onStart(adventurer, duration, value, stack)
        stack.count++
        if (duration == null) return null
        val timer = adventurer.timeline.getTimer {
            stack.count--
            behavior.onEnd(adventurer, duration, value, stack)
        }
        timer.set(duration)
        return timer
    }
}

/**
 * Contains the behavior of a buff
 *
 * @property name the name of this buff for display
 * @property initialValue the initial value for a stack
 * @property onStart ran when it is applied at any point
 * @property onEnd ran when an individual instance ends
 * @property stackStart ran when the number of stacks changes from 0 to 1. canceled when stack ends
 * @property stackEnd ran when the entire stack end
 * @property stackCap maximum number of stacks after which further stacks will bounce
 */
class BuffBehavior<T, U>(
    val name: String,
    val initialValue: Adventurer.() -> U,
    val onStart: Adventurer.(duration: Double?, value: T, stack: BuffBehavior<T, U>.Stack) -> Unit = { _, _, _ -> },
    val onEnd: Adventurer.(duration: Double?, value: T, stack: BuffBehavior<T, U>.Stack) -> Unit = { _, _, _ -> },
    val stackStart: Adventurer.(BuffBehavior<T, U>.Stack) -> Unit = {},
    val stackEnd: Adventurer.(BuffBehavior<T, U>.Stack) -> Unit = {},
    val stackCap: Int = 20
) {
    /**
     * An ability "stack", similar to buff stacks. Necessitated for implementation of wyrmprint caps
     */
    inner class Stack(val adventurer: Adventurer) {
        var startEvent: Timeline.Event? = null

        var count: Int = 0
            set(value) {
                if (field == 0 && value == 1) {
                    startEvent = adventurer.timeline.schedule {
                        adventurer.stackStart(this@Stack)
                    }
                }
                if (value == 0) {
                    startEvent?.cancel()
                    adventurer.stackEnd(this)
                }
                field = value
            }

        var value: U = adventurer.initialValue()
    }

    /**
     * Get the stack of this for the given [adventurer], creating a new one first if needed
     */
    fun getStack(adventurer: Adventurer) =
        adventurer.buffStacks[this] as BuffBehavior<T, U>.Stack? ?: Stack(adventurer).also { adventurer.buffStacks[this] = it }

    /**
     * Creates a [BuffInstance] targeting this
     */
    operator fun invoke(value: T) = getInstance(value)

    /**
     * Creates a [BuffInstance] targeting this
     */
    fun getInstance(value: T) = BuffInstance(name, value, this)
}