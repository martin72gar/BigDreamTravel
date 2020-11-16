package com.martinsiregar.bigdreamairlines.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.martinsiregar.bigdreamairlines.R
import com.martinsiregar.bigdreamairlines.helpers.DestinationAdapter
import com.martinsiregar.bigdreamairlines.helpers.SampleData
import com.martinsiregar.bigdreamairlines.models.Destination
import com.martinsiregar.bigdreamairlines.services.DestinationService
import com.martinsiregar.bigdreamairlines.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)
		title = "Destination"


		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
//		loadLocal()
	}

	private fun loadLocal() {
		destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)
	}

	private fun loadDestinations() {

		val destinationService : DestinationService = ServiceBuilder.buildService(DestinationService::class.java)

		val requestCall: Call<List<Destination>> = destinationService.getDestinationList()

		requestCall.enqueue(object: Callback<List<Destination>> {
			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if (response.isSuccessful) {
					val destinationList : List<Destination> = response.body()!!
					destiny_recycler_view.adapter = DestinationAdapter(destinationList)
				} else {
					Toast.makeText(this@DestinationListActivity, "Failed to retrieve item.", Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
				Toast.makeText(this@DestinationListActivity, "Error Occured" + t.toString(), Toast.LENGTH_LONG).show()

			}

		})
    }
}
