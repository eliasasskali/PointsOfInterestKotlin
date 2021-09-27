package com.worldline.pointsofinterest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.worldline.pointsofinterest.data.PointsOfInterestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class POIDetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.poi_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pointOfInterestId = arguments?.get("selectedPointOfInterestId").toString().toInt()
        fetchPointOfInterest(pointOfInterestId)
    }

    private fun fetchPointOfInterest(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val pointOfInterest = PointsOfInterestRepository().fetchPointOfInterestDetail(id)
            launch(Dispatchers.Main) {
                //val mapView = view.findViewById<MapView>(R.id.mapView)
                view?.let {
                    it.apply {
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
                        if (pointOfInterest.desc.isNotEmpty()) {
                            descriptionView.text = pointOfInterest.desc
                        }
                        else {
                            descriptionView.visibility = View.GONE
                            descriptionDivider.visibility = View.GONE
                        }

                        if (pointOfInterest.email.isNotEmpty()) {
                            val mailView = mailCellView.findViewById<TextView>(R.id.cell_title)
                            mailView.text = pointOfInterest.email
                        }
                        else {
                            mailCellView.visibility = View.GONE
                        }

                        if (pointOfInterest.phone.isNotEmpty()) {
                            val phoneView = phoneCellView.findViewById<TextView>(R.id.cell_title)
                            phoneView.text = pointOfInterest.phone
                        }
                        else {
                            phoneCellView.visibility = View.GONE
                        }

                        if (pointOfInterest.transport.isNotEmpty()) {
                            val transportView = transportCellView.findViewById<TextView>(R.id.cell_title)
                            transportView.text = pointOfInterest.transport
                        }
                        else {
                            transportCellView.visibility = View.GONE
                        }

                        if (pointOfInterest.address.isNotEmpty()) {
                            val addressView = addressCellView.findViewById<TextView>(R.id.cell_title)
                            addressView.text = pointOfInterest.address
                        }
                        else {
                            addressCellView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}