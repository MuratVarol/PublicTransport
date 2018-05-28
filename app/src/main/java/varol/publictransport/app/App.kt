package varol.publictransport.app

import android.support.multidex.MultiDexApplication
import varol.publictransport.di.component.AppComponent
import varol.publictransport.di.component.DaggerAppComponent

import varol.publictransport.di.module.AppModule
import varol.publictransport.di.module.NetModule

/**
 * Created by Murat on 22.12.2017.
 */

open class App : MultiDexApplication()
{

  companion object{
    @JvmStatic lateinit var appComponent: AppComponent
  }

  override fun onCreate() {
    super.onCreate()
    initDagger()

  }

  /**
   * Initialize dagger 2 for injection
   */
  private fun initDagger() {
    appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .netModule(NetModule())
        .build()
  }

}