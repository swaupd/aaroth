package com.example.aaroth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.paint
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
        "Device Registration Request",
        "Login Pin Reset",
        "Update Customer Data",
        "Customer Registration",
        "User Registration",
        "Download Reports",
        "ID Deactivation",
        "Information",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.bg),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(start = 120.dp)
            )

            Text(
                text = "Hello User!",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(start = 20.dp)
                    .align(Alignment.Start)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f) // This allows scrolling
            ) {
                items(features) { feature ->
                    FeatureButton(feature) { }
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
    val drawableName = feature.lowercase().replace(" ", "")
    val drawableId = context.resources.getIdentifier(drawableName, "drawable", context.packageName)

    if (drawableId != 0) {
        Image(
            painter = painterResource(drawableId),
            contentDescription = feature,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clickable {
                    try {
                        val activityClass = Class.forName("com.example.aaroth.${feature.removeSpaces()}")
                        val intent = Intent(context, activityClass)
                        context.startActivity(intent)
                    } catch (e: ClassNotFoundException) {
                        Log.e("FeatureButton", "Activity class not found: ${feature.removeSpaces()}", e)
                        Toast.makeText(context, "Feature not implemented yet", Toast.LENGTH_SHORT).show()
                    }
                }
        )
    } else {
        Log.e("FeatureButton", "Drawable not found: $drawableName")
        Toast.makeText(context, "Image not found for $feature", Toast.LENGTH_SHORT).show()
    }
}


