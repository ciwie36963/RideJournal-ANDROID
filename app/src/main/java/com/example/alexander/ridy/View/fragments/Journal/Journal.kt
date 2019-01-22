package com.example.alexander.ridy.View.fragments.Journal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.alexander.ridy.Model.adapters.RideAdapter
import com.example.alexander.ridy.R
import com.example.alexander.ridy.ViewModel.RideViewModel
import kotlinx.android.synthetic.main.fragment_journal.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A Fragment is a section of an Activity, which has its own lifecycle, receives its own input events, can be added or removed while the Activity is running.
 * This fragment takes care of representing the journal containing the rides
 * @property viewManager responsible for measuring and positioning item views within a RecyclerView as well as determining the policy for when to recycle item views that are no longer visible to the user
 * @property rideViewModel a model which holds temporary data of a ride
 * @property rideAdapter the rideAdapter forms a bridge between the UI components and the data source that fill data into the UI Component
 * @property recyclerView a UI element which presents data
 */
class Journal : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var rideViewModel: RideViewModel
    lateinit var rideAdapter : RideAdapter
    private lateinit var recyclerView : RecyclerView

    /**
     * This method is used to store and restore information about the state of your activity.
     * In cases such as orientation changes, closing the app or any other scenario that leads to onCreate() being recalled,
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rideViewModel = ViewModelProviders.of(activity!!).get(RideViewModel::class.java)

        rideViewModel.allRides.observe(activity!!, Observer{
        rideAdapter = RideAdapter(rideViewModel.allRides.value!!)
        })
    }

    /**
     * onCreateView is called to "inflate" the layout of the fragment,
     * i.e. graphic initialization is usually done here.
     * It is always called after the onCreate method.
     * @param inflater the attr that handles the inflation of the fragment
     * @param container attr that forms a container for the data to be displayed
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     * @return function returns a view, this will be displayed on the screen
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_journal, container, false)
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        return view
    }

    /**
     * If the app continues after the onPause method is called, the onResume method is called.
     * It is one of the methods of the activity life cycle.
     * The function makes sure you can delete an item by swiping to the left or to the right and notifies the user with a toast
     */
    override fun onResume() {
        super.onResume()

        viewManager = LinearLayoutManager(this@Journal.context)
        rideViewModel.allRides.observe(activity!!, Observer{
            recyclerView.adapter = RideAdapter(rideViewModel.allRides.value!!)
        })
        recycler_view.layoutManager = viewManager

        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {

                        AsyncTask.execute {
                        rideViewModel.delete(rideAdapter.getRideAt(position))
                        }

                        Toast.makeText(activity!!.applicationContext,"Ride Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     */
    companion object {
        /**
         * Function returns an instance of a Journal fragment
         * @return the journal instance
         */
        fun newInstance(): Journal {
            return Journal()
        }
    }
}