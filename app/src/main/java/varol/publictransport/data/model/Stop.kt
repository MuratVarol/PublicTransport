package varol.publictransport.data.model

import com.squareup.moshi.Json
import java.util.Date

/**
 * Created by Murat on 20.12.2017.
 */
data class Stop(
    @Json(name = "lat")
    var lat: Double?,

    @Json(name = "lng")
    var lng: Double?,

    @Json(name = "datetime")
    var datetime: Date?,

    @Json(name = "name")
    var name: String?,

    @Json(name = "properties")
    var properties: Any?

)