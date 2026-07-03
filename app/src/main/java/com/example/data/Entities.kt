package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notices")
data class Notice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val date: String,
    val role: String = "ALL" // ALL, TEACHER, GUARDIAN, STUDENT
)

@Entity(tableName = "salat_logs")
data class SalatLog(
    @PrimaryKey val date: String, // YYYY-MM-DD
    val fajr: Boolean = false,
    val dhuhr: Boolean = false,
    val asr: Boolean = false,
    val maghrib: Boolean = false,
    val isha: Boolean = false,
    val quranMinutes: Int = 0,
    val quranPages: Int = 0
)

@Entity(tableName = "quran_progress")
data class QuranProgress(
    @PrimaryKey val id: Int = 1,
    val lastSurah: String = "Al-Fatihah",
    val lastAyah: Int = 1,
    val completedSurahs: String = "Al-Fatihah, Al-Ikhlas, Al-Falaq, An-Nas"
)

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val id: String, // e.g. SIS-2026-001
    val name: String,
    val className: String,
    val roll: Int,
    val attendanceHistory: String = "P,P,P,P,P", // Comma-separated: P=Present, A=Absent
    val examResults: String = "English: 85, Mathematics: 90, Islamic Studies: 95, Bengali: 88" // Comma-separated subject:mark
) {
    val attendanceRate: Float
        get() {
            val list = attendanceHistory.split(",").filter { it.isNotEmpty() }
            if (list.isEmpty()) return 100.0f
            val present = list.count { it == "P" }
            return (present.toFloat() / list.size) * 100f
        }
}

@Entity(tableName = "staff_members")
data class StaffMember(
    @PrimaryKey val id: String,
    val name: String,
    val designation: String, // e.g. Assistant Teacher, Senior Teacher, Principal
    val subject: String = "General",
    val email: String,
    val phone: String
)

@Entity(tableName = "fee_payments")
data class FeePayment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val billingMonth: String,
    val amount: Double,
    val method: String, // bKash, Nagad, Rocket
    val accountNo: String,
    val transactionId: String,
    val paymentDate: String,
    val receiptNo: String,
    val status: String = "Paid" // Paid, Pending
)

@Entity(tableName = "homeworks")
data class Homework(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val className: String,
    val description: String,
    val assignedDate: String,
    val dueDate: String
)

@Entity(tableName = "dua_checklist")
data class DuaItem(
    @PrimaryKey val id: Int,
    val arabic: String,
    val englishTranslation: String,
    val bengaliTranslation: String,
    val benefit: String,
    val isMemorized: Boolean = false
)
