package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import io.ktor.client.HttpClient
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.JsonConfiguration

class ApplicationPresenter: ApplicationContract.Presenter() {

    private val dispatchers = AppDispatchersImpl()
    private var view: ApplicationContract.View? = null
    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = dispatchers.main + job

    override fun onViewTaken(view: ApplicationContract.View) {
        this.view = view
        view.setLabel(createApplicationScreenMessage())
        view.populateSpinners()
    }

    override fun onSubmitButtonTapped(origStat : String, destStat : String, departureTime : String?) {
        val client = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json(JsonConfiguration(ignoreUnknownKeys = true)))
            }
        }
        view?.setTimesLabel("Loading...")
        view?.setLabel("Loading...")
        launch {
            val result = client.get<FareSearchResponse>("https://mobile-api-softwire2.lner.co.uk/v1/fares?originStation=$origStat&destinationStation=$destStat&noChanges=false&numberOfAdults=2&numberOfChildren=0&journeyType=single&outboundDateTime=2020-12-09$departureTime&outboundIsArriveBy=false")
            val outboundDeparture1 = result.outboundJourneys[0].departureTime
            val inboundDeparture1 = if (result.inboundJourneys != null) result.inboundJourneys!![0].departureTime else "N/A"
            view?.setTimesLabel("Journey: First Outbound: \n  $outboundDeparture1 \n First Inbound: \n  $inboundDeparture1")
            view?.setLabel(result.outboundJourneys[0].originStation.displayName + " -> " + result.outboundJourneys[1].destinationStation.displayName)
        }

    }
}
