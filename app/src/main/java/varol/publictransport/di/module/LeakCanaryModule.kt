package varol.publictransport.di.module

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import dagger.Provides
import varol.publictransport.app.scope.PerActivity
import javax.inject.Singleton


/**
 * Created by Murat on 22.12.2017.
 */

@Module
class LeakCanaryModule {

  @Provides
  @Singleton
  fun providesRefWatcher(application: Application): RefWatcher = LeakCanary.install(application)
}