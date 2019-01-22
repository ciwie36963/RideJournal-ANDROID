package com.example.alexander.ridy.Model.extensions

/**
 * Class that has a certain function to check input values
 */
class InputRegex {
    companion object {
        /**
         * Function to check input values and determines if it matches the pattern or not
         * @param input the input from the UI
         */
        fun controleerVeld(input: String) : Boolean = "^[0-9]\\d*(\\.\\d+)?$".toRegex().matches(input)
    }
}