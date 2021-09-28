package com.worldline.pointsofinterest

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.worldline.pointsofinterest.data.AndroidNetworkStatusChecker
import com.worldline.pointsofinterest.data.PointsOfInterestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class POIDetailFragment: Fragment() {
    private val pointsOfInterestRepository: PointsOfInterestRepository by lazy {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkStatusChecker = AndroidNetworkStatusChecker(connectivityManager)
        PointsOfInterestRepository(networkStatusChecker)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.poi_detail_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pointOfInterestId = arguments?.get("selectedPointOfInterestId").toString().toInt()
        fetchPointOfInterest(pointOfInterestId)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchPointOfInterest(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val pointOfInterest = context?.let { pointsOfInterestRepository.fetchPointOfInterestDetail(id) }
            launch(Dispatchers.Main) {
                pointOfInterest?.let { pointOfInterest ->
                    if (pointOfInterest.isValid()) {
                        view?.let { view ->
                            view.apply {
                                val titleView = findViewById<TextView>(R.id.poi_detail_title)
                                val coordinatesView = findViewById<TextView>(R.id.poi_detail_geocoordinates)
                                val descriptionView = findViewById<TextView>(R.id.poi_detail_description)
                                val descriptionDivider = findViewById<View>(R.id.description_cell_divider)

                                // Views to hide if value is null
                                val mailCellView = findViewById<View>(R.id.poi_detail_email_cell)
                                val phoneCellView = findViewById<View>(R.id.poi_detail_phone_cell)
                                val transportCellView = findViewById<View>(R.id.poi_detail_transport_cell)
                                val addressCellView = findViewById<View>(R.id.poi_detail_address_cell)

                                // Setting info
                                titleView.text = pointOfInterest.title
                                coordinatesView.text = pointOfInterest.geocoordinates
                                if (pointOfInterest.desc.isNotEmpty() && pointOfInterest.desc != "null") {
                                    descriptionView.text = pointOfInterest.desc
                                } else {
                                    descriptionView.visibility = View.GONE
                                    descriptionDivider.visibility = View.GONE
                                }

                                if (pointOfInterest.email.isNotEmpty() && pointOfInterest.email != "null") {
                                    val mailView = mailCellView.findViewById<TextView>(R.id.cell_title)
                                    mailView.text = pointOfInterest.email
                                } else {
                                    mailCellView.visibility = View.GONE
                                }

                                if (pointOfInterest.phone.isNotEmpty() && pointOfInterest.phone != "null") {
                                    val phoneView = phoneCellView.findViewById<TextView>(R.id.cell_title)
                                    phoneView.text = pointOfInterest.phone
                                } else {
                                    phoneCellView.visibility = View.GONE
                                }

                                if (pointOfInterest.transport.isNotEmpty() && pointOfInterest.transport != "null") {
                                    val transportView = transportCellView.findViewById<TextView>(R.id.cell_title)
                                    transportView.text = pointOfInterest.transport
                                } else {
                                    transportCellView.visibility = View.GONE
                                }

                                if (pointOfInterest.address.isNotEmpty() && pointOfInterest.address != "null") {
                                    val addressView = addressCellView.findViewById<TextView>(R.id.cell_title)
                                    addressView.text = pointOfInterest.address
                                } else {
                                    addressCellView.visibility = View.GONE
                                }
                            }
                        }
                    }
                    // Point of Interest not valid
                    else {
                        Toast.makeText(context, "Error: Not a valid Point of Interest.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}