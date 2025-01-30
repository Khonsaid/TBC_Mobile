package uz.gita.latizx.tbcmobile.screen.auth.page

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import uz.gita.latizx.tbcmobile.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
) {
    object One : OnBoardingPage(
        image = R.drawable.ill_intro_step_1,
        title = R.string.intro_title_page_1
    )

    object Two : OnBoardingPage(
        image = R.drawable.ill_intro_step_2,
        title = R.string.intro_title_page_2
    )

    object Three : OnBoardingPage(
        image = R.drawable.ill_intro_step_3,
        title = R.string.intro_title_page_3
    )

    object Four : OnBoardingPage(
        image = R.drawable.ill_intro_step_4,
        title = R.string.intro_title_page_4
    )

    object Five : OnBoardingPage(
        image = R.drawable.ill_intro_step_5,
        title = R.string.intro_title_page_5
    )
}