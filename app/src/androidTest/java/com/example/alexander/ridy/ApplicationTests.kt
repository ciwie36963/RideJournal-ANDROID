package com.example.alexander.ridy

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ApplicationTests {

    @Rule
    @JvmField
    val activityRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun startScreenButtonBikeTest() {
        Espresso.onView(ViewMatchers.withId(R.id.btnBike)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    @Test
    fun startScreenButtonCarTest() {
        Espresso.onView(ViewMatchers.withId(R.id.btnCar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun vehicleDetailsScreenWrongGasolineInput(){
        Espresso.onView(ViewMatchers.withId(R.id.btnBike)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fuelUsagePerKmEditText)).perform(ViewActions.typeText("!")).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.travelAllowanceEditText)).perform(ViewActions.typeText("0.15")).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btnSave)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fuelUsagePerKmEditText)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("Foutieve ingave")))
    }

    @Test
    fun vehicleDetailsScreenWrongTravelAllowanceInput(){
        Espresso.onView(ViewMatchers.withId(R.id.btnBike)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.travelAllowanceEditText)).perform(ViewActions.typeText("!")).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.fuelUsagePerKmEditText)).perform(ViewActions.typeText("5.5")).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btnSave)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.travelAllowanceEditText)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("Foutieve ingave")))
    }
}