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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCheckScreen() {
    // Create a list of bools for checkboxes
    val taskStates = remember { mutableStateListOf(false, false, false, false, false, false) }
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBar(
            title = {
                Text(text = "HabitAI", color = Color.Black)
            },
            actions = {
                IconButton(onClick = { /* Add functionality for icon */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.sun), // replace with your icon
                        contentDescription = "Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5A234)), // Customize the color
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ADD YOUR MORNING TASKS",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        taskStates.forEachIndexed { index, isChecked ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { taskStates[index] = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Title:",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Description:",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    // Reset all checkboxes
                    for (i in taskStates.indices) {
                        taskStates[i] = false
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFC107))
            ) {
                Text(text = "RESET", color = Color.Black)
            }
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFC107))
            ) {
                Text(text = "SAVE", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.navigate("home_screen") },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFC107)),
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "HOME", color = Color.Black)
            }
            Button(
                onClick = { navController.navigate("calendar_screen") },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFC107))
            ) {
                Text(text = "CALENDAR", color = Color.Black)
            }
        }
    }
}