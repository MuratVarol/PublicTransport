package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 20.12.2017.
 */
data class Segment(

    @Json(name = "name")
    var name: String?,

    @Json(name = "num_stops")
    var num_stops: Int,

    @Json(name = "stops")
    var stops: List<Stop> = listOf(),

    @Json(name = "travel_mode")
    var travel_mode: String?,

    @Json(name = "description")
    var description: String?,

    @Json(name = "color")
    var color: String?,

    @Json(name = "icon_url")
    var icon_url: String?,

    @Json(name = "polyline")
    var polyline: String?

)