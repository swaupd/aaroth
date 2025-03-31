package com.example.aaroth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aaroth.ui.theme.AarothTheme
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import java.io.File
import java.io.FileOutputStream
import org.json.JSONObject

interface ReportApi {
    @Headers("Content-Type: application/json")
    @POST("generate_report")
    suspend fun generateReport(@Body requestBody: okhttp3.RequestBody): ResponseBody
}

class DownloadReportViewModel : ViewModel() {
    private val api: ReportApi
    private val TAG = "DownloadReportViewModel"

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                Log.d(TAG, "Sending request: ${request.url}")
                val response = chain.proceed(request)
                Log.d(TAG, "Received response: ${response.code}")
                response
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.193.75:5000/")
            .client(client)
            .build()

        api = retrofit.create(ReportApi::class.java)
    }

    fun downloadReport(
        phoneNumber: String,
        context: Context,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val jsonObject = JSONObject().apply {
                    put("phoneNumber", phoneNumber)
                }
                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json".toMediaType())

                Log.d(TAG, "Starting download for phone number: $phoneNumber")
                val response = api.generateReport(requestBody)

                // Log response details
                Log.d(TAG, "Response content length: ${response.contentLength()}")
                Log.d(TAG, "Response content type: ${response.contentType()}")

                // Create downloads directory if it doesn't exist
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                downloadsDir.mkdirs()

                val file = File(downloadsDir, "${phoneNumber}_latest_report.pdf")
                Log.d(TAG, "Saving file to: ${file.absolutePath}")

                // Write the file
                try {
                    FileOutputStream(file).use { outputStream ->
                        response.byteStream().use { inputStream ->
                            val buffer = ByteArray(4096)
                            var bytesRead: Int
                            var totalBytes = 0
                            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                outputStream.write(buffer, 0, bytesRead)
                                totalBytes += bytesRead
                            }
                            Log.d(TAG, "Total bytes written: $totalBytes")
                        }
                    }

                    if (file.exists() && file.length() > 0) {
                        Log.d(TAG, "File saved successfully, size: ${file.length()} bytes")
                        onSuccess(file.absolutePath)
                    } else {
                        throw Exception("File was not created or is empty")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error writing file", e)
                    throw e
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error in downloadReport", e)
                onError(e.message ?: "Unknown error occurred")
            }
        }
    }
}

class DownloadReports : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DownloadReportsScreen()
                }
            }
        }
    }
}

@Composable
fun DownloadReportsScreen() {
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var filePath by remember { mutableStateOf<String?>(null) }

    val viewModel = remember { DownloadReportViewModel() }
    val context = LocalContext.current
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

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(start = 120.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
            )

            Text(
                text = "Generate Customer Report",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0x99FFFFFF), shape = RoundedCornerShape(35.dp))
                    .border(
                        BorderStroke(3.dp, Color(0x40000000)),
                        shape = RoundedCornerShape(35.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Customer Phone Number:",
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp)),
                    enabled = !isLoading
                )

            }
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
                border = BorderStroke(3.dp, Color.Black),
                onClick = {
                    if (phoneNumber.isBlank()) {
                        showError = true
                        errorMessage = "Please enter a phone number"
                        return@OutlinedButton
                    }

                    isLoading = true
                    showError = false
                    filePath = null

                    viewModel.downloadReport(
                        phoneNumber = phoneNumber,
                        context = context,
                        onSuccess = { path ->
                            isLoading = false
                            filePath = path
                            Toast.makeText(
                                context,
                                "Report downloaded to Downloads folder",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onError = { error ->
                            isLoading = false
                            showError = true
                            errorMessage = error
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.Black
                    )
                } else {
                    Text("Download Report", color = Color.Black)
                }
            }

            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            filePath?.let { path ->
                Text(
                    text = "File saved to:\n$path",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }


    }
}
