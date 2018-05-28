package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 22.12.2017.
 */
data class Property(

    //region common

    @Json(name = "id")
    var id: String?,

    //endregion

    //region car_sharing


    @Json(name = "model")
    var model: String?,

    @Json(name = "license_plate")
    var licensePlate: String?,

    @Json(name = "fuel_level")
    var fuelLevel: Int?,

    @Json(name = "engine_type")
    var engineType: String?,

    @Json(name = "internal_cleanliness")
    var internalCleanliness: String?,

    @Json(name = "description")
    var description: String?,

    @Json(name = "seats")
    var seats: Int?,

    @Json(name = "doors")
    var doors: Int?,

//endregion



//region bike_sharing

    @Json(name = "available_bikes")
    var availableBikes: Int?,

//endregion

//region properties

    @Json(name = "companies")
    var companies: List<TaxiCompany>? = listOf()

//endregion


)