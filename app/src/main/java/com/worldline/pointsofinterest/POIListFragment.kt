package com.worldline.pointsofinterest

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.worldline.pointsofinterest.data.AndroidNetworkStatusChecker
import com.worldline.pointsofinterest.data.PointsOfInterestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class POIListFragment: Fragment() {
    private lateinit var adapter: POIListAdapter
    val pointsOfInterestRepository: PointsOfInterestRepository by lazy {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val androidNetworkStatusChecker = AndroidNetworkStatusChecker(connectivityManager)
        PointsOfInterestRepository(androidNetworkStatusChecker)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.poi_list_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        adapter = POIListAdapter(mutableListOf()) { poi ->
            val args = bundleOf(
                Pair("selectedPointOfInterestId", poi.id)
            )
            val options = navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
            findNavController().navigate(R.id.poiDetailFragment, args, options)
        }
        recyclerView.adapter = adapter
        fetchPointsOfInterest()

        // SearchBar
        val searchView = view.findViewById<SearchView>(R.id.poi_search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val pointsOfInterestFiltered = context?.let { pointsOfInterestRepository.filterWithText(newText?: "") }
                if (pointsOfInterestFiltered != null) {
                    adapter.updatePOIs(pointsOfInterestFiltered)
                }
                return false
            }
        })

        // Change search bar magnifier icon color
        val searchIcon = view.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        // Change search bar cancel icon color
        val cancelIcon = view.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        // Change search bar text color
        val textView = view.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchPointsOfInterest() {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                val pointsOfInterestResponse = pointsOfInterestRepository.fetchPointsOfInterest()
                launch(Dispatchers.Main) {
                    if (pointsOfInterestResponse.success) {
                        adapter.updatePOIs(pointsOfInterestResponse.pointsOfInterest)
                    }
                    else {
                        Toast.makeText(it, "Network Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}