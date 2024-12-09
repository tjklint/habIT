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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCheckScreen() {
    val navController = LocalNavController.current
    val CustomFontFamily = FontFamily(Font(R.font.yomogi, FontWeight.Bold))
    val CustomFontFamily2 = FontFamily(Font(R.font.juliusone, FontWeight.Bold))
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth

    // State to hold fetched tasks
    var tasks by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var loading by remember { mutableStateOf(true) }

    // Fetch tasks when the screen loads
    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("tasks")
                .whereEqualTo("userId", currentUser.uid)
                .get()
                .addOnSuccessListener { result ->
                    tasks = result.documents.map { document ->
                        val data = document.data ?: emptyMap()
                        data + mapOf("id" to document.id) // Add document ID to data
                    }
                    loading = false
                }
                .addOnFailureListener {
                    loading = false
                }
        } else {
            loading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "HabitAI", color = Color.Black, fontFamily = CustomFontFamily) },
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
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ADD YOUR MORNING TASKS",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily2
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (loading) {
            Text(
                text = "Loading tasks...",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            tasks.forEach { task ->
                val taskId = task["id"] as? String // Get the document ID

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var isChecked by remember { mutableStateOf(task["completed"] as? Boolean ?: false) }

                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checked ->
                            isChecked = checked
                            // Update the `completed` field in Firestore
                            if (taskId != null) {
                                db.collection("tasks").document(taskId)
                                    .update("completed", checked)
                                    .addOnSuccessListener {
                                        // Successfully updated
                                    }
                                    .addOnFailureListener { e ->
                                        // Handle error, e.g., show a toast or log
                                    }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Title: ${task["taskName"] ?: ""}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CustomFontFamily2
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Description: ${task["description"] ?: ""}",
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontFamily = CustomFontFamily2
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            // Delete the task from Firestore
                            if (taskId != null) {
                                db.collection("tasks").document(taskId)
                                    .delete()
                                    .addOnSuccessListener {
                                        // Successfully deleted
                                        tasks = tasks.filter { it["id"] != taskId }
                                    }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sun),
                            contentDescription = "Delete Task",
                            tint = Color.Red
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("home_screen") },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFC107)),
                    modifier = Modifier.padding(start = 23.dp)
                ) {
                    Text(
                        text = "HOME",
                        color = Color.Black,
                        fontFamily = CustomFontFamily2,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}