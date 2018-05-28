package varol.publictransport.base

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.widget.Toast
import varol.publictransport.app.App
import varol.publictransport.di.component.AppComponent

/**
 * Created by Murat on 22.12.2017.
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.BaseView {



  private val TAG = BaseActivity::class.simpleName.toString()

  private var mPresenter: Presenter<*>? = null



  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)

    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    injectComponents()

  }


  protected abstract fun injectComponents()


  fun getAppComponent(): AppComponent = App.appComponent

  override fun initPresenter(presenter: Presenter<*>) {
    this.mPresenter = presenter
  }


  override fun onDestroy() {
    super.onDestroy()

    mPresenter?.detachView()
    mPresenter = null

//    refWatcher.watch(this, TAG)
  }


  //region custom methods


  fun isGpsEnabled(): Boolean{
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
  }

  //endregion


}
