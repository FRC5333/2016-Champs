package frc.team5333.stronghold.core.control

import frc.team5333.stronghold.core.configs.ConfigMap

class InternalPID(var Kp: Double, var Ki: Double, var Kd: Double) {

    var target = 0.0

    private var last_time = 0L
    var last_error = 0.0

    private var firstrun = true

    private var running_i = 0.0
    private var last_update = 0.0

    private var last_updates: DoubleArray = DoubleArray(ConfigMap.Control.pid_target_samples)
    private var last_update_idx: Int = 0
    private var valid_for_target = false

    fun update(feedback: Double): Double {
        var error = target - feedback
        var time = System.currentTimeMillis()

        if (firstrun) {
            last_time = time
            firstrun = false
        }

        var dt = time - last_time
        var de = error - last_error

        var p = Kp * error
        running_i += error * dt
        var d = 0.0
        if (dt > 0.0) d = de / dt

        last_time = time
        last_error = error

        last_updates[last_update_idx] = last_error
        last_update_idx++
        if (last_update_idx == last_updates.size) {
            last_update_idx = 0
            valid_for_target = true
        }


        last_update = p + running_i * Ki + d * Kd
        return last_update
    }

    fun onTarget(threshold: Double): Boolean = valid_for_target && Math.abs(last_updates.average()) < threshold
}