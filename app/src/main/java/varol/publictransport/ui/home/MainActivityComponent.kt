package varol.publictransport.ui.home

import dagger.Component
import varol.publictransport.app.scope.PerActivity
import varol.publictransport.di.component.AppComponent

/**
 * Created by Murat on 22.12.2017.
 */

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class) ,
    modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
  fun inject(mainActivity: MainActivity)

} 

//
//https://github.com/jaredsburrows/android-gif-example/tree/master/src/main/kotlin/burrows/apps/example/gif/data/rest
//https://www.youtube.com/watch?v=j8Axa404ems
//https://medium.com/@rachitmishra/dependency-injection-on-android-with-dagger-2-and-kotlin-part-2-460062d53880
//https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2
//https://stackoverflow.com/questions/38979546/how-can-dagger-2-be-used-to-inject-using-multiple-components-into-the-same-objec