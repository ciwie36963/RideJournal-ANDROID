package com.example.alexander.ridy.Model.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexander.ridy.Model.domain.Ride
import com.example.alexander.ridy.R
import kotlinx.android.synthetic.main.ride_row.view.*

/**
 * RideAdapter forms a bridge between the UI components and the data source that fill data into the UI Component
 * @param rides The list of rides which will be displayed in the journal fragment
 */
class RideAdapter(private val rides: List<Ride>) : RecyclerView.Adapter<RideAdapter.RideHolder>() {

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView
     * @property distanceTextView The TextView which will display the distance
     * @property vehicleTypeTextView The TextView which will display the vehicle
     * @property moneySavedTextView The TextView which will display the money saved
     * @property rideToWorkTextView The TextView which will display the ride to work
     * @constructor Creates the connection between the textView in this class and the layout element
     */
    class RideHolder : RecyclerView.ViewHolder {
        var distanceTextView : TextView
        var vehicleTypeTextView : TextView
        var moneySavedTextView : TextView
        var rideToWorkTextView : TextView

        constructor(itemView: View) : super(itemView) {
            this.distanceTextView = itemView.findViewById(R.id.distance)
            this.vehicleTypeTextView = itemView.findViewById(R.id.vehicleType)
            this.moneySavedTextView = itemView.findViewById(R.id.moneySaved)
            this.rideToWorkTextView = itemView.findViewById(R.id.rideToWork)
        }
    }

    /**
     * This method is called right when the adapter is created and is used to initialize your ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.ride_row, parent, false)
        return RideHolder(textView)
    }

    /**
     * Function which has the size of the rides list
     */
    override fun getItemCount() = rides.size

    /**
     * Function which binds the correct values to the correct textViews
     * @param holder The rider holder, it "holds" a ride
     * @param position The position in the ride list
     */
    override fun onBindViewHolder(holder: RideHolder, position: Int) {
        holder.distanceTextView.text = (String.format("%.2f",rides[position].distanceRide / 1000) + " KM")
        holder.vehicleTypeTextView.text = rides[position].vehicle.toString()
        holder.moneySavedTextView.text = (String.format("%.2f",rides[position].moneySaved) + " EURO")
        holder.rideToWorkTextView.text = rides[position].rideToWork.toString()
    }

    /**
     * Function which retrieves a ride at a certain position
     * @param position The position in the list
     */
    fun getRideAt(position: Int) : Ride {
        return rides.get(position)
    }

}