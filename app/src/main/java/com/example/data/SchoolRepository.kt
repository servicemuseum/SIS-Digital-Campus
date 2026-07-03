package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SchoolRepository(private val schoolDao: SchoolDao) {

    val allNotices: Flow<List<Notice>> = schoolDao.getAllNotices()
    val allSalatLogs: Flow<List<SalatLog>> = schoolDao.getAllSalatLogs()
    val quranProgress: Flow<QuranProgress?> = schoolDao.getQuranProgressFlow()
    val allStudents: Flow<List<Student>> = schoolDao.getAllStudents()
    val allStaff: Flow<List<StaffMember>> = schoolDao.getAllStaff()
    val allPayments: Flow<List<FeePayment>> = schoolDao.getAllPayments()
    val allHomeworks: Flow<List<Homework>> = schoolDao.getAllHomeworks()
    val allDuas: Flow<List<DuaItem>> = schoolDao.getAllDuas()

    fun getStudentsByClass(className: String): Flow<List<Student>> = schoolDao.getStudentsByClass(className)
    fun getHomeworksByClass(className: String): Flow<List<Homework>> = schoolDao.getHomeworksByClass(className)

    suspend fun insertNotice(notice: Notice) = schoolDao.insertNotice(notice)
    
    suspend fun logSalat(date: String, prayer: String, completed: Boolean) {
        val existing = schoolDao.getSalatLogByDate(date) ?: SalatLog(date = date)
        val updated = when (prayer.lowercase()) {
            "fajr" -> existing.copy(fajr = completed)
            "dhuhr" -> existing.copy(dhuhr = completed)
            "asr" -> existing.copy(asr = completed)
            "maghrib" -> existing.copy(maghrib = completed)
            "isha" -> existing.copy(isha = completed)
            else -> existing
        }
        schoolDao.insertSalatLog(updated)
    }

    suspend fun updateQuranMinutes(date: String, minutes: Int, pages: Int) {
        val existing = schoolDao.getSalatLogByDate(date) ?: SalatLog(date = date)
        val updated = existing.copy(
            quranMinutes = existing.quranMinutes + minutes,
            quranPages = existing.quranPages + pages
        )
        schoolDao.insertSalatLog(updated)
    }

    suspend fun updateQuranProgress(lastSurah: String, lastAyah: Int, completedSurahs: String) {
        val progress = QuranProgress(id = 1, lastSurah = lastSurah, lastAyah = lastAyah, completedSurahs = completedSurahs)
        schoolDao.insertQuranProgress(progress)
    }

    suspend fun updateStudentAttendance(studentId: String, isPresent: Boolean) {
        // Retrieve student, update history
        val all = schoolDao.getAllStudents().firstOrNull() ?: return
        val student = all.find { it.id == studentId } ?: return
        val currentHistory = student.attendanceHistory
        val status = if (isPresent) "P" else "A"
        val updatedHistory = if (currentHistory.isEmpty()) status else "$currentHistory,$status"
        schoolDao.updateStudent(student.copy(attendanceHistory = updatedHistory))
    }

    suspend fun addStudentExamResult(studentId: String, subject: String, mark: Int) {
        val all = schoolDao.getAllStudents().firstOrNull() ?: return
        val student = all.find { it.id == studentId } ?: return
        val resultsMap = student.examResults.split(",")
            .filter { it.contains(":") }
            .map {
                val parts = it.split(":")
                parts[0].trim() to parts[1].trim()
            }.toMap().toMutableMap()
        
        resultsMap[subject] = mark.toString()
        val updatedResults = resultsMap.map { "${it.key}: ${it.value}" }.joinToString(", ")
        schoolDao.updateStudent(student.copy(examResults = updatedResults))
    }

    suspend fun insertStudent(student: Student) = schoolDao.insertStudent(student)
    suspend fun insertStaff(staff: StaffMember) = schoolDao.insertStaff(staff)
    suspend fun insertPayment(payment: FeePayment) = schoolDao.insertPayment(payment)
    suspend fun insertHomework(homework: Homework) = schoolDao.insertHomework(homework)
    suspend fun updateDua(dua: DuaItem) = schoolDao.updateDua(dua)

    suspend fun checkAndSeedData() {
        val notices = schoolDao.getAllNotices().firstOrNull()
        if (notices.isNullOrEmpty()) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val todayStr = dateFormat.format(Date())

            // 1. Seed Notices
            schoolDao.insertNotice(Notice(
                title = "SIS Digital Campus Launch",
                content = "Welcome to Shifa International School's official digital portal! Students, teachers, parents, and administrators can now access resources, monitor academic progress, tracks Islamic values, and manage transactions seamlessly.",
                date = todayStr,
                role = "ALL"
            ))
            schoolDao.insertNotice(Notice(
                title = "Admission Open for 2026-2027",
                content = "Admissions are officially open from Play-Group to Class 9. Interested guardians can fill out the online admission form in the administration portal or contact the campus main desk.",
                date = todayStr,
                role = "GUARDIAN"
            ))
            schoolDao.insertNotice(Notice(
                title = "Monthly Syllabus Guidelines & Lesson Plans",
                content = "All teachers are requested to submit their multimedia class syllabi and lesson logs for July by the end of this week. Principal Md. Abdul Mannan will lead the staff review meeting.",
                date = todayStr,
                role = "TEACHER"
            ))
            schoolDao.insertNotice(Notice(
                title = "E-Library New Additions",
                content = "New interactive NCTB e-books and Spoken English audio resources have been added to the academic module. Students are encouraged to download their class textbooks and moral education resources.",
                date = todayStr,
                role = "STUDENT"
            ))

            // 2. Seed Students
            schoolDao.insertStudent(Student(
                id = "SIS-2026-001",
                name = "Afnan Ahmed",
                className = "Class 5",
                roll = 1,
                attendanceHistory = "P,P,P,A,P,P,P,P",
                examResults = "English: 92, Mathematics: 98, Islamic Studies: 96, Bengali: 90"
            ))
            schoolDao.insertStudent(Student(
                id = "SIS-2026-002",
                name = "Fariha Kabir",
                className = "Class 5",
                roll = 2,
                attendanceHistory = "P,P,P,P,P,P,P,P",
                examResults = "English: 88, Mathematics: 85, Islamic Studies: 94, Bengali: 92"
            ))
            schoolDao.insertStudent(Student(
                id = "SIS-2026-003",
                name = "Zunairah Khan",
                className = "Class 8",
                roll = 1,
                attendanceHistory = "P,P,P,A,P,A,P,P",
                examResults = "English: 95, Mathematics: 92, Islamic Studies: 98, Bengali: 89"
            ))
            schoolDao.insertStudent(Student(
                id = "SIS-2026-004",
                name = "Tahmid Hasan",
                className = "Class 10",
                roll = 3,
                attendanceHistory = "P,P,A,P,P,P,P,P",
                examResults = "English: 85, Mathematics: 95, Islamic Studies: 92, Bengali: 84"
            ))

            // 3. Seed Staff Members
            schoolDao.insertStaff(StaffMember(
                id = "SIS-T-001",
                name = "Md. Abdul Mannan",
                designation = "Principal & Senior Islamic Scholar",
                subject = "Islamic Moral Studies",
                email = "principal@shifaintschool.com",
                phone = "+880 1711-223344"
            ))
            schoolDao.insertStaff(StaffMember(
                id = "SIS-T-002",
                name = "Tasnim Ara",
                designation = "Senior Assistant Teacher",
                subject = "English Language & Spoken",
                email = "tasnim@shifaintschool.com",
                phone = "+880 1812-345678"
            ))
            schoolDao.insertStaff(StaffMember(
                id = "SIS-T-003",
                name = "Kamrul Islam",
                designation = "Assistant Teacher",
                subject = "Mathematics",
                email = "kamrul@shifaintschool.com",
                phone = "+880 1913-987654"
            ))
            schoolDao.insertStaff(StaffMember(
                id = "SIS-T-004",
                name = "Nusrat Jahan",
                designation = "Assistant Teacher",
                subject = "Bengali & Arts",
                email = "nusrat@shifaintschool.com",
                phone = "+880 1514-455667"
            ))

            // 4. Seed QuranProgress
            schoolDao.insertQuranProgress(QuranProgress(
                id = 1,
                lastSurah = "Al-Baqarah",
                lastAyah = 255,
                completedSurahs = "Al-Fatihah, Al-Ikhlas, Al-Falaq, An-Nas, Al-Mulk"
            ))

            // 5. Seed Duas
            schoolDao.insertDua(DuaItem(
                id = 1,
                arabic = "رَّبِّ زِدْنِي عِلْمًا",
                englishTranslation = "O my Lord! Increase me in knowledge.",
                bengaliTranslation = "হে আমার প্রতিপালক! আমার জ্ঞান বৃদ্ধি করে দিন।",
                benefit = "Recite regularly to ask Allah to expand memory, intellect, and beneficial knowledge.",
                isMemorized = true
            ))
            schoolDao.insertDua(DuaItem(
                id = 2,
                arabic = "رَّبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
                englishTranslation = "My Lord! Have mercy on them as they raised me when I was small.",
                bengaliTranslation = "হে আমার প্রতিপালক! তাঁদের প্রতি দয়া করুন যেভাবে তাঁরা আমাকে শৈশবে লালন-পালন করেছিলেন।",
                benefit = "A powerful Quranic supplication for the well-being and mercy of one's parents.",
                isMemorized = false
            ))
            schoolDao.insertDua(DuaItem(
                id = 3,
                arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
                englishTranslation = "In Your name, O Allah, I die and I live.",
                bengaliTranslation = "হে আল্লাহ! আপনার নামেই আমি মৃত্যুবরণ করি (ঘুমাই) এবং জীবিত হই (জেগে উঠি)।",
                benefit = "Sunnah supplication recited immediately before sleeping.",
                isMemorized = true
            ))
            schoolDao.insertDua(DuaItem(
                id = 4,
                arabic = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
                englishTranslation = "O Allah, open for me the gates of Your mercy.",
                bengaliTranslation = "হে আল্লাহ, আমার জন্য তোমার রহমতের দরজাগুলো খুলে দাও।",
                benefit = "Sunnah dua recited upon entering the mosque.",
                isMemorized = false
            ))

            // 6. Seed Homework
            schoolDao.insertHomework(Homework(
                subject = "Mathematics",
                className = "Class 5",
                description = "Solve Exercises 3.4 on Fraction Multiplication and Word Problems 1-5 in your textbook.",
                assignedDate = todayStr,
                dueDate = "06 Jul 2026"
            ))
            schoolDao.insertHomework(Homework(
                subject = "Islamic Moral Education",
                className = "Class 5",
                description = "Memorize and write the Bengali translation and moral takeaways of Dua #2 (Dua for Parents). Log Salat regularly.",
                assignedDate = todayStr,
                dueDate = "05 Jul 2026"
            ))

            // 7. Seed Payments
            schoolDao.insertPayment(FeePayment(
                billingMonth = "June 2026",
                amount = 2500.0,
                method = "bKash",
                accountNo = "01788776655",
                transactionId = "BKX7H6G5F4",
                paymentDate = "10 Jun 2026",
                receiptNo = "SIS-REC-2026-4498",
                status = "Paid"
            ))
        }
    }
}
