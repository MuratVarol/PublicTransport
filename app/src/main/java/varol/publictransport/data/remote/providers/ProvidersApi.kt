package varol.publictransport.data.remote.providers

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import varol.publictransport.data.model.ProvidersAttributes
import javax.inject.Inject

/**
 * Created by Murat on 23.12.2017.
 */

class ProvidersApi {

  //region variable declaration

  private var mRetrofit: Retrofit
  private var mProviderService: IProvidersApi

  //endregion

  //region constructer

  @Inject
  constructor(retrofit: Retrofit){
    mRetrofit = retrofit
    mProviderService = mRetrofit.create(IProvidersApi::class.java)
  }

  //endregion


  fun fetchProvidersAttributes(): Deferred<ProvidersAttributes> =
      mProviderService.fetchProvidersAttributes()


}

