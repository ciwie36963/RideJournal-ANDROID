package com.example.alexander.ridy.View.fragments.More

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alexander.ridy.R
import kotlinx.android.synthetic.main.fragment_more.*

/**
 * A Fragment is a section of an Activity, which has its own lifecycle, receives its own input events, can be added or removed while the Activity is running.
 * This fragment takes care of representing the more screen with some general info and a button to send eventual complaints
 */
class More : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        return view
    }

    /**
     * If the app continues after the onPause method is called, the onResume method is called.
     * It is one of the methods of the activity life cycle.
     * The function makes sure you can send an email when pressing the btnMail button
     */
    override fun onResume() {
        super.onResume()

        btnMail.setOnClickListener() {
            sendEmail()
        }
    }

    /**
     * Function that makes the user able to send a complaint using mail
     */
    protected fun sendEmail() {
        val TO = arrayOf("alexander1@hotmail.com")
        val CC = arrayOf("alexander2@gmail.com")

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
        emailIntent.putExtra(Intent.EXTRA_CC, CC)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here")

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail"))
        } catch (ex: android.content.ActivityNotFoundException) {
        }
    }

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     */
    companion object {

        /**
         * Function returns an instance of a More fragment
         * @return a more instance
         */
        fun newInstance(): More {
            return More()
        }
    }

}