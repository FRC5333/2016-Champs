package frc.team5333.webui.controller

import frc.team5333.webui.WebHandler
import spark.Request
import spark.Response
import java.util.*
import kotlin.collections.hashMapOf

class DashboardController : Controller {

    override fun address(): String = "/dashboard"

    override fun init() { }

    override fun handle(request: Request?, response: Response?): Any? {
        var map: HashMap<String, Any> = hashMapOf( )
        return WebHandler.applyTemplate("dashboard.html", map)
    }
}