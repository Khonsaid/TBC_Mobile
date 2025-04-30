package uz.gita.latizx.tbcmobile.screen.settings.map

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.gita.latizx.presenter.settings.map.MapContract
import uz.gita.latizx.presenter.settings.map.MapViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.screen.settings.bottom_sheet.LocationBottomSheet
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar

class MapScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<MapViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        BottomSheetNavigator {
            MapScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MapScreenContent(
    uiState: State<MapContract.UIState> = remember { mutableStateOf(MapContract.UIState()) },
    eventDispatcher: (MapContract.UIIntent) -> Unit = {},
) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    LaunchedEffect(uiState.value.showInfoDialog) {
        if (uiState.value.showInfoDialog)
            uiState.value.locationData?.let { data ->
                val dialog = LocationBottomSheet(data)
                dialog.onDismissRequest = {
                    eventDispatcher(MapContract.UIIntent.HideDialog)
                    bottomSheetNavigator.hide()
                }
                dialog.onSelectCard = {

                }
                bottomSheetNavigator.show(dialog)
            }
    }
    LaunchedEffect(bottomSheetNavigator) {
        snapshotFlow { bottomSheetNavigator.isVisible }.collect { isVisible ->
            if (!isVisible) eventDispatcher(MapContract.UIIntent.HideDialog)
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(uiState.value.location.first, uiState.value.location.second), 12f)
    }
    val locationPermissions = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    )

    Scaffold(
        topBar = {
            AppTopBar(
                text = R.string.sp_map_switcher_left_button,
                onClickBack = { eventDispatcher(MapContract.UIIntent.OpenPrev) }
            )
        }
    ) {
        Box(
            Modifier.padding(it)
        ) {
            LaunchedEffect(Unit) {
                if (locationPermissions.allPermissionsGranted) {

                } else {
                    locationPermissions.launchMultiplePermissionRequest()
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isBuildingEnabled = true, mapType = MapType.NORMAL),
                onMapClick = {

                },
                content = {
                    uiState.value.data.forEach { location ->
                        val context = LocalContext.current
                        var markerIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }
                        LaunchedEffect(location) {
                            markerIcon = bitmapDescriptorFromVector(context, R.drawable.ic_map_pin)
                        }
                        Marker(
                            state = MarkerState(LatLng(location.attributes.marker.coordinates.lat, location.attributes.marker.coordinates.lng)),
                            title = location.attributes.title,
                            icon = markerIcon,
                            onClick = {
                                eventDispatcher(MapContract.UIIntent.ShowDialog(location))
                                true
                            }
                        )
                    }
                }
            )
        }
    }
}

private suspend fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor? = withContext(Dispatchers.Default) {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId) ?: return@withContext null
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    BitmapDescriptorFactory.fromBitmap(bitmap)
}