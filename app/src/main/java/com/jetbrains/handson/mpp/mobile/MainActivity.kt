package com.jetbrains.handson.mpp.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ApplicationContract.View {
    private var departureTimePicker : TimePickerFragment? = null
    private var journeysRecyclerView : RecyclerView? = null
    private var presenter: ApplicationPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ApplicationPresenter()
        presenter?.onViewTaken(this)

        //Populate recyleview
        //val journeysList = Datasource(this).getJourneysList()
        journeysRecyclerView = journeys_recycler_view

    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }

    override fun setTimesLabel(text: String) {
        times_text.text = text
    }

    override fun populateSpinners() {

        ArrayAdapter.createFromResource(this,
                R.array.origin_array, android.R.layout.simple_spinner_item
        ).also {
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            origin_spinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(this,
                R.array.destination_array, android.R.layout.simple_spinner_item
        ).also {
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            destination_spinner.adapter = adapter
        }
    }

    fun submitStations(view: View) {
        //val presenter = ApplicationPresenter()
        val origStat = origin_spinner.getSelectedItem().toString()
        val destStat = destination_spinner.getSelectedItem().toString()
        presenter?.onSubmitButtonTapped(origStat, destStat, departureTimePicker?.selectedTime)
    }

    fun showTimePickerDialog(view: View) {
        departureTimePicker = TimePickerFragment()
        departureTimePicker!!.show(supportFragmentManager, "timePicker")

    }

    override fun showJourneyRecyclerView(journeysArray : Array<String>) {
        journeysRecyclerView?.adapter = JourneyAdapter(journeysArray)
    }
}
