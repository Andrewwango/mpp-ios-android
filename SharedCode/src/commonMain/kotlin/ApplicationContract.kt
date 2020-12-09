package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.CoroutineScope

interface ApplicationContract {
    interface View {
        fun setLabel(text: String)
        fun populateSpinners()
        fun setTimesLabel(text: String)
    }

    abstract class Presenter: CoroutineScope {
        abstract fun onViewTaken(view: View)
        abstract fun onSubmitButtonTapped(origStat : String, destStat : String, departureTime : String?)
    }
}
