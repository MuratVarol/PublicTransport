package varol.publictransport.data.remote.routes

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import varol.publictransport.data.model.Route
import varol.publictransport.data.model.Routes

/**
 * Created by Murat on 23.12.2017.
 */

interface IRoutesApi{


  @GET("door2door-io/transit-app-task/master/data.json")
  fun fetchRoutes() : Deferred<Routes>

}