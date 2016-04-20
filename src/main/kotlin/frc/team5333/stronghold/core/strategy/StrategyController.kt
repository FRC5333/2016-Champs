package frc.team5333.stronghold.core.strategy

import frc.team5333.stronghold.core.events.StrategyEvent
import frc.team5333.stronghold.core.events.bus.EventBus

enum class StrategyController {
    INSTANCE;

    private var activeStrategy: Strategy = StrategyBlank()

    val lock = Object()

    /**
     * Tick the active strategy periodically
     */
    fun tick(fast: Boolean) {
        synchronized(lock) {
            if (fast)   activeStrategy.tickFast()
            else        activeStrategy.tick()

            if (activeStrategy.isComplete()) {
                var next_strat = activeStrategy.nextStrategy()
                if (next_strat != null) {
                    setStrategy_Now(next_strat)
                } else {
                    setStrategy_Now(StrategyOperator())
                }
            }
        }
    }

    fun tickFast() {
        tick(true)
    }

    fun tickSlow() {
        tick(false)
    }

    fun setStrategy(strat: Strategy) {
        synchronized(lock) {
            setStrategy_Now(strat)
        }
    }

    fun getStrategy(): Strategy = activeStrategy

    private fun setStrategy_Now(value: Strategy) {
        activeStrategy.onDisable()
        EventBus.INSTANCE.raiseEvent(StrategyEvent.StrategyChangeEvent(value, activeStrategy))
        activeStrategy = value
        value.onEnable()
    }

}