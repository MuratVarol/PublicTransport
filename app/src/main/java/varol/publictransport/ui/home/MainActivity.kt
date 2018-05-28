package varol.publictransport.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.StringRes
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.squareup.leakcanary.RefWatcher
import kotlinx.android.synthetic.main.activity_main.cl_bottomSheetHolder_ActMain
import kotlinx.android.synthetic.main.activity_main.cl_layout_MainAct
import kotlinx.android.synthetic.main.activity_main.cv_allRoutes_MainAct
import kotlinx.android.synthetic.main.activity_main.cv_myLocation_MainAct
import kotlinx.android.synthetic.main.activity_main.iv_dragBottomSheet_ActMain
import kotlinx.android.synthetic.main.activity_main.iv_locationIcon_MainAct
import kotlinx.android.synthetic.main.activity_main.iv_settings_MainAct
import kotlinx.android.synthetic.main.activity_main.rl_bottomSheet_ActMain
import kotlinx.android.synthetic.main.activity_main.rl_searchBar_MainAct
import kotlinx.android.synthetic.main.activity_main.rv_travelModes_MainAct
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions
import varol.publictransport.R
import varol.publictransport.R.layout
import varol.publictransport.adapter.CommonAdapter
import varol.publictransport.adapter.CommonAdapter.OnRouteClickListener
import varol.publictransport.adapter.CommonAdapter.OnTravelModeClickListener
import varol.publictransport.base.BaseActivity
import varol.publictransport.base.BaseSettings
import varol.publictransport.data.enum.TravelModes
import varol.publictransport.data.model.Route
import varol.publictransport.data.model.Segment
import varol.publictransport.data.model.TravelMode
import varol.publictransport.util.toColor
import javax.inject.Inject

@RuntimePermissions
class MainActivity : BaseActivity(), OnMapReadyCallback, MainActivityContract.BaseView {



  private val TAG = MainActivity::class.simpleName.toString()

  //region variable declaration

  @Inject
  lateinit var presenter: MainActivityPresenter
  @Inject
  lateinit var refWatcher: RefWatcher


  private var googleMap: GoogleMap? = null
  private var mapView: MapView? = null
  private var locationProviderClient: FusedLocationProviderClient? = null


  private lateinit var routesAdapter: CommonAdapter<Route>
  private lateinit var travelModesAdapter: CommonAdapter<TravelMode>

  //endregion

  //region Widget declaration


  //endregion

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    injectComponents()

    initView(savedInstanceState)

    initVariables()
  }


  override fun injectComponents() {
    DaggerMainActivityComponent.builder()
        .appComponent(getAppComponent())
        .mainActivityModule(MainActivityModule())
        .build()
        .inject(this)

    presenter.attachView(this)
  }


  override fun initView(savedInstanceState: Bundle?) {

    iv_settings_MainAct?.setOnClickListener {
      showToast(resources.getString(R.string.will_be_available_soon))
    }

    rl_searchBar_MainAct.setOnClickListener {
      showToast(resources.getString(R.string.will_be_available_soon))
    }
    
    //region BottomSheet
    val bottomSheetBehavior = BottomSheetBehavior.from(rl_bottomSheet_ActMain)

    /**
     *  set [bottomSheet_ActMain]'s click listener
     */
    iv_dragBottomSheet_ActMain?.setOnClickListener {
      if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
      } else if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
      }
    }

    /**
     *  set [bottomSheet_ActMain]'s behavior listener
     */
    bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
      override fun onStateChanged(bottomSheet: View, newState: Int) {
        /** DO NOTHING **/
      }

      override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val rotation = (slideOffset * 180)
        Log.i("slideOffset", slideOffset.toString() + "...rotation: " + rotation)
        iv_dragBottomSheet_ActMain?.animate()?.rotation(rotation)?.setDuration(1)?.start()
      }
    })

    //endregion

    //region recyclerViews

    routesAdapter = CommonAdapter(this)
    travelModesAdapter = CommonAdapter(this)

    rv_travelModes_MainAct.layoutManager = GridLayoutManager(this, 5)
    rv_travelModes_MainAct.adapter = travelModesAdapter

//    rv_routes_MainAct.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//    rv_routes_MainAct.adapter = routesAdapter

    //endregion

    /**
     * Set MapView
     */
    mapView = findViewById<MapView>(R.id.mapView_ActMain)
    mapView?.let {
      it.onCreate(savedInstanceState)
      it.onResume()
      it.getMapAsync(this)
    }

    initMapView()


    cv_myLocation_MainAct?.setOnClickListener {
      MainActivityPermissionsDispatcher.getMyLocationWithCheck(this)
    }

    cv_allRoutes_MainAct.setOnClickListener {
      launch(UI) {
        presenter.getAllRoutes()
      }
    }


  }


  /**
   * Initialize all variables for first run and call required methods on create
   */
  override fun initVariables() {

    locationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    launch(UI) {

      //      presenter.getRoutesByTravelMode(TravelModes.BUS)
      presenter.getAvailableTravelModes()

      //presenter.getRoutesByType(SegmentTypes.TAXI)
    }

    //region Adapter's item click listeners

    /** Click listener for [Route] item **/

    routesAdapter.setOnRouteItemClickListener(object: OnRouteClickListener{
      override fun onItemClick(position: Int) {

      }

    })


    travelModesAdapter.setOnTravelModeItemClickListener(object : OnTravelModeClickListener{
      override fun onItemClick(position: Int) {
        val travelMode = travelModesAdapter.getItemTravelMode(position)
        travelMode?.let {
          launch(UI) {
            try {
              val eTravelMode :TravelModes? = TravelModes.getEnumValue(travelMode.toUpperCase())
              if(eTravelMode != null)
              {
                presenter.getRoutesByTravelMode(eTravelMode)
              }else
              {
                googleMap?.clear()
                toast(resources.getString(R.string.no_available_route))
              }

            }catch (e:Exception)
            {
              googleMap?.clear()
              toast(resources.getString(R.string.no_available_route))

              // TODO: HANDLE EXCEPTION
              e.printStackTrace()
            }


          }

        }
      }

    })


    //endregion

  }


  /**
   * Initialize MapView with MapsInitializer
   */
  private fun initMapView() {
    try {
      MapsInitializer.initialize(this)
    } catch (ex: Exception) {
      ex.printStackTrace()
    }
  }


  /**
   * Put all the routes filtered by [TravelModes] enum
   * This method will be called after [MainActivityPresenter.getRoutesByType] method's success return
   */
  override suspend fun showSingleTypeRouteOnMap(routeList: List<Route>) {

    googleMap?.clear()

    routeList.forEach { route ->
      try {
        route.segments!!.forEach { segment ->
          showPolyLineOnMap(segment)
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

  }


  /**
   * List all available travel modes
   * This method will be called after [MainActivityPresenter.getAvailableTravelModes]+
   * - method's success return
   */
  override suspend fun showAvailableTravelModesOnSheet(travelModeList: List<TravelMode>) {
    travelModesAdapter.addAll(travelModeList)
  }


  /**
   * List filtered routes by [SegmentTypes] filter
   */
  override suspend fun showRoutesOnMap(routeList: List<Route>) {

    googleMap?.clear()

    /**
     * loops on [Route] list and it's nested [Segment] field and after [Segment]'s nested [Stop] +
     * - list and put all stops and polylines to map
     */
    routeList.forEach { route ->
      try {
        route.segments!!.forEach { segment ->
          showPolyLineOnMap(segment)
        }
      } catch (e: Exception) {
        // TODO: HANDLE EXCEPTION
        e.printStackTrace()
      }
    }

  }


  /**
   * Put poly lines to map by given [Segment]'s [Segment.polyline] variable
   * Also puts other properties of [Segment.polyline] to placed polyline
   */
  private fun showPolyLineOnMap(segment: Segment) {
    try {

      /**
       * decode polyline to LatLng list, change color with service provided color and put on map
       */
      val latLngList = PolyUtil.decode(segment.polyline ?: return)
      val polylineColor = segment.color?.toColor() ?: Color.BLACK
      val polyOptions = PolylineOptions().addAll(latLngList).color(polylineColor)
      googleMap?.addPolyline(polyOptions)


      /**
       * polyline click listener
       */
      googleMap?.setOnPolylineClickListener { polyline ->
        toast(polyline.tag.toString())
      }

      /**
       * put stops on map with colors
       */
      segment.stops.forEach {
        val iconDrawable = (ContextCompat.getDrawable(this, R.drawable.ic_adjust))!!
        val mapMarker = MarkerOptions().title(it.name ?: resources.getString(R.string.stop))
            .icon(getMarkerIconFromDrawable(iconDrawable, polylineColor))
            .snippet(segment.travel_mode ?: "")
            .position(LatLng(it.lat ?: 0.0, it.lng ?: 0.0))
        googleMap?.addMarker(mapMarker)
      }
    } catch (e: Exception) {
      // TODO: HANDLE EXCEPTION
      e.printStackTrace()
    }


  }


  override fun showToast(message: String) {
    toast(message)
  }


  /**
   * @return [BitmapDescriptor]
   * Converts given Drawable to BitmapDescriptor, also change drawable's color by given+
   * - color parameter
   */
  private fun getMarkerIconFromDrawable(drawable: Drawable, color: Int): BitmapDescriptor {
    drawable.setColorFilter(color, Mode.SRC_ATOP)
    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888)
    canvas.setBitmap(bitmap)
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
  }


  /**
   * If permission granted; shows current location after listener worked successfully
   * else; ask for permission
   */
  @SuppressLint("MissingPermission")
  @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION)
  fun getMyLocation() {

    if(isGpsEnabled())
    {
      googleMap?.isMyLocationEnabled = true

      iv_locationIcon_MainAct?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_repeat))

      locationProviderClient?.lastLocation?.addOnSuccessListener { location ->



        location?.let {
          iv_locationIcon_MainAct.clearAnimation()
          moveAndZoomCamera(location)
          iv_locationIcon_MainAct.setBackgroundResource(R.drawable.ic_my_location)
        }
      }
    }else
    {
     val gpsSnackBar: Snackbar = Snackbar.make(cl_bottomSheetHolder_ActMain,R.string.enable_gps_snack_text,Snackbar.LENGTH_LONG)
      gpsSnackBar.setAction(R.string.enable_gps_snack_action_text, {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
      })

    }



  }


  /**
   * @param location: Converts given [Location] to [LatLng] and move camera to given location
   * Moves and zoom camera on my location button click
   */
  private fun moveAndZoomCamera(location: Location) {
    val zoom = (CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude),
        BaseSettings.zoomValueOnMoveLocation) as CameraUpdate)
    googleMap?.animateCamera(zoom)
  }


  /**
   * @param latLng: move camera to given [LatLng]
   * Moves and zoom camera on my location button click
   */
  private fun moveAndZoomCamera(latLng: LatLng) {
    val zoom = (CameraUpdateFactory.newLatLngZoom(latLng, 10F) as CameraUpdate)
    googleMap?.animateCamera(zoom)
  }


  /**
   * [isMyLocationButtonEnabled] property needs Location permission, without permission running+
   *  this method will cause exception. [PermissionDispatcher] handle this and it is OK to put+
   *   @SuppressLint("MissingPermission") annotation
   */
  @SuppressLint("MissingPermission")
  override fun onMapReady(gMap: GoogleMap?) {
    googleMap = gMap ?: return
    googleMap?.uiSettings?.isMyLocationButtonEnabled = false


  }



  override fun showLoadingDialog() {

  }

  override fun hideLoadingDialog() {

  }

  /** Methods in this region will run only AndroidVersion >= Marshmallow(6.0, Api Level 23) */
  //region Permission checks

  /**
   * Common rationale dialog that pop up dialog and show user to why he/she should give permission
   */
  private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
    AlertDialog.Builder(this)
        .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
        .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
        .setCancelable(false)
        .setMessage(messageResId)
        .show()
  }

  /**
   * Request permission result method, will be called after user accept or deny permission asking
   */
  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
      grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
  }

  /**
   * This method will be called by generated PermissionsDispatcher's method on we need location permission
   */
  @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION)
  fun onGetMyLocationShow(request: PermissionRequest) {
    showRationaleDialog(R.string.give_location_permission, request)
  }

  /**
   * This method will be called after user denied giving permission to access location
   */
  @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION)
  fun onGetMyLocationShowDenied() {
    iv_locationIcon_MainAct.setBackgroundResource(R.drawable.ic_gps_off)
  }

  /**
   * This method will be called after user select "never ask again" options
   */
  @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION)
  fun onGetMyLocationShowNeverAskAgain() {
  }

//endregion


  override fun onResume() {
    super.onResume()
    mapView?.onResume()

  }

  override fun onPause() {
    super.onPause()
    mapView?.onPause()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView?.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView?.onDestroy()
    refWatcher.watch(this, TAG)
  }

}
