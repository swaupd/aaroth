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
import androidx.compose.foundation.clickable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

class UserRegistration : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AarothTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserRegistrationContent()
                }
            }
        }
    }
}

data class UserFormData(
    val username: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = ""
)

interface UserApiService {
    @POST("register_user")
    suspend fun registerUser(@Body userData: UserFormData): retrofit2.Response<Map<String, String>>
}

@Composable
private fun UserRegistrationContent() {
    var formData by remember { mutableStateOf(UserFormData()) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current as ComponentActivity
    // Create Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.193.75:5000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(UserApiService::class.java)
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
                    .padding(start = 140.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
            )

            Text(
                text = "User Registration",
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
                Text(
                    "Username:",
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.username,
                    onValueChange = { formData = formData.copy(username = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))


                )
                Text(
                    "Email:",
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.email,
                    onValueChange = { formData = formData.copy(email = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))


                )
                // Personal Information
                Text(
                    "First Name:",
                    color = Color(0xFF000000),
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
                    "Last Name:",
                    color = Color(0xFF000000),
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
                    "Password:",
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = formData.password,
                    onValueChange = { formData = formData.copy(password = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0x90D9D9D9))
                        .border(BorderStroke(3.dp, Color(0x66000000)), shape = RoundedCornerShape(15.dp))


                )
                var confirmPassword by remember { mutableStateOf("") }
                Text(
                    "Confirm Password:",
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(4.dp)
                )
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 14.sp),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
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
                            val response = apiService.registerUser(formData)
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
        UserRegistrationContent() // Enable preview mode
    }
}
