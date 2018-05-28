package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 21.12.2017.
 */
data class MainModel
(
    @Json(name = "routes")
    var routes: List<Route> = listOf(),

    @Json(name = "provider_attributes")
    var providerAttributes: ProvidersAttribute


)