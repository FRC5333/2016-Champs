package frc.team5333.stronghold.core.strategy

class StrategyOperator : Strategy() {

    override fun getName(): String = "Operator"

    override fun isOperatorControl(): Boolean = true

    override fun tick() {
    }

    override fun onEnable() {
        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }
}