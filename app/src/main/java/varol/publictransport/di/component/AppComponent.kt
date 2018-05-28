package varol.publictransport.di.component

import android.app.Application
import android.content.res.Resources
import com.squareup.leakcanary.RefWatcher
import com.squareup.moshi.Moshi
import dagger.Component
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import varol.publictransport.data.remote.providers.ProvidersApi
import varol.publictransport.data.remote.providers.ProvidersRepoModule
import varol.publictransport.data.remote.routes.RoutesApi
import varol.publictransport.data.remote.routes.RoutesRepoModule
import varol.publictransport.di.module.AppModule
import varol.publictransport.di.module.LeakCanaryModule
import varol.publictransport.di.module.NetModule
import javax.inject.Singleton

/**
 * Created by Murat on 22.12.2017.
 */
@Singleton
@Component(modules = arrayOf(
  AppModule::class,
  NetModule::class,
  RoutesRepoModule::class,
  LeakCanaryModule::class,
  ProvidersRepoModule::class))

interface AppComponent {

  fun application(): Application

  fun resources(): Resources

  fun cache(): Cache

  fun okHttpCliend(): OkHttpClient

  fun moshi(): Moshi

  fun retrofit(): Retrofit

  fun routesApi(): RoutesApi

  fun providersApi(): ProvidersApi

  fun refWatcher(): RefWatcher

}