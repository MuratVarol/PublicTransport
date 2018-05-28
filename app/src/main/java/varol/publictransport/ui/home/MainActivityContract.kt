package varol.publictransport.ui.home

import varol.publictransport.base.BaseContract
import varol.publictransport.data.enum.TravelModes
import varol.publictransport.data.model.Route
import varol.publictransport.data.model.TravelMode

/**
 * Created by Murat on 22.12.2017.
 */
interface MainActivityContract {

  interface BaseView : BaseContract.BaseView {

    fun showToast(message: String)
    suspend fun showRoutesOnMap(routeList: List<Route>)
    suspend fun showSingleTypeRouteOnMap(routeList: List<Route>)
    suspend fun showAvailableTravelModesOnSheet(travelModeList: List<TravelMode>)

  }

  interface Presenter {

    suspend fun getAllRoutes()
    suspend fun getRoutesByTravelMode(travelModes: TravelModes)

  }

}