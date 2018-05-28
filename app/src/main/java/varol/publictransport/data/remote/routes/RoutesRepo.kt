package varol.publictransport.data.remote.routes

import varol.publictransport.data.enum.SegmentTypes
import varol.publictransport.data.enum.TravelModes
import varol.publictransport.data.model.Route
import varol.publictransport.data.model.TravelMode
import javax.inject.Inject

/**
 * Created by Murat on 27.12.2017.
 */

/**
 * Use this class as logic layer.
 * This class is responsible of calling services via [RoutesApi]
 * and doing calculations and/or filtering if necessary.
 *
 */



class RoutesRepo {


  private var mRoutesApi: RoutesApi

  @Inject
  constructor(routesApi: RoutesApi) {
    mRoutesApi = routesApi
  }

  suspend fun fetchRoutes(): List<Route> = mRoutesApi.fetchRoutes().await().routes

  suspend fun fetchRoutesByTravelMode(travelMode: TravelModes): List<Route> {
    val allRoutes: List<Route> = mRoutesApi.fetchRoutes().await().routes

    /**
     * For real web service we don't need filtering
     */
    var routeList = arrayListOf<Route>()
    allRoutes.forEach { route ->
      route.segments?.forEach {
        if (it.travel_mode?.trim()?.toLowerCase() == travelMode.value.trim().toLowerCase()) {
          routeList.add(route)
        }
      }

    }
    return routeList
  }


  suspend fun fetchRoutesByType(segmentType: SegmentTypes): List<Route> {
    val allRoutes: List<Route> = mRoutesApi.fetchRoutes().await().routes
    var routeList = arrayListOf<Route>()
    /**
     * For real web service we don't need filtering
     */
    allRoutes.forEach { route ->
      if (route.type?.trim()?.toLowerCase() == segmentType.value.trim().toLowerCase()) {
        routeList.add(route)
      }


    }
    return routeList
  }


  suspend fun fetchAvailableTravelModes(): List<TravelMode> {
    val allRoutes: List<Route> = mRoutesApi.fetchRoutes().await().routes
    var travelModeList = arrayListOf<TravelMode>()
    /**
     * For real web service we don't need filtering
     */
    allRoutes.forEach { route ->
      route.segments?.forEach {
        val travelMode = TravelMode(it.travel_mode, it.color, it.icon_url)
        /*****************************/

        val exist = travelModeList.any {
          it.travel_mode == travelMode.travel_mode
        }

        if (!exist) {
          travelModeList.add(travelMode)
        }

        /**************************************/
//        if (!travelModeList.contains(travelMode)) {
//          travelModeList.add(travelMode)
//        }
      }

    }
    return travelModeList
  }


}



