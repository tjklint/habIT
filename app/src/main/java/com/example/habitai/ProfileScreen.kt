package com.example.habitai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.example.habitai.ui.theme.HabITAITheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val navController = LocalNavController.current
    val CustomFontFamily = FontFamily(Font(R.font.yomogi, FontWeight.Bold))
    val CustomFontFamily2 = FontFamily(Font(R.font.juliusone, FontWeight.Bold))
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    // State variables to hold profile data
    var username by remember { mutableStateOf("") }
    var createdAt by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var moodLevel by remember { mutableStateOf(3f) }

    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        username = document.getString("username") ?: "N/A"
                        val timestamp = document.get("createdAt") as? com.google.firebase.Timestamp
                        createdAt = timestamp?.toDate()?.let {
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(it)
                        } ?: "N/A"
                        moodLevel = (document.getDouble("moodLevel") ?: 3.0).toFloat()
                        loading = false
                    }
                }
        } else {
            loading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
    ) {
        TopAppBar(
            title = {
                Text(text = "HabitAI", color = Color.Black, fontSize = 20.sp, fontFamily = CustomFontFamily)
            },
            actions = {
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.sun),
                        contentDescription = "Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5A234)),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            if (loading) {
                Text(
                    text = "Loading profile...",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontFamily = CustomFontFamily2,
                    fontSize = 16.sp
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFFFE082),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Your Profile",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontFamily = CustomFontFamily2,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Username: $username", fontFamily = CustomFontFamily2)
                    Text(text = "Created at: $createdAt", fontFamily = CustomFontFamily2)
                    Text(
                        text = "AI Mood Level",
                        fontFamily = CustomFontFamily2,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Slider(
                        value = moodLevel,
                        onValueChange = { newMoodLevel ->
                            moodLevel = newMoodLevel
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                db.collection("users").document(currentUser.uid)
                                    .update("moodLevel", newMoodLevel)
                            }
                        },
                        valueRange = 1f..5f,
                        steps = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = when (moodLevel.toInt()) {
                            1 -> "Very Nice"
                            2 -> "Nice"
                            3 -> "Neutral"
                            4 -> "Mean"
                            5 -> "Very Mean"
                            else -> "Unknown"
                        },
                        fontFamily = CustomFontFamily2,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("home_screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFFFFE082), shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD97A))
            ) {
                Text(text = "HOME", color = Color.Black, fontFamily = CustomFontFamily2)
            }
        }
    }
}
