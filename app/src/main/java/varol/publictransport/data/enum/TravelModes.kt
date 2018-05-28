package varol.publictransport.data.enum

import varol.publictransport.data.enum.TravelModes.CYCLING

/**
 * Created by Murat on 31.12.2017.
 */
enum class TravelModes constructor(val value: String){
   CYCLING ("cycling"),
   DRIVING ("driving" ),
   WALKING ("walking"),
   SUBWAY ("subway"),
   BUS ("bus");

   companion object {
      fun getEnumValue(value: String): TravelModes?
      {
         return when(value) {
            "CYCLING" -> TravelModes.CYCLING
            "DRIVING" -> TravelModes.DRIVING
            "WALKING" -> TravelModes.WALKING
            "SUBWAY" -> TravelModes.SUBWAY
            "BUS" -> TravelModes.BUS
            else -> null
         }
      }

   }


}
