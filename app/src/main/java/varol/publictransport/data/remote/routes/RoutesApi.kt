package varol.publictransport.data.remote.routes

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import varol.publictransport.data.model.Routes
import javax.inject.Inject

/**
 * Created by Murat on 22.12.2017.
 */


/**
 * This class responsible of communication with web services
 * Don't use this class directly from activity/fragment or presenter, best practice is+
 *  calling this class upon [RoutesRepo] class
 */

class RoutesApi {

  //region variable declaration

  private var mRetrofit: Retrofit
  private var mRouteService: IRoutesApi

  //endregion


  //region constructer

  @Inject
  constructor(retrofit: Retrofit) {
    mRetrofit = retrofit
    mRouteService = mRetrofit.create(IRoutesApi::class.java)

  }

  //endregion


  //region Call methods

  fun fetchRoutes(): Deferred<Routes> = mRouteService.fetchRoutes()


  //endregion

}