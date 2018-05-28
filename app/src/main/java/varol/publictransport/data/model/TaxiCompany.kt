package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 22.12.2017.
 */
data class TaxiCompany(
    @Json(name = "name")
    var name: String?,

    @Json(name = "phone")
    var phone: String?
)