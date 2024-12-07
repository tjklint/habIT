package com.example.habitai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFCC66)),
            verticalAlignment = Alignment.CenterVertically
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("M", "T", "W", "TH", "F", "S", "SU").forEach { day ->
                Text(text = day, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFCC66))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(7) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .border(1.dp, Color.Black)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* Handle Delete */ }, colors = ButtonDefaults.buttonColors(Color(0xFFFFCC66))) {
                Text("DELETE")
            }
            Button(onClick = { /* Handle Home */ }, colors = ButtonDefaults.buttonColors(Color(0xFFFFCC66))) {
                Text("HOME")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Add */ },
            colors = ButtonDefaults.buttonColors(Color(0xFFFFCC66)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("ADD")
        }
    }
}