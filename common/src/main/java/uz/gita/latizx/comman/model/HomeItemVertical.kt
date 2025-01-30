package uz.gita.latizx.comman.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import uz.gita.latizx.comman.enums.HomeItemVerticalEnum

data class HomeItemVertical(
    @StringRes val title: Int,
    @StringRes val text: Int,
    @DrawableRes val image: Int,
    @ColorRes val cardColor: Int,
    val type: HomeItemVerticalEnum,
)
