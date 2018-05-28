package varol.publictransport.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.support.annotation.DrawableRes
import android.support.annotation.RawRes
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import varol.publictransport.R
import varol.publictransport.util.glideutils.GlideApp
import varol.publictransport.util.glideutils.SvgSoftwareLayerSetter

/**
 * Created by Murat on 21.12.2017.
 */

fun String.toUri(): android.net.Uri = android.net.Uri.parse(this)

fun String.toColor() : Int{
  return try {
    Color.parseColor(this)
  }catch (e: Exception) {
    Color.BLACK
  }
}

//region ImageView's

/**
 * https://github.com/bumptech/glide/blob/master/samples/svg/src/main/java/com/bumptech/glide/samples/svg/MainActivity.java
 */

/**
 * Load given uri source to ImageView
 * @return given ImageView's itself
 */
fun ImageView.loadByUrl(context: Context, uriString: String): ImageView {

  try {
    /**
     * Check extension first; Gradle doesn't support loading svg extension from Uri.
     */
    if (AndroidUtils.isProvidedLinkSvgExtension(uriString)) {
      this.loadSvg(context, uriString)
    } else {
      GlideApp.with(context).load(uriString).into(this)
    }
  }catch (e:Exception)
  {
    /**
     * do better logging
     */
    e.printStackTrace()
  }finally {
    return this
  }

}

/**
 * Load given drawable source to ImageView
 * @return given ImageView's itself
 */
fun ImageView.loadByDrawable(context: Context, @RawRes @DrawableRes drawable: Int): ImageView {

  try {
    /**
     * No need to check file extension. Gradle supports drawables such as  svg, png, xml formats
     */
    GlideApp.with(context).load(drawable).into(this)
  } catch (e: Exception) {
    /**
     * do better logging
     */
    e.printStackTrace()
  } finally {
    return this
  }


}


private fun ImageView.loadSvg(context: Context, uriString: String): ImageView {
  val requestBuilder: RequestBuilder<PictureDrawable>

  requestBuilder = GlideApp.with(context)
      .`as`(PictureDrawable::class.java)
      .listener(SvgSoftwareLayerSetter())
      .placeholder(R.drawable.ic_place_holder)
      .error(R.drawable.ic_broken_image)

  requestBuilder.load(uriString.toUri()).into(this)
  return this
}

/**
 * https://github.com/bumptech/glide/blob/master/samples/svg/src/main/java/com/bumptech/glide/samples/svg/MainActivity.java
 */
private fun ImageView.loadSvg(context: Context, @RawRes @DrawableRes drawable: Int): ImageView {
  val requestBuilder: RequestBuilder<PictureDrawable>

  requestBuilder = GlideApp.with(context)
      .`as`(PictureDrawable::class.java)
      .listener(SvgSoftwareLayerSetter())
      .placeholder(R.drawable.ic_place_holder)
      .error(R.drawable.ic_broken_image)

  requestBuilder.load(drawable).into(this)
  return this
}

fun ImageView.resize(width: Int, height: Int): ImageView {
  this.layoutParams.width = width
  this.layoutParams.height = height
  return this
}

//endregion
