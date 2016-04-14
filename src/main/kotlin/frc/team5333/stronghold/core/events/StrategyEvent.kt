package frc.team5333.stronghold.core.events

import frc.team5333.stronghold.core.events.bus.EventBase
import frc.team5333.stronghold.core.strategy.Strategy

/**
 * A StrategyEvent is called upon a change to the current Strategy
 *
 * @author Jaci
 */
open class StrategyEvent : EventBase() {

    /**
     * The StrategyChangeEvent is called upon a change to the currently active Strategy. This is used to
     * detect whether the driver does or doesn't have control, and for when different actions are being executed.
     *
     * @author Jaci
     */
    class StrategyChangeEvent(var newStrategy: Strategy, var oldStrategy: Strategy) : StrategyEvent() {
        override fun getCustomData(): String = "${newStrategy.getName()}"
    }

}