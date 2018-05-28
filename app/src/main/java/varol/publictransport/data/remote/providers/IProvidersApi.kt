package varol.publictransport.data.remote.providers

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import varol.publictransport.data.model.ProvidersAttributes

/**
 * Created by Murat on 23.12.2017.
 */

interface IProvidersApi {

  @GET("door2door-io/transit-app-task/master/data.json")
  fun fetchProvidersAttributes(): Deferred<ProvidersAttributes>

}