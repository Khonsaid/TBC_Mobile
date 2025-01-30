package uz.gita.latizx.tbcmobile.navigator

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.transitions.ScreenTransition

@OptIn(ExperimentalVoyagerApi::class)
class CustomTransition : ScreenTransition {
    override fun enter(lastEvent: StackEvent): EnterTransition? {
        return slideIn { size ->
            val x = 0 // X o'qi bo'yicha harakatlanmaydi
            IntOffset(x, size.height)// Pastdan tepaga kirish
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition? {
        return slideOut { size ->
            val x = 0 // X o'qi bo'yicha harakatlanmaydi
            IntOffset(x, -size.height) // Tepadan pastga chiqish
        }
    }
}