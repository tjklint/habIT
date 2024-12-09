package com.example.habitai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.habitai.ui.theme.HabITAITheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.ZoneId


//val customFontFamily = FontFamily(
    //Font(R.font.yomogi)
//)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = Firebase.auth
    val db = Firebase.firestore
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
        Text(text = "Login", fontSize = 40.sp,fontFamily = CustomFontFamily)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username",fontFamily = CustomFontFamily,fontSize = 25.sp) },
            modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Black),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFFD97A),
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password",fontFamily = CustomFontFamily,fontSize = 25.sp) },
            modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Black),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFFD97A),
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    val email = "$username@habit.com"

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid = task.result?.user?.uid
                                uid?.let {
                                    val userDoc = db.collection("users").document(it)

                                    userDoc.get().addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            val storedUsername = document.getString("username")
                                            val lastLoginTimestamp = document.getTimestamp("lastLogin")

                                            if (storedUsername == username) {
                                                // Get current date
                                                val currentDate = LocalDate.now()
                                                val lastLoginDate = lastLoginTimestamp?.toDate()?.toInstant()
                                                    ?.atZone(ZoneId.systemDefault())?.toLocalDate()

                                                if (lastLoginDate == null || lastLoginDate.isBefore(currentDate)) {
                                                    // Reset tasksCompleted state to false for new day
                                                    db.collection("tasks")
                                                        .whereEqualTo("userId", uid)
                                                        .get()
                                                        .addOnSuccessListener { tasks ->
                                                            for (taskDocument in tasks) {
                                                                val taskId = taskDocument.id
                                                                db.collection("tasks").document(taskId)
                                                                    .update("completed", false)
                                                                    .addOnSuccessListener {
                                                                        // Successfully updated
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        // Handle error, e.g., log it
                                                                    }
                                                            }
                                                        }
                                                    // Update the login timestamp
                                                    userDoc.update(
                                                        mapOf(
                                                            "lastLogin" to FieldValue.serverTimestamp()
                                                        )
                                                    )
                                                } else {
                                                    // Update only the login timestamp for the same day
                                                    userDoc.update("lastLogin", FieldValue.serverTimestamp())
                                                }

                                                Toast.makeText(
                                                    context,
                                                    "Login Successful!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate("home_screen")
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Incorrect Password or Username",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Incorrect Password or Username",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(26.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD97A),
                contentColor = Color.Black
            )
        ) {
            Text(text = "Login", fontFamily = CustomFontFamily, fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                navController.navigate("register_screen")
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
fun LoginScreenPreview() {
    HabITAITheme{
        LoginScreen()
    }
}