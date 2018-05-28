package varol.publictransport.ui.home

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.HttpException
import varol.publictransport.base.Presenter
import varol.publictransport.data.enum.SegmentTypes
import varol.publictransport.data.enum.TravelModes
import varol.publictransport.data.remote.routes.RoutesRepo
import javax.inject.Inject

/**
 * Created by Murat on 22.12.2017.
 */
class MainActivityPresenter : Presenter<MainActivityContract.BaseView>, MainActivityContract.Presenter {


  private var mRoutesRepo: RoutesRepo

  @Inject
  constructor(routesRepo: RoutesRepo) {

    mRoutesRepo = routesRepo

  }

   override suspend fun getAllRoutes() {

    launch(UI)
    {
      try {
        val response = mRoutesRepo.fetchRoutes()
        view?.showRoutesOnMap(response)
      } catch (e: Throwable) {
        view?.showToast("Throwable")
      } catch (httpException: HttpException) {
        view?.showToast("HttpException")
      }

    }
  }

  override suspend fun getRoutesByTravelMode(travelMode: TravelModes) {
    launch(UI)
    {
      try {
        val response = mRoutesRepo.fetchRoutesByTravelMode(travelMode)
        view?.showRoutesOnMap(response)
      } catch (e: Throwable) {
        view?.showToast("Throwable")
      } catch (httpE: HttpException) {
        view?.showToast("HttpException")
      }

    }
  }


  suspend  fun getRoutesByType(segmentType: SegmentTypes) {
    launch(UI)
    {
      try {
        val response = mRoutesRepo.fetchRoutesByType(segmentType)
        view?.showRoutesOnMap(response)
      } catch (e: Throwable) {
        view?.showToast("Throwable")
      } catch (httpE: HttpException) {
        view?.showToast("HttpException")
      }

    }
  }

  suspend fun getAvailableTravelModes()
  {
    launch(UI)
    {
      try {
        val response = mRoutesRepo.fetchAvailableTravelModes()
        view?.showAvailableTravelModesOnSheet(response)
      } catch (e: Throwable) {
        view?.showToast("Throwable")
      } catch (httpE: HttpException) {
        view?.showToast("HttpException")
      }

    }
  }


}