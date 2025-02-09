package com.example.aaroth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aaroth.ui.theme.AarothTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AarothTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun HomePage() {
    val features = listOf(
        "Customer Registration",
        "Customer Data Updation",
        "User Data Registration",
        "User Data Updation",
        "Product/Service Request",
        "Download Reports",
        "Send Reports",
        "Get Customer Details",
        "Basic Summary",
    )
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val buttonSize = screenWidth * 0.25f // 25% of screen width for each button in grid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(buttonSize), // Use Adaptive GridCells
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(features) { feature ->
                FeatureButton(feature) {

                }
            }
        }
    }
}
fun String.removeSpaces(): String {
    return this.replace(" ", "")
}
@Composable
fun FeatureButton(feature: String, onFeatureClick: (String) -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            // Dynamically generate activity class name
            val activityClassName = feature.removeSpaces()

            // Attempt to get the activity class
            try {
                val activityClass = Class.forName("com.example.aaroth.$activityClassName")
                val intent = Intent(context, activityClass)
                context.startActivity(intent)
            } catch (e: ClassNotFoundException) {
                // Handle the case where the activity class is not found
                // You might want to log an error or show a message to the user
                Log.e("FeatureButton", "Activity class not found: $activityClassName", e)
                Toast.makeText(context, "Feature not implemented yet", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(feature, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AarothTheme {
        HomePage()
    }
}
