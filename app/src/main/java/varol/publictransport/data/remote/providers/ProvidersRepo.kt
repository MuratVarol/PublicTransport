package varol.publictransport.data.remote.providers

import kotlinx.coroutines.experimental.Deferred
import varol.publictransport.data.model.ProvidersAttributes
import javax.inject.Inject

/**
 * Created by Murat on 30.12.2017.
 */
class ProvidersRepo {

  private lateinit var mProvidersApi: ProvidersApi


  @Inject
  constructor(providersApi: ProvidersApi)
  {
    mProvidersApi = providersApi
  }

  fun fetchProviders() : Deferred<ProvidersAttributes> = mProvidersApi.fetchProvidersAttributes()

}