package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {
    // Notice Queries
    @Query("SELECT * FROM notices ORDER BY id DESC")
    fun getAllNotices(): Flow<List<Notice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: Notice)

    // SalatLog Queries
    @Query("SELECT * FROM salat_logs ORDER BY date DESC")
    fun getAllSalatLogs(): Flow<List<SalatLog>>

    @Query("SELECT * FROM salat_logs WHERE date = :date LIMIT 1")
    suspend fun getSalatLogByDate(date: String): SalatLog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalatLog(log: SalatLog)

    // QuranProgress Queries
    @Query("SELECT * FROM quran_progress WHERE id = 1 LIMIT 1")
    fun getQuranProgressFlow(): Flow<QuranProgress?>

    @Query("SELECT * FROM quran_progress WHERE id = 1 LIMIT 1")
    suspend fun getQuranProgress(): QuranProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuranProgress(progress: QuranProgress)

    // Student Queries
    @Query("SELECT * FROM students ORDER BY className ASC, roll ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE className = :className ORDER BY roll ASC")
    fun getStudentsByClass(className: String): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    // StaffMember Queries
    @Query("SELECT * FROM staff_members ORDER BY designation DESC, name ASC")
    fun getAllStaff(): Flow<List<StaffMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: StaffMember)

    // FeePayment Queries
    @Query("SELECT * FROM fee_payments ORDER BY id DESC")
    fun getAllPayments(): Flow<List<FeePayment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: FeePayment)

    // Homework Queries
    @Query("SELECT * FROM homeworks ORDER BY id DESC")
    fun getAllHomeworks(): Flow<List<Homework>>

    @Query("SELECT * FROM homeworks WHERE className = :className ORDER BY id DESC")
    fun getHomeworksByClass(className: String): Flow<List<Homework>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomework(homework: Homework)

    // DuaItem Queries
    @Query("SELECT * FROM dua_checklist ORDER BY id ASC")
    fun getAllDuas(): Flow<List<DuaItem>>

    @Update
    suspend fun updateDua(dua: DuaItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDua(dua: DuaItem)
}
