package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 22.12.2017.
 */
data class ProvidersAttributes(

    @Json(name = "provider_attributes")
    var providerAttributes: ProvidersAttribute
)