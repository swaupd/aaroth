@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aaroth
import android.content.Intent
import androidx.compose.ui.res.painterResource
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aaroth.ui.theme.AarothTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.clickable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


class CustomerRegistration : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AarothTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomerRegistrationContent()
                }
            }
        }
    }
}

data class CustomerFormData(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val dob: String = "",
    val gender: String = "",
    val motherTongue: String = "",
    val preferredLanguage: String = "",
    val nationality: String = "",
    val diet: String = "",
    val city: String = "",
    val district: String = "",
    val state: String = "",
    val streetName: String = "",
    val areaLocation: String = "",
    val phoneType: Boolean = false,
    val guardianFirstName: String = "",
    val guardianMiddleName: String = "",
    val guardianLastName: String = "",
    val phoneNumber: String = ""
)

interface ApiService {
    @POST("register")
    suspend fun registerCustomer(@Body customerData: CustomerFormData): retrofit2.Response<Map<String, String>>
}

@Composable
private fun CustomerRegistrationContent() {
    var formData by remember { mutableStateOf(CustomerFormData()) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current as ComponentActivity
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    // Create Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.193.75:5000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
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
                .padding(16.dp)
                .verticalScroll(scrollState),

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
                text = "Customer Registration",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0x99FFFFFF), shape = RoundedCornerShape(35.dp))
                    .border(BorderStroke(3.dp, Color(0x40000000)), shape = RoundedCornerShape(35.dp))
                    .padding(20.dp),
            ) {
                // Personal Information
                Text(
                    "First Name:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.firstName,
                    onValueChange = { formData = formData.copy(firstName = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))


                )

                Text(
                    "Middle Name (if any):",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                    )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.middleName,
                    onValueChange = { formData = formData.copy(middleName = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "Last Name:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 15.sp),
                    value = formData.lastName,
                    onValueChange = { formData = formData.copy(lastName = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )
                Text(
                    "Date of Birth:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.dob,
                    onValueChange = { }, // Read-only
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                        .height(45.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp)),
                    enabled = false
                )
                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = try {
                            LocalDate.parse(formData.dob, dateFormatter)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()
                        } catch (e: Exception) {
                            System.currentTimeMillis()
                        }
                    )

                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val localDate = Instant.ofEpochMilli(millis)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                    formData = formData.copy(dob = localDate.format(dateFormatter))
                                }
                                showDatePicker = false
                            }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                var genderExpanded by remember { mutableStateOf(false) }
                val genderOptions = listOf("Male", "Female", "Other")
                Text(
                    "Gender:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = genderExpanded,
                    onExpandedChange = { genderExpanded = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                ) {

                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 14.sp),
                        value = formData.gender,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) }


                    )
                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false }
                    ) {
                        genderOptions.forEach { gender ->
                            DropdownMenuItem(
                                text = { Text(gender) },
                                onClick = {
                                    formData = formData.copy(gender = gender)
                                    genderExpanded = false
                                }
                            )
                        }
                    }
                }

                Text(
                    "Mother Tongue:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.motherTongue,
                    onValueChange = { formData = formData.copy(motherTongue = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "Preferred Language:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.preferredLanguage,
                    onValueChange = { formData = formData.copy(preferredLanguage = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "Nationality:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.nationality,
                    onValueChange = { formData = formData.copy(nationality = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "Diet Preference:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.diet,
                    onValueChange = { formData = formData.copy(diet = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                // Address Fields
                Text(
                    "Street Name:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                    )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.streetName,
                    onValueChange = { formData = formData.copy(streetName = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "Area Location:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                    )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.areaLocation,
                    onValueChange = { formData = formData.copy(areaLocation = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "City:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                    )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.city,
                    onValueChange = { formData = formData.copy(city = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "District:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                    )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.district,
                    onValueChange = { formData = formData.copy(district = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                Text(
                    "State:",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                    )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.state,
                    onValueChange = { formData = formData.copy(state = it) },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x90D9D9D9))
                    .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )

                // Phone Information
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = !formData.phoneType,
                        onClick = { formData = formData.copy(phoneType = false) }
                    )
                    Text("Personal Phone No.")
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = formData.phoneType,
                        onClick = { formData = formData.copy(phoneType = true) }
                    )
                    Text("Guardian's Phone No.")
                }

                if (formData.phoneType) {
                    Text(
                        "Guardian First Name:",
                        color = Color(0x99000000),
                        modifier = Modifier
                            .padding(4.dp)
                        )
                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 14.sp),
                        value = formData.guardianFirstName,
                        onValueChange = { formData = formData.copy(guardianFirstName = it) },
                        modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                    )

                    Text(
                        "Guardian Middle Name:",
                        color = Color(0x99000000),
                        modifier = Modifier
                            .padding(4.dp)
                        )
                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 14.sp),
                        value = formData.guardianMiddleName,
                        onValueChange = { formData = formData.copy(guardianMiddleName = it) },
                        modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                    )

                    Text(
                        "Guardian Last Name:",
                        color = Color(0x99000000),
                        modifier = Modifier
                            .padding(4.dp)
                        )
                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 14.sp),
                        value = formData.guardianLastName,
                        onValueChange = { formData = formData.copy(guardianLastName = it) },
                        modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                    )
                }

                Text(
                    if (formData.phoneType) "Guardian's Phone Number" else "Personal Phone Number",
                    color = Color(0x99000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            formData = formData.copy(phoneNumber = it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))
                )




            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
                border = BorderStroke(3.dp, Color.Black),
                onClick = {
                    scope.launch {
                        try {
                            val response = apiService.registerCustomer(formData)
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Registration successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                context.finish()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Registration failed: ${
                                        response.errorBody()?.string() ?: "Unknown error"
                                    }",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Error: ${e.message ?: "Unknown error"}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Submit", color = Color.Black)

            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AarothTheme {
        DownloadReportsScreen() // Enable preview mode
    }
}
