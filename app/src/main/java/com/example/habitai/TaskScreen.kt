package com.example.habitai

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.habitai.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val uiState by taskViewModel.uiState.collectAsState()
    var prompt by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("Your response will appear here.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5A234))
            .padding(16.dp)
    ) {
        Text(
            text = "Ask About Your Tasks",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Input field for user query
        TextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text(text = "Your Query", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFFD97A),
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = { taskViewModel.fetchAndSendPrompt(prompt) },
            enabled = prompt.isNotEmpty(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Ask AI")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .background(Color(0xFFFFD97A))
                .padding(16.dp)
        ) {
            when (uiState) {
                is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is UiState.Error -> {
                    val errorMessage = (uiState as UiState.Error).errorMessage
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    result = (uiState as UiState.Success).outputText
                    Text(text = result, color = Color.Black, modifier = Modifier.align(Alignment.TopStart))
                }
                else -> {
                    Text(text = result, color = Color.Gray, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
