package varol.publictransport.data.remote.routes

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import varol.publictransport.app.scope.PerActivity
import javax.inject.Singleton

/**
 * Created by Murat on 23.12.2017.
 */


@Module
class RoutesRepoModule {
  @Provides
  @Singleton
  fun providesRoutesApiModule(routesApi: RoutesApi): RoutesRepo {
    return RoutesRepo(routesApi)
  }

}