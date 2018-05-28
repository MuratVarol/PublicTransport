package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 20.12.2017.
 */
data class Price(
    @Json(name = "currency")
    var currency: String?,

    @Json(name = "amount")
    var amount: Double=0.0
)