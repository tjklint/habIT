package com.example.habitai

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyDkSfI0uk52v3-8IFbvJ2afRxvCYQk9Yfc"
    )

    /**
     * Fetch user tasks and name, then send the refined prompt to Gemini.
     */
    fun fetchAndSendPrompt(prompt: String) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            _uiState.value = UiState.Error("User not logged in.")
            return
        }

        _uiState.value = UiState.Loading

        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { userDoc ->
                val email = userDoc.getString("username") ?: "Unknown User"

                db.collection("tasks")
                    .whereEqualTo("userId", currentUser.uid)
                    .get()
                    .addOnSuccessListener { result ->
                        val tasks = result.documents.map { doc ->
                            val taskName = doc.getString("taskName") ?: "Untitled Task"
                            val description = doc.getString("description") ?: "No description"
                            val completed = doc.getBoolean("completed") ?: false
                            Task(taskName, description, completed)
                        }

                        // Refined prompt with user name and tasks
                        val refinedPrompt = """
                            You are an expert in productivity, discipline, task management, and routines.
                            Only provide information about tasks, discipline, or routines. 
                            Be concise and provide practical advice. 
                            Cite relevant books, studies, or research if applicable.
                            
                            The user's name is $email.
                            Here are their tasks:
                            ${tasks.joinToString("\n") { "- ${it.title}: ${it.description} (${if (it.completed) "completed" else "incomplete"})" }}
                            
                            Try to keep replies to less than 100 words.
                            
                            This is the User's query: $prompt
                        """.trimIndent()

                        sendPromptToAI(refinedPrompt)
                    }
                    .addOnFailureListener { error ->
                        _uiState.value = UiState.Error("Failed to fetch tasks: ${error.message}")
                    }
            }
            .addOnFailureListener { error ->
                _uiState.value = UiState.Error("Failed to fetch user data: ${error.message}")
            }
    }

    /**
     * Send the refined prompt to Gemini for AI processing.
     */
    private fun sendPromptToAI(refinedPrompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("TaskViewModel", "Sending prompt to Gemini: $refinedPrompt")

                val response = generativeModel.generateContent(
                    content { text(refinedPrompt) }
                )

                response.text?.let {
                    Log.d("TaskViewModel", "Received response from Gemini: $it")
                    _uiState.value = UiState.Success(it)
                } ?: run {
                    _uiState.value = UiState.Error("No response from AI.")
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error calling Gemini: ${e.message}")
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred.")
            }
        }
    }
}


