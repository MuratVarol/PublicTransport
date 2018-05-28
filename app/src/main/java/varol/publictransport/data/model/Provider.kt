package varol.publictransport.data.model

import com.squareup.moshi.Json

/**
 * Created by Murat on 20.12.2017.
 */

data class Provider(
    @Json(name = "provider_icon_url")
    var providerIconUrl: String?,

    @Json(name = "disclaimer")
    var disclaimer: String?,

    @Json(name = "ios_itunes_url")
    var iosTunesUrl: String?,

    @Json(name = "ios_app_url")
    var iosAppUrl: String?,

    @Json(name = "android_package_name")
    var androidPackageName: String?,

    @Json(name = "display_name")
    var displayName: String?



)