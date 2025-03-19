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
    @SerialName("city") val cityWrapper: CityWrapper
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

@Serializable
data class CityWrapper(
    val data: CityData
)

@Serializable
data class CityData(
    val id: Int,
    val attributes: CityAttributes
)

@Serializable
data class CityAttributes(
    val name: String,
    val longitude: String,
    val latitude: String
)
