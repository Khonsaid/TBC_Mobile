package uz.gita.latizx.tbcmobile

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.FragmentActivity
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.latizx.comman.LocationHelper
import uz.gita.latizx.tbcmobile.navigator.AppNavigatorHandler
import uz.gita.latizx.tbcmobile.screen.main.settings.SettingsScreen
import uz.gita.latizx.tbcmobile.screen.splash.SplashScreen
import uz.gita.latizx.tbcmobile.ui.theme.TBCMobileTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var navigatorHandler: AppNavigatorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            TBCMobileTheme {
                Navigator(SplashScreen()) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigatorHandler.navigation.collect {
                            it.invoke(navigator)
                        }
                    }
                    ScaleTransition(navigator = navigator)
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocationHelper.attach(newBase))
    }
}