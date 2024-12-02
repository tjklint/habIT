package com.example.habitai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitai.ui.theme.HabITAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerScreen() {
    val navController = LocalNavController.current
    var description by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
    ) {
        TopAppBar(
            title = {
                Text(text = "HabitAI", color = Color.Black, fontSize = 20.sp)
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
                    text = "Add Task",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFFFFFFF),
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFFFFFFF),
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

            }

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFFFFE082), shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD97A))
            ) {
                Text(text = "ADD", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    title=""
                    description=""
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFFFFE082), shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD97A))
            ) {
                Text(text = "RESET", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { navController.navigate("home_screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFFFFE082), shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD97A))
            ) {
                Text(text = "HOME", color = Color.Black)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TaskManagerPreview() {
    HabITAITheme {
        TaskManagerScreen()
    }
}