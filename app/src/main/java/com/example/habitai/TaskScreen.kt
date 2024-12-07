package com.example.habitai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val placeholderPrompt = "Ask about tasks, routines, or discipline..."
    val placeholderResult = "Your response will appear here."
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    val uiState by taskViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5A234))
            .padding(16.dp)
    ) {
        Text(
            text = "Improve Your Routine",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = { Text(text = "Your Query", color = Color.Black) },
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFFFD97A),
                    cursorColor = Color.Blue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { taskViewModel.sendPrompt(prompt) },
                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD97A),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Ask")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color(0xFFFFD97A))
                .padding(16.dp)
        ) {
            when (uiState) {
                is UiState.Loading -> CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
                is UiState.Error -> {
                    result = (uiState as UiState.Error).errorMessage
                    Text(
                        text = result,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
                is UiState.Success -> {
                    result = (uiState as UiState.Success).outputText
                    Text(
                        text = result,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
                else -> {
                    Text(
                        text = placeholderResult,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.smiling_sun),
            contentDescription = "Footer Image",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
