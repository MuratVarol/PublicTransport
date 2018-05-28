package varol.publictransport.data.remote.providers

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import varol.publictransport.app.scope.PerActivity
import javax.inject.Singleton

/**
 * Created by Murat on 23.12.2017.
 */


@Module
class ProvidersRepoModule {
  @Provides
  @Singleton
  fun providesProvidersRepoModule(providersApi: ProvidersApi): ProvidersRepo {
    return ProvidersRepo(providersApi)
  }
}