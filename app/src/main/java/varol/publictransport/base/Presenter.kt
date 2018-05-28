package varol.publictransport.base

import android.view.View
import java.lang.ref.WeakReference

/**
 * Created by Murat on 23.12.2017.
 */
open class Presenter<V : BaseContract.BaseView> : BaseContract.BasePresenter<V> {

  private var weakRef: WeakReference<V>? = null


  override fun attachView(view: V) {
    if (!isViewAttached){
      weakRef= WeakReference(view)
      view.initPresenter(this)
    }
  }

  override fun detachView() {
    weakRef?.clear()
    weakRef = null
  }

  val view: V?
    get() = weakRef?.get()

  private val isViewAttached: Boolean
    get() = weakRef != null && weakRef!!.get() != null

}