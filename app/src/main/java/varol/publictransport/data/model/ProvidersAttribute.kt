package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 21.12.2017.
 */

data class ProvidersAttribute(

    @Json(name = "vbb")
    var providerVBB: Provider?,

    @Json(name = "drivenow")
    var providerDriveNow: Provider?,

    @Json(name = "car2go")
    var providerCar2Go: Provider?,

    @Json(name = "google")
    var providerGoogle: Provider?,

    @Json(name = "nextbike")
    var providerNextBike: Provider?,

    @Json(name = "callabike")
    var providerCallABike: Provider?



)