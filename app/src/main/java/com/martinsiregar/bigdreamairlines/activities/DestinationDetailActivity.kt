package com.martinsiregar.bigdreamairlines.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.martinsiregar.bigdreamairlines.R
import com.martinsiregar.bigdreamairlines.helpers.SampleData
import com.martinsiregar.bigdreamairlines.models.Destination
import com.martinsiregar.bigdreamairlines.services.DestinationService
import com.martinsiregar.bigdreamairlines.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_detail)

        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code
        /*val destination = SampleData.getDestinationById(id)


        }*/
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getDestination(id)

        requestCall.enqueue(object : Callback<Destination> {
            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()
                    destination?.let {
                        et_city.setText(destination.city)
                        et_description.setText(destination.description)
                        et_country.setText(destination.country)

                        collapsing_toolbar.title = destination.city
                    }
                } else {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Toast.makeText(
                    this@DestinationDetailActivity,
                    "Failed to retrieve details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

            val city = et_city.text.toString()
            val description = et_description.text.toString()
            val country = et_country.text.toString()

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.updateDestination(id, city, country, description)

            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish()
                        var updatedDestination = response.body()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            updatedDestination!!.city + " successfully updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Failed to update item",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to update item",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            /*// To be replaced by retrofit code
            val destination = Destination()
            destination.id = id
            destination.city = city
            destination.description = description
            destination.country = country

            SampleData.updateDestination(destination);
            finish() // Move back to DestinationListActivity*/
        }
    }

    private fun initDeleteButton(id: Int) {

        btn_delete.setOnClickListener {

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.deleteDestination(id)

            requestCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Failed to delete",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to delete",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            /*// To be replaced by retrofit code
            SampleData.deleteDestination(id)
            finish() // Move back to DestinationListActivity*/
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
