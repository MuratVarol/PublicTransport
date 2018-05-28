package varol.publictransport.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/**
 * Created by Murat on 22.12.2017.
 */
@Module
class AppModule(val application: Application) {

  @Provides
  @Singleton
  fun providesApplication(): Application = application

  @Provides
  @Singleton
  fun providesApplicationContext(): Context = application.applicationContext

  @Provides
  @Singleton
  fun providesResources(): Resources = application.resources

}