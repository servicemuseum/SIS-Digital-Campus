package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = SchoolRepository(db.schoolDao())

    // Language state: "en" (English) or "bn" (Bengali)
    private val _language = MutableStateFlow("en")
    val language: StateFlow<String> = _language.asStateFlow()

    // Active Role: "Administrator", "Teacher", "Guardian", "Student"
    private val _currentRole = MutableStateFlow("Student")
    val currentRole: StateFlow<String> = _currentRole.asStateFlow()

    // Tab Selection: "home", "academic", "islamic", "finance", "admin"
    private val _currentTab = MutableStateFlow("home")
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    // Screen specific states
    val notices: StateFlow<List<Notice>> = repository.allNotices.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val salatLogs: StateFlow<List<SalatLog>> = repository.allSalatLogs.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val quranProgress: StateFlow<QuranProgress?> = repository.quranProgress.stateIn(viewModelScope, SharingStarted.Lazily, null)
    val students: StateFlow<List<Student>> = repository.allStudents.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val staff: StateFlow<List<StaffMember>> = repository.allStaff.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val payments: StateFlow<List<FeePayment>> = repository.allPayments.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val homeworks: StateFlow<List<Homework>> = repository.allHomeworks.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val duas: StateFlow<List<DuaItem>> = repository.allDuas.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Selected Class for academic materials (Pre-Play to Class 10)
    private val _selectedClass = MutableStateFlow("Class 5")
    val selectedClass: StateFlow<String> = _selectedClass.asStateFlow()

    // CCTV Scanning states for Admin
    private val _activeCamera = MutableStateFlow("Main Gate")
    val activeCamera: StateFlow<String> = _activeCamera.asStateFlow()

    // Van Tracking Simulation Coordinates (0.0 to 1.0 on a local drawing canvas)
    private val _vanPositionX = MutableStateFlow(0.1f)
    val vanPositionX: StateFlow<Float> = _vanPositionX.asStateFlow()
    private val _vanPositionY = MutableStateFlow(0.4f)
    val vanPositionY: StateFlow<Float> = _vanPositionY.asStateFlow()

    // Mock Online Quiz State
    private val _quizQuestionIndex = MutableStateFlow(0)
    val quizQuestionIndex: StateFlow<Int> = _quizQuestionIndex.asStateFlow()
    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()
    private val _quizCompleted = MutableStateFlow(false)
    val quizCompleted: StateFlow<Boolean> = _quizCompleted.asStateFlow()

    // Salat Streak Counter
    val salatStreak: StateFlow<Int> = salatLogs.map { logs ->
        var streak = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val cal = Calendar.getInstance()
        
        // Count consecutive days with at least one Salat checked
        for (i in 0..30) {
            val dateStr = sdf.format(cal.time)
            val log = logs.find { it.date == dateStr }
            if (log != null && (log.fajr || log.dhuhr || log.asr || log.maghrib || log.isha)) {
                streak++
            } else if (i > 0) {
                // Allow today to be unlogged, but if yesterday is empty, break
                if (i == 1 && logs.find { it.date == sdf.format(Date()) } == null) {
                    continue
                } else {
                    break
                }
            }
            cal.add(Calendar.DATE, -1)
        }
        streak
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    init {
        viewModelScope.launch {
            repository.checkAndSeedData()
            simulateVanMovement()
        }
    }

    private suspend fun simulateVanMovement() {
        // Simple loop to slowly move the van along a path on the school route map
        viewModelScope.launch {
            val path = listOf(
                0.1f to 0.4f, 0.25f to 0.42f, 0.4f to 0.35f, 0.6f to 0.5f,
                0.75f to 0.48f, 0.85f to 0.3f, 0.9f to 0.55f, 0.7f to 0.7f,
                0.5f to 0.65f, 0.3f to 0.6f, 0.15f to 0.55f
            )
            var index = 0
            while (true) {
                val point = path[index]
                _vanPositionX.value = point.first
                _vanPositionY.value = point.second
                delay(3000)
                index = (index + 1) % path.size
            }
        }
    }

    // Settings / Control
    fun toggleLanguage() {
        _language.value = if (_language.value == "en") "bn" else "en"
    }

    fun setRole(role: String) {
        _currentRole.value = role
        // Adjust default tab to prevent viewing out-of-role restricted views
        if (role == "Student" && _currentTab.value == "admin") {
            _currentTab.value = "home"
        } else if (role == "Guardian" && _currentTab.value == "admin") {
            _currentTab.value = "home"
        }
    }

    fun setTab(tab: String) {
        _currentTab.value = tab
    }

    fun setSelectedClass(className: String) {
        _selectedClass.value = className
    }

    fun setActiveCamera(camera: String) {
        _activeCamera.value = camera
    }

    // Actions
    fun publishNotice(title: String, content: String, role: String) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val todayStr = dateFormat.format(Date())
            repository.insertNotice(Notice(title = title, content = content, role = role, date = todayStr))
        }
    }

    fun addHomework(subject: String, className: String, description: String, dueDate: String) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val todayStr = dateFormat.format(Date())
            repository.insertHomework(
                Homework(
                    subject = subject,
                    className = className,
                    description = description,
                    assignedDate = todayStr,
                    dueDate = dueDate
                )
            )
        }
    }

    fun markAttendance(studentId: String, isPresent: Boolean) {
        viewModelScope.launch {
            repository.updateStudentAttendance(studentId, isPresent)
        }
    }

    fun enterResult(studentId: String, subject: String, mark: Int) {
        viewModelScope.launch {
            repository.addStudentExamResult(studentId, subject, mark)
        }
    }

    fun processFeePayment(billingMonth: String, amount: Double, method: String, accountNo: String): String {
        val receiptNo = "SIS-REC-2026-${(1000..9999).random()}"
        val transactionId = "${method.take(2).uppercase()}${('A'..'Z').random()}${('0'..'9').random()}${(100..999).random()}"
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val todayStr = dateFormat.format(Date())

        viewModelScope.launch {
            repository.insertPayment(
                FeePayment(
                    billingMonth = billingMonth,
                    amount = amount,
                    method = method,
                    accountNo = accountNo,
                    transactionId = transactionId,
                    paymentDate = todayStr,
                    receiptNo = receiptNo,
                    status = "Paid"
                )
            )
        }
        return receiptNo
    }

    fun insertStudent(student: Student) {
        viewModelScope.launch {
            repository.insertStudent(student)
        }
    }

    fun logDailySalat(prayer: String, completed: Boolean) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(Date())
        viewModelScope.launch {
            repository.logSalat(todayStr, prayer, completed)
        }
    }

    fun logQuranMinutes(minutes: Int, pages: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(Date())
        viewModelScope.launch {
            repository.updateQuranMinutes(todayStr, minutes, pages)
        }
    }

    fun saveQuranProgress(lastSurah: String, lastAyah: Int, completedSurahs: String) {
        viewModelScope.launch {
            repository.updateQuranProgress(lastSurah, lastAyah, completedSurahs)
        }
    }

    fun toggleDuaMemorized(dua: DuaItem) {
        viewModelScope.launch {
            repository.updateDua(dua.copy(isMemorized = !dua.isMemorized))
        }
    }

    // Mock Online Quiz Engine
    val quizQuestions = listOf(
        QuizQuestion(
            questionEn = "Which Prophet was known for his patience (Sabr)?",
            questionBn = "কোন নবী তাঁর ধৈর্যের (সবর) জন্য পরিচিত ছিলেন?",
            optionsEn = listOf("Prophet Yusuf (AS)", "Prophet Ayyub (AS)", "Prophet Musa (AS)", "Prophet Yunus (AS)"),
            optionsBn = listOf("হযরত ইউসুফ (আঃ)", "হযরত আইয়ুব (আঃ)", "হযরত মূসা (আঃ)", "হযরত ইউনুস (আঃ)"),
            correctAnswerIndex = 1
        ),
        QuizQuestion(
            questionEn = "How many Farz parts are there in Wudu?",
            questionBn = "ওযুর ফরয কাজ কয়টি?",
            optionsEn = listOf("3", "4", "5", "6"),
            optionsBn = listOf("৩টি", "৪টি", "৫টি", "৬টি"),
            correctAnswerIndex = 1
        ),
        QuizQuestion(
            questionEn = "What is the primary language of Shifa International School curriculum?",
            questionBn = "শিফা ইন্টারন্যাশনাল স্কুলের প্রধান কারিকুলাম ভাষা কী?",
            optionsEn = listOf("English & Bengali (NCTB)", "Arabic only", "Urdu only", "English medium only"),
            optionsBn = listOf("ইংরেজি ও বাংলা (এনসিটিবি)", "শুধু আরবি", "শুধু উর্দু", "শুধু ইংলিশ মিডিয়াম"),
            correctAnswerIndex = 0
        )
    )

    fun answerQuizQuestion(selectedOptionIndex: Int) {
        val currentQ = quizQuestions[_quizQuestionIndex.value]
        if (selectedOptionIndex == currentQ.correctAnswerIndex) {
            _quizScore.value += 1
        }
        if (_quizQuestionIndex.value < quizQuestions.size - 1) {
            _quizQuestionIndex.value += 1
        } else {
            _quizCompleted.value = true
        }
    }

    fun resetQuiz() {
        _quizQuestionIndex.value = 0
        _quizScore.value = 0
        _quizCompleted.value = false
    }
}

data class QuizQuestion(
    val questionEn: String,
    val questionBn: String,
    val optionsEn: List<String>,
    val optionsBn: List<String>,
    val correctAnswerIndex: Int
)
