package com.example.habitai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.habitai.ui.theme.HabITAITheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate


//val customFontFamily = FontFamily(
//Font(R.font.yomogi)
//)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val CustomFontFamily = FontFamily(
        Font(R.font.yomogi, FontWeight.Bold)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5A234)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val navController = LocalNavController.current
        Text(text = "Register", fontSize = 40.sp,fontFamily = CustomFontFamily)

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    //dummy email format for Firebase Authentication
                    val email = "$username@habit.com"

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid = task.result?.user?.uid
                                //save the username and UID in Firestore
                                if (uid != null) {
                                    val userData = hashMapOf(
                                        "username" to username,
                                        "uid" to uid,
                                        "createdAt" to com.google.firebase.Timestamp.now(),
                                        "tasks" to emptyList<Map<String, Any>>()
                                    )
                                    db.collection("users").document(uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,
                                                "Registration Successful!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.navigate("login_screen")
                                        }
                                }
                            }
                            else {
                                Toast.makeText(
                                    context,
                                    "Username already in use",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(26.dp)).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD97A),
                contentColor = Color.Black
            )

        ) {
            Text(text = "Register",fontFamily = CustomFontFamily,fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                navController.navigate("login_screen")
            },
            modifier = Modifier.border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(26.dp)).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD97A),
                contentColor = Color.Black
            )

        ) {
            Text(text = "Login",fontFamily = CustomFontFamily,fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Image(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = "Sun",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    HabITAITheme{
        RegisterScreen()
    }
}