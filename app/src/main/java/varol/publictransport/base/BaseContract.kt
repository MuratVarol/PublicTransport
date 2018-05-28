package varol.publictransport.base

import android.os.Bundle
import varol.publictransport.data.model.Routes

/**
 * Created by Murat on 23.12.2017.
 */
interface BaseContract{

  interface BaseView {

    fun initView(savedInstanceState: Bundle?)
    fun initVariables()
    fun initPresenter(presenter: Presenter<*>)

    fun showLoadingDialog()
    fun hideLoadingDialog()


  }

  interface BasePresenter<in V: BaseView> {
    fun attachView(view: V)
    fun detachView()
  }

}