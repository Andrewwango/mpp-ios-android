package com.jetbrains.handson.mpp.mobile

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonConfiguration
import kotlin.coroutines.CoroutineContext

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

    override fun onSubmitButtonTapped(origStat: String, destStat: String, departureTime: List<Int>?) {
        val client = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json(
                        JsonConfiguration(
                            ignoreUnknownKeys = true
                        )
                    )
                )
            }
        }
        //view?.setTimesLabel("Loading...")
        view?.setLabel("Loading...")

        val departureTimeString = "T${departureTime!![0]}%3A${departureTime!![1]}:00.000%2B00%3A00"

        launch {
            val result = client.get<FareSearchResponse>("https://mobile-api-softwire2.lner.co.uk/v1/fares?originStation=$origStat&destinationStation=$destStat&noChanges=false&numberOfAdults=2&numberOfChildren=0&journeyType=single&outboundDateTime=2020-12-09$departureTimeString&outboundIsArriveBy=false")
            //val outboundDeparture1 = result.outboundJourneys[0].departureTime
            //val inboundDeparture1 = if (result.inboundJourneys != null) result.inboundJourneys!![0].departureTime else "N/A"
            view?.setLabel(result.outboundJourneys[0].originStation.displayName + " -> " + result.outboundJourneys[1].destinationStation.displayName)

            var journeysArray = Array(result.outboundJourneys.size){""}
            for (i in 0..journeysArray.size-1){
                journeysArray[i] = parseDateTime(result.outboundJourneys[i].departureTime)
            }
            view?.showJourneyRecyclerView(journeysArray)
        }

    }

    fun parseDateTime(dt: String) : String{
        val year = dt.slice(0..3)
        val month = dt.slice(5..6)
        val day = dt.slice(8..9)
        val hour = dt.slice(11..12)
        val minute = dt.slice(14..15)
        return "$hour:$minute on $day/$month/$year"
    }
}
