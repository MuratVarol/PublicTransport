package varol.publictransport.ui.home

import dagger.Module
import dagger.Provides
import varol.publictransport.app.scope.PerActivity
import varol.publictransport.data.remote.routes.RoutesRepo

/**
 * Created by Murat on 22.12.2017.
 */
@Module
class MainActivityModule {

  @PerActivity
  @Provides
  internal fun providesMainActivityPresenter(routesRepo: RoutesRepo): MainActivityPresenter
      = MainActivityPresenter(routesRepo)
}