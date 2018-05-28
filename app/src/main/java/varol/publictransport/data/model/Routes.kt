package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 22.12.2017.
 */
data class Routes(
    @Json(name = "routes")
    var routes: List<Route> = listOf()
)