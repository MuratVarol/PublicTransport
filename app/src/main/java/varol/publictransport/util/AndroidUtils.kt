package varol.publictransport.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.annotation.Nullable
import android.webkit.MimeTypeMap
import varol.publictransport.BuildConfig

/**
 * Created by Murat on 25.12.2017.
 */
class AndroidUtils
{


  companion object {

    //region Version check util methods

    fun isMarshmallowOrHigher(): Boolean {
      return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }


    //endregion


    //region Connectivity check util methods

    /**
     * @return ConnectivityManager
     * send context as parameter to prevent activity leaking
     */
    private fun getConnectivityManager(context: Context): ConnectivityManager? {
      return context.applicationContext
          .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }


    /**
     * @return NetworkInfo
     */
    @Nullable
    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
      val connectivityManager = getConnectivityManager(context) ?: return null
      return connectivityManager.activeNetworkInfo
    }

    /**
     * @return Boolean
     * True if there is any network connection
     */
    fun isNetworkConnected(context: Context): Boolean {
      val activeNetworkInfo = getActiveNetworkInfo(context)
      return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

  //endregion


    //region Media check and control utils

    fun getMediaType(url: String?): String? {

      var type :String? = null
      val extension = MimeTypeMap.getFileExtensionFromUrl(url)
      if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: ""
      }
      return type
    }


    fun isProvidedLinkSvgExtension(url: String?) : Boolean
    {
      if (url==null) return false

      getMediaType(url).let {
        return getMediaType(url)?.endsWith("svg")!!
      }

    }



    //endregion


    //region Application utils

    fun isDebugMode(): Boolean = BuildConfig.DEBUG

    //endregion




  }


}