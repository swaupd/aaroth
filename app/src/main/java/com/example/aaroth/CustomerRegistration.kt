@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aaroth
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
import com.example.aaroth.ui.theme.AarothTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
                    value = formData.firstName,
                    onValueChange = { formData = formData.copy(firstName = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))


                )

                OutlinedTextField(
                    value = formData.middleName,
                    onValueChange = { formData = formData.copy(middleName = it) },
                    label = { Text("Middle Name (if any)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.lastName,
                    onValueChange = { formData = formData.copy(lastName = it) },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.dob,
                    onValueChange = { }, // Read-only
                    label = { Text("Date of Birth") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
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

                ExposedDropdownMenuBox(
                    expanded = genderExpanded,
                    onExpandedChange = { genderExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formData.gender,
                        onValueChange = {},
                        label = { Text("Gender") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                        modifier = Modifier.menuAnchor()
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

                OutlinedTextField(
                    value = formData.motherTongue,
                    onValueChange = { formData = formData.copy(motherTongue = it) },
                    label = { Text("Mother Tongue") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.preferredLanguage,
                    onValueChange = { formData = formData.copy(preferredLanguage = it) },
                    label = { Text("Preferred Language") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.nationality,
                    onValueChange = { formData = formData.copy(nationality = it) },
                    label = { Text("Nationality") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.diet,
                    onValueChange = { formData = formData.copy(diet = it) },
                    label = { Text("Diet Preference") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Address Fields
                OutlinedTextField(
                    value = formData.streetName,
                    onValueChange = { formData = formData.copy(streetName = it) },
                    label = { Text("Street Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.areaLocation,
                    onValueChange = { formData = formData.copy(areaLocation = it) },
                    label = { Text("Area Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.city,
                    onValueChange = { formData = formData.copy(city = it) },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.district,
                    onValueChange = { formData = formData.copy(district = it) },
                    label = { Text("District") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formData.state,
                    onValueChange = { formData = formData.copy(state = it) },
                    label = { Text("State") },
                    modifier = Modifier.fillMaxWidth()
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
                    OutlinedTextField(
                        value = formData.guardianFirstName,
                        onValueChange = { formData = formData.copy(guardianFirstName = it) },
                        label = { Text("Guardian First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = formData.guardianMiddleName,
                        onValueChange = { formData = formData.copy(guardianMiddleName = it) },
                        label = { Text("Guardian Middle Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = formData.guardianLastName,
                        onValueChange = { formData = formData.copy(guardianLastName = it) },
                        label = { Text("Guardian Last Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = formData.phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            formData = formData.copy(phoneNumber = it)
                        }
                    },
                    label = { Text(if (formData.phoneType) "Guardian's Phone Number" else "Personal Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
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
                        .padding(vertical = 16.dp)
                ) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AarothTheme {
        CustomerRegistrationContent() // Enable preview mode
    }
}