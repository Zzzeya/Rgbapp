package com.example.rgbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.view.View
import androidx.core.graphics.toColorInt
import com.google.android.material.textfield.TextInputEditText
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    // 1. Declare the view variables.
    // The type must match the view in your XML.
    private lateinit var redEditText: TextInputEditText
    private lateinit var greenEditText: TextInputEditText
    private lateinit var blueEditText: TextInputEditText
    private lateinit var createColorButton: Button
    private lateinit var colorDisplayView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 2. Set the content view to your layout.
        setContentView(R.layout.activity_main)

        // 3. Initialize the views by finding them by their ID.
        // This is the most critical part where crashes usually occur.
        // Ensure the ID exactly matches the one in activity_main.xml.
        redEditText = findViewById(R.id.redEditText)
        greenEditText = findViewById(R.id.greenEditText)
        blueEditText = findViewById(R.id.blueEditText)
        createColorButton = findViewById(R.id.createColorButton)
        colorDisplayView = findViewById(R.id.colorDisplayView)

        // 4. Set the onClickListener for the button.
        createColorButton.setOnClickListener {
            // Get the text from the input fields.
            val red = redEditText.text.toString().trim()
            val green = greenEditText.text.toString().trim()
            val blue = blueEditText.text.toString().trim()

            // Validate that each input is a valid 2-character hexadecimal string.
            if (isHexValid(red) && isHexValid(green) && isHexValid(blue)) {
                // If all inputs are valid, create the full hexadecimal color string.
                val hexColor = "#$red$green$blue"
                try {
                    // Attempt to parse the hex string into a Color object using the KTX extension function.
                    val color = hexColor.toColorInt()
                    // Set the background color of the display view.
                    colorDisplayView.setBackgroundColor(color)
                    // Show a success message.
                    Toast.makeText(this, "Color created: $hexColor", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    // Handle potential parsing errors.
                    Toast.makeText(this, "Failed to create color. Check your inputs.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If any input is invalid, show an error message.
                Toast.makeText(this, "Please enter 2 valid hex characters for each channel.", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Checks if a string is a valid 2-character hexadecimal value.
     */
    private fun isHexValid(hexString: String): Boolean {
        // A valid hex string must be exactly 2 characters long.
        if (hexString.length != 2) {
            return false
        }
        // Use a regular expression to check for hex characters (0-9, a-f, A-F).
        return hexString.matches("[0-9a-fA-F]{2}".toRegex())
    }
}
