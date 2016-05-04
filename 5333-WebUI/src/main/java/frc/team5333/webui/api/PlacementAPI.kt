package frc.team5333.webui.api

import frc.team5333.stronghold.core.data.PlacementInfo
import spark.Request
import spark.Response
import kotlin.text.toInt

class PlacementAPI : API {

    override fun address(): String? = "placement/set/:id"

    override fun init() { }

    override fun handle(request: Request, response: Response?): Any? {
        var num = request.params(":id").toInt()

        PlacementInfo.update(num)
        return "Done"
    }
}