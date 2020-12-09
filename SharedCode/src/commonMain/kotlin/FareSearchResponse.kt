package com.jetbrains.handson.mpp.mobile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class FareSearchResponse(
    @SerialName("numberOfAdults")
    val numberOfAdults: Int,
    @SerialName("numberOfChildren")
    val numberOfChildren: Int,
    @SerialName("outboundJourneys")
    val outboundJourneys: List<JourneyFareResponse>,
    @SerialName("inboundJourneys")
    val inboundJourneys: List<JourneyFareResponse>? = null,
    @SerialName("nextOutboundQuery")
    val nextOutboundQuery: String? = null,
    @SerialName("previousOutboundQuery")
    val previousOutboundQuery: String? = null,
    @SerialName("nextInboundQuery")
    val nextInboundQuery: String? = null,
    @SerialName("previousInboundQuery")
    val previousInboundQuery: String? = null
    //@SerialName("bookingMessages")
    //val bookingMessages: BookingMessagesResponse
)

@Serializable
data class JourneyFareResponse(
    @SerialName("arrivalTime")
    val arrivalTime: String,
    @SerialName("departureTime")
    val departureTime: String,
    @SerialName("destinationStation")
    val destinationStation: Station,
    @SerialName("originStation")
    val originStation: Station
)

@Serializable
data class Station(
    @SerialName("crs")
    val crs: String,
    @SerialName("displayName")
    val displayName : String
)