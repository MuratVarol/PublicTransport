package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 31.12.2017.
 */
data class TravelMode(
    @Json(name = "travel_mode")
    var travel_mode: String?,

    @Json(name = "color")
    var color: String?,

    @Json(name = "icon_url")
    var icon_url: String?
)