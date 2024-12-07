package com.example.habitai
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.habitai.ui.theme.HabITAITheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = LocalNavController.current
    Column(modifier = Modifier.fillMaxSize()) {

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

        Spacer(modifier = Modifier.height(40.dp))

        // Main Buttons Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // "Add Tasks" Button
            ButtonWithImage(text = "Add Tasks", imageRes = R.drawable.smiling_sun,"taskmanager_screen",navController)

            // "View Calendar" Button
            ButtonWithImage(text = "View Calendar", imageRes = R.drawable.calendar,"calendar_screen",navController)

            // "View Profile" Button
            ButtonWithImage(text = "View Profile", imageRes = R.drawable.profile,"profile_screen",navController)

            ButtonWithImage(text = "Improve Routine", imageRes = R.drawable.smiling,"task_screen",navController)
        }
    }
}

@Composable
fun ButtonWithImage(text: String, imageRes: Int,navigation:String,navController: NavController) {
    Button(
        onClick = {navController.navigate(navigation) },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD97A))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = text,
                modifier = Modifier.size(48.dp).padding(end = 16.dp)
            )
            Text(text = text, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HabITAITheme {
        HomeScreen()
    }
}