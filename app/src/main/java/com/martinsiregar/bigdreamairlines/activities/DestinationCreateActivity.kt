package com.martinsiregar.bigdreamairlines.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.martinsiregar.bigdreamairlines.R
import com.martinsiregar.bigdreamairlines.helpers.SampleData
import com.martinsiregar.bigdreamairlines.models.Destination
import com.martinsiregar.bigdreamairlines.services.DestinationService
import com.martinsiregar.bigdreamairlines.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {
	private val TAG : String? = DestinationCreateActivity::class.java.simpleName

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			/*// To be replaced by retrofit code
			SampleData.addDestination(newDestination)
            finish() // Move back to DestinationListActivity*/

			val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.addDestination(newDestination)

			requestCall.enqueue(object: Callback<Destination> {
				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if (response.isSuccessful) {
						finish()
						var newlyCreatedDestination = response.body()
						Log.d(TAG, "onResponse: "+newlyCreatedDestination.toString())
						Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()
					} else {
						Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
					Log.d(TAG, "onFailure: "+t.message)
				}
			})
		}
	}
}
