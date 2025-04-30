package uz.gita.latizx.comman.model.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationData(
    val id: Int,
    val attributes: LocationAttributes
)

@Serializable
data class LocationAttributes(
    val title: String,
    val address: String,
    val marker: Marker,
    val description: String,
)

@Serializable
data class Marker(
    val geohash: String,
    val coordinates: Coordinates
)

@Serializable
data class Coordinates(
    val lat: Double,
    val lng: Double
)

