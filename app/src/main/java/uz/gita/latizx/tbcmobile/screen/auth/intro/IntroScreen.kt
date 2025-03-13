package uz.gita.latizx.tbcmobile.screen.auth.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import uz.gita.latizx.presenter.auth.intro.IntroContract
import uz.gita.latizx.presenter.auth.intro.IntroViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.screen.auth.dialog.LanguageDialog
import uz.gita.latizx.tbcmobile.screen.auth.page.OnBoardingPage
import uz.gita.latizx.tbcmobile.ui.components.button.AppFilledButton
import uz.gita.latizx.tbcmobile.ui.components.button.AppOutlinedButton
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@OptIn(ExperimentalVoyagerApi::class)
class IntroScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: IntroContract.ViewModel = getViewModel<IntroViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        BottomSheetNavigator(
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            IntroScreenContent(uiState, viewModel::onEventDispatcher)
        }
        val context = LocalContext.current
        LaunchedEffect(uiState.value.shouldRecreateActivity) {
            if (uiState.value.shouldRecreateActivity) {
                if (uiState.value.isBottomSheetVisible) {
                    viewModel.onEventDispatcher(IntroContract.UiIntent.HideLanguageBottomSheet)
                }
                // Joriy activity ni oling va qayta yarating
                val activity = context as? android.app.Activity
                activity?.recreate()

                viewModel.onEventDispatcher(IntroContract.UiIntent.ResetRecreateFlag)
            }
        }
    }
}

@Composable
private fun IntroScreenContent(
    uiState: State<IntroContract.UiState> = remember { mutableStateOf(IntroContract.UiState()) },
    eventDispatcher: (IntroContract.UiIntent) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colorScheme.backgroundPrimary
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {/* Support click */
                        eventDispatcher(IntroContract.UiIntent.OpenSupportScreen)
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_intro_message),
                        contentDescription = "img message"
                    )
                }
                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .clickable(
                            indication = ripple(radius = 16.dp, color = AppTheme.colorScheme.backgroundBrandTertiary.copy(alpha = 0.2f)),
                            interactionSource = remember { MutableInteractionSource() }) {/* Language click */
                            eventDispatcher(IntroContract.UiIntent.ShowLanguageBottomSheet)
                        }
                        .background(
                            color = AppTheme.colorScheme.backgroundBrandTertiary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = when (uiState.value.lang) {
                            "ru" -> "RU"
                            "en" -> "EN"
                            else -> "UZ"
                        },
                        style = AppTheme.typography.captionLarge,
                        color = AppTheme.colorScheme.textOnPrimary
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_down_regular),
                        contentDescription = "ic down",
                        tint = AppTheme.colorScheme.textOnPrimary
                    )
                }
            }

            val pages = listOf(
                OnBoardingPage.Two, OnBoardingPage.Five, OnBoardingPage.Three, OnBoardingPage.One, OnBoardingPage.Four,
            )
            val pagerState = rememberPagerState(pageCount = { pages.size })
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                HorizontalPager(
                    modifier = Modifier.weight(4f),
                    state = pagerState,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PageContent(onBoardingPage = pages[it])
                }
                // Indicators Row
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) AppTheme.colorScheme.backgroundStatusInfoSecondary
                            else AppTheme.colorScheme.backgroundStatusInfo
                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(10.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AppOutlinedButton(
                    text = stringResource(R.string.common_signin_button_text),
                    onClick = { /*Sign in click*/
                        eventDispatcher(IntroContract.UiIntent.OpenSignInScreen)
                    }
                )

                AppFilledButton(
                    text = stringResource(R.string.signing_sign_up),
                    onClick = { /*Sign up click*/
                        eventDispatcher(IntroContract.UiIntent.OpenSignUpScreen)
                    }
                )
            }

            val bottomSheetNavigator = LocalBottomSheetNavigator.current
            LaunchedEffect(uiState.value.isBottomSheetVisible) {
                if (uiState.value.isBottomSheetVisible) {
                    val dialog = LanguageDialog(uiState.value.lang)
                    dialog.onClickLang = {
                        eventDispatcher(IntroContract.UiIntent.SaveLanguage(it))
                        bottomSheetNavigator.hide()
                    }
                    dialog.onDismissRequest = {
                        bottomSheetNavigator.hide()
                        eventDispatcher(IntroContract.UiIntent.HideLanguageBottomSheet)
                    }
                    bottomSheetNavigator.show(dialog)
                } else {
                    if (bottomSheetNavigator.isVisible) bottomSheetNavigator.hide()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    IntroScreenContent()
//    PageContent(OnBoardingPage.First)
}

@Composable
private fun PageContent(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(onBoardingPage.image),
            contentDescription = "onBoardingPage"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = stringResource(onBoardingPage.title),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colorScheme.textPrimary,
            textAlign = TextAlign.Center
        )
    }
}