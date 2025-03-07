package uz.gita.latizx.tbcmobile.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.delay
import uz.gita.latizx.presenter.splash.SplashContract
import uz.gita.latizx.presenter.splash.SplashViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SplashViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        SplashScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@Composable
private fun SplashScreenContent(
    uiState: State<SplashContract.UIState> = remember { mutableStateOf(SplashContract.UIState) },
    eventDispatcher: (SplashContract.UIIntent) -> Unit = {},
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1f,
        animationSpec = tween(
            durationMillis = 1300
        ), label = ""
    )

    LaunchedEffect(key1 = Unit) {
        startAnimation = true
        delay(1500)
        eventDispatcher(SplashContract.UIIntent.OpenNextScreen)
    }
    Surface(
        color = AppTheme.colorScheme.backgroundPrimary,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.img_brand_logo_splash),
                    contentDescription = "logo splash",
                    modifier = Modifier.alpha(alphaAnim.value),
                )
                Image(
                    painter = painterResource(R.drawable.img_brand_text_splash),
                    contentDescription = "text splash",
                    modifier = Modifier.alpha(alphaAnim.value)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SplashScreenContent()
}