# HabIT: Better habits, better living.

### Team Composition:
- Timothy Jayden Klint
- Joshua Benjamin Kravitz
- Dylan Savelson

### Important Links:
- Figma link: TODO

### Description:
HabIT is a personalized app designed to help users build consistent morning routines. Users can create and customize their routines, check off tasks throughout the day, and visualize their progress on a calendar, where each day entry will fill up the block. With AI integration, the app provides personalized reminders, routine suggestions, and insights to help users optimize their habits over time.

### Features/Scope:
- Routine Customization: Users can design unique morning and routines with specific tasks.
- Progress Tracking: Users can check off tasks after clicking on a day, which updates a calendar to show progress.
- AI-Driven Personalization: AI offers personalized reminders, routine suggestions, and insights to improve habit consistency.
- Visual Analytics: A calendar view allows users to review their streaks, success rates, and patterns over time.

---

# HabIT: Better Habits, Better Living

<div align="center">
  <h2>HabIT</h2>
  <h3>An AI-powered habit tracker to keep you accountable, anytime, anywhere</h3>
</div>

<div align="center">
  <img src="https://img.shields.io/badge/google%20gemini-8E75B2?style=for-the-badge&logo=google%20gemini&logoColor=white" alt="Google Gemini">
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white" alt="Android Studio">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/GoogleCloud-%234285F4.svg?style=for-the-badge&logo=google-cloud&logoColor=white" alt="Google Cloud">
  <img src="https://img.shields.io/badge/firebase-a08021?style=for-the-badge&logo=firebase&logoColor=ffcd34" alt="Firebase">
</div>

## 📖 About the Project

HabIT is your personal habit coach, blending cutting-edge AI with habit tracking to help you stay accountable and achieve your goals. Whether it's mastering a morning routine, staying on top of tasks, or forming better daily habits, HabIT is here to make your journey seamless and effective.

### Key Features:
- **AI-Powered Guidance:** Receive personalized habit advice with Google Gemini.
- **Dynamic Mood Levels:** Customize how nice (or mean!) your AI responses are, from "super nice" to "super mean."
- **Smart Habit Tracking:** Manage tasks, mark completions, and visualize progress.
- **Reminders and Notifications:** Never forget a habit with smart alerts tailored to your schedule.
- **Real-Time Sync:** Securely sync habits and progress with Firebase.
- **Simple and Clean UI:** Built with Jetpack Compose for an intuitive experience.

## 🚀 Tech Stack

| **Tech Stack**                                                                                         | **Demo Video**                                           |
|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------|
| - **Kotlin:** For clean, concise, and powerful Android development. <br> - **Jetpack Compose:** Declarative UI for beautiful, modern design. <br> - **Google Gemini:** Provides AI-powered habit recommendations. <br> - **Firebase:** Real-time database for seamless habit and task syncing. <br> - **Google Cloud:** Supports AI processing and app infrastructure. <br> - **Android Studio:** The official IDE for development. | ![HabIT Demo Video](https://img.youtube.com/vi/YOUR_VIDEO_ID/0.jpg) <br> [Watch Video](https://www.youtube.com/watch?v=YOUR_VIDEO_ID) |


## 🛠️ Getting Started

### Prerequisites
- Android Studio (latest version)
- Firebase account
- Google Cloud account with **Gemini API** enabled.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/tjklint/habIT.git
   cd habIT
   ```
2. Open the project in Android Studio.
3. Configure Firebase
   - Add your google-services.json file to the app/ directory.
   - Set up Firestore and Authentication in the Firebase Console.
4. Configure Google Gemini
   - Enable the Gemini API in your Google Cloud Project.
   - Add your API key to TaskViewModel.kt:
   ```kotlin
   apiKey = "YOUR_GEMINI_API_KEY"
   ```
5. Run the App
   - Connect an Android device or emulator.
   - Click Run in Android Studio.


