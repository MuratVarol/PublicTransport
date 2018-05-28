package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 20.12.2017.
 */


data class Route(

    @Json(name = "type")
    var type: String?,

    @Json(name = "provider")
    var provider: String?,

    @Json(name = "segments")
    var segments: List<Segment>? = listOf(),

    @Json(name = "properties")
    var properties: Property?,

    @Json(name = "price")
    var price: Price?

)
