package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SchoolBannerSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.school_banner),
            contentDescription = "Shifa International School Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Elegant overlay gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 100f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Shifa International School",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Muktinagar, Siddhirganj, Narayanganj",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ProfileCard(lang: String, role: String, onSwitchRoleClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (role) {
                            "Administrator" -> Icons.Default.AdminPanelSettings
                            "Teacher" -> Icons.Default.School
                            "Guardian" -> Icons.Default.SupervisorAccount
                            else -> Icons.Default.Person
                        },
                        contentDescription = "Role Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = Translations.get("role_label", lang),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = when (role) {
                            "Administrator" -> if (lang == "bn") "প্রশাসক (Admin)" else "Administrator"
                            "Teacher" -> if (lang == "bn") "শিক্ষক (Teacher)" else "Teacher"
                            "Guardian" -> if (lang == "bn") "অভিভাবক (Guardian)" else "Guardian"
                            else -> if (lang == "bn") "শিক্ষার্থী (Student)" else "Student"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Button(
                onClick = onSwitchRoleClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier
                    .height(36.dp)
                    .testTag("switch_role_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Cached,
                    contentDescription = "Switch",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = Translations.get("change_role", lang),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ------------------- HOME SCREEN -------------------
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    lang: String,
    notices: List<Notice>,
    role: String,
    onNavigateToTab: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SchoolBannerSection()
        }

        // About Shifa School
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Translations.get("school_name", lang),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = Translations.get("school_location", lang),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(Translations.get("established", lang), fontSize = 11.sp) },
                            icon = { Icon(Icons.Default.Event, null, modifier = Modifier.size(14.dp)) }
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text("Principal: Md. Abdul Mannan", fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            icon = { Icon(Icons.Default.AccountBox, null, modifier = Modifier.size(14.dp)) }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = Translations.get("curriculum", lang),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = Translations.get("contact_info", lang),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Quick Modules Access
        item {
            Text(
                text = if (lang == "bn") "সহজ মডিউল এক্সেস" else "Quick Access Modules",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Academic Module Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToTab("academic") },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Book, null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = Translations.get("academic_mgmt", lang),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Islamic Module Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToTab("islamic") },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.tertiary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Mosque, null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = Translations.get("islamic_tracker", lang),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Notices List
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Translations.get("notice_board", lang),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (role == "Administrator") {
                    TextButton(onClick = { onNavigateToTab("admin") }) {
                        Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(Translations.get("publish_notice", lang), fontSize = 12.sp)
                    }
                }
            }
        }

        val filteredNotices = notices.filter {
            it.role == "ALL" || it.role == role.uppercase()
        }

        if (filteredNotices.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (lang == "bn") "কোন নোটিশ পাওয়া যায়নি।" else "No active notices found.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(filteredNotices) { notice ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            when (notice.role) {
                                                "ADMINISTRATOR" -> Color.Red
                                                "TEACHER" -> MaterialTheme.colorScheme.primary
                                                "GUARDIAN" -> MaterialTheme.colorScheme.secondary
                                                else -> MaterialTheme.colorScheme.tertiary
                                            },
                                            CircleShape
                                        )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = notice.role,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = notice.date,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = notice.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = notice.content,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

// ------------------- ACADEMIC SCREEN -------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicScreen(
    viewModel: MainViewModel,
    lang: String,
    role: String,
    selectedClass: String,
    students: List<Student>,
    homeworks: List<Homework>
) {
    var activeSubTab by remember { mutableStateOf("routine") } // routine, books, exam, results
    var selectedBookForReading by remember { mutableStateOf<String?>(null) }
    var currentTestScore by remember { mutableStateOf<Int?>(null) }

    // Forms for Teacher
    var showHomeworkDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    var selectedStudentForResult by remember { mutableStateOf<Student?>(null) }

    val classes = listOf("Pre-Play", "Class 1", "Class 3", "Class 5", "Class 8", "Class 10")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Horizontal Class Selector Selector
        Text(
            text = Translations.get("select_class", lang),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            classes.forEach { cls ->
                FilterChip(
                    selected = selectedClass == cls,
                    onClick = { viewModel.setSelectedClass(cls) },
                    label = { Text(cls, fontSize = 12.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sub Tab Selector
        TabRow(
            selectedTabIndex = when (activeSubTab) {
                "routine" -> 0
                "books" -> 1
                "exam" -> 2
                else -> 3
            },
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = activeSubTab == "routine",
                onClick = { activeSubTab = "routine" },
                text = { Text(Translations.get("tab_academic", lang), fontSize = 12.sp) }
            )
            Tab(
                selected = activeSubTab == "books",
                onClick = { activeSubTab = "books" },
                text = { Text(Translations.get("nctb_books", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "exam",
                onClick = { activeSubTab = "exam" },
                text = { Text(Translations.get("exam_portal", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "results",
                onClick = { activeSubTab = "results" },
                text = { Text(Translations.get("result_processing", lang), fontSize = 11.sp, maxLines = 1) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content switching based on SubTab
        Box(modifier = Modifier.weight(1f)) {
            when (activeSubTab) {
                "routine" -> {
                    RoutineSubTab(viewModel, lang, selectedClass, role, homeworks, onAddHomeworkClick = {
                        showHomeworkDialog = true
                    })
                }
                "books" -> {
                    BooksSubTab(lang, selectedClass, onReadBook = { book ->
                        selectedBookForReading = book
                    })
                }
                "exam" -> {
                    ExamSubTab(viewModel, lang)
                }
                "results" -> {
                    ResultsSubTab(
                        viewModel, lang, role, selectedClass, students,
                        onEnterResultClick = { student ->
                            selectedStudentForResult = student
                            showResultDialog = true
                        }
                    )
                }
            }
        }
    }

    // Book Reading simulator dialog
    if (selectedBookForReading != null) {
        Dialog(onDismissRequest = { selectedBookForReading = null }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = selectedBookForReading!!,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "National Curriculum & Textbook Board (NCTB) Bangladesh",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = if (lang == "bn") "অধ্যায় ১: মৌলিক ধারণা এবং নৈতিকতা" else "Chapter 1: Foundations & Moral Character",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (lang == "bn") {
                            "এই অধ্যায়ে আমরা শিখব কীভাবে প্রাত্যহিক জীবনে নিয়মানুবর্তিতা, নৈতিক দায়িত্বশীলতা এবং সামাজিক মূল্যবোধ বজায় রাখা যায়। শিফা ইন্টারন্যাশনাল স্কুলের মূল লক্ষ্য হলো শিক্ষার সাথে সাথে আধ্যাত্মিক উন্নয়ন নিশ্চিত করা।"
                        } else {
                            "In this chapter, we explore how daily discipline, moral accountability, and social responsibilities form the bedrock of a successful life. SIS integrates standard national academics with robust ethical insights."
                        },
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { selectedBookForReading = null }) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }

    // Homework Assignment Dialog (Teacher)
    if (showHomeworkDialog) {
        var subject by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var dueDate by remember { mutableStateOf("") }

        Dialog(onDismissRequest = { showHomeworkDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Translations.get("add_homework", lang),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject / বিষয়") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Instructions / বিবরণ") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("Due Date / জমা দেওয়ার তারিখ (e.g. 08 Jul)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showHomeworkDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (subject.isNotEmpty() && description.isNotEmpty() && dueDate.isNotEmpty()) {
                                    viewModel.addHomework(subject, selectedClass, description, dueDate)
                                    showHomeworkDialog = false
                                }
                            }
                        ) {
                            Text("Assign")
                        }
                    }
                }
            }
        }
    }

    // Enter Student Marks Dialog (Teacher)
    if (showResultDialog && selectedStudentForResult != null) {
        var subject by remember { mutableStateOf("Mathematics") }
        var marksText by remember { mutableStateOf("") }

        Dialog(onDismissRequest = { showResultDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${Translations.get("enter_results", lang)} - ${selectedStudentForResult!!.name}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject / বিষয়") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = marksText,
                        onValueChange = { marksText = it },
                        label = { Text("Marks (0-100) / নম্বর") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showResultDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val marks = marksText.toIntOrNull()
                                if (marks != null && marks in 0..100) {
                                    viewModel.enterResult(selectedStudentForResult!!.id, subject, marks)
                                    showResultDialog = false
                                }
                            }
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineSubTab(
    viewModel: MainViewModel,
    lang: String,
    selectedClass: String,
    role: String,
    homeworks: List<Homework>,
    onAddHomeworkClick: () -> Unit
) {
    val routine = when (selectedClass) {
        "Class 5" -> listOf(
            "08:30 AM - 09:15 AM" to ("Quran Recitation & Hadith" to "Md. Abdul Mannan"),
            "09:15 AM - 10:00 AM" to ("English Grammar" to "Tasnim Ara"),
            "10:00 AM - 10:45 AM" to ("Mathematics Geometry" to "Kamrul Islam"),
            "11:15 AM - 12:00 PM" to ("Bengali literature" to "Nusrat Jahan"),
            "12:00 PM - 12:45 PM" to ("Science & Moral Values" to "Tasnim Ara")
        )
        "Class 8" -> listOf(
            "08:30 AM - 09:15 AM" to ("Quran Recitation" to "Md. Abdul Mannan"),
            "09:15 AM - 10:00 AM" to ("Mathematics Algebra" to "Kamrul Islam"),
            "10:00 AM - 10:45 AM" to ("English Spoken" to "Tasnim Ara"),
            "11:15 AM - 12:00 PM" to ("Science" to "Kamrul Islam"),
            "12:00 PM - 12:45 PM" to ("History & Ethics" to "Nusrat Jahan")
        )
        else -> listOf(
            "09:00 AM - 09:45 AM" to ("Alphabet & Rhymes" to "Nusrat Jahan"),
            "09:45 AM - 10:30 AM" to ("Islamic Stories" to "Md. Abdul Mannan"),
            "10:30 AM - 11:15 AM" to ("Spoken English Basics" to "Tasnim Ara")
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = Translations.get("class_routine", lang) + " ($selectedClass)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(routine) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.first,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.second.first,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = item.second.second,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Active Homework Checklist
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (lang == "bn") "সক্রিয় বাড়ির কাজ (Homework)" else "Assigned Homework",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                if (role == "Teacher") {
                    Button(
                        onClick = onAddHomeworkClick,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(Icons.Default.Add, null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(Translations.get("add_homework", lang), fontSize = 11.sp)
                    }
                }
            }
        }

        val classHomeworks = homeworks.filter { it.className == selectedClass }
        if (classHomeworks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Translations.get("no_homework", lang),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(classHomeworks) { hw ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = hw.subject,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "${Translations.get("due_date", lang)}: ${hw.dueDate}",
                                fontSize = 11.sp,
                                color = Color(0xFFC0392B),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = hw.description,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BooksSubTab(lang: String, selectedClass: String, onReadBook: (String) -> Unit) {
    val books = when (selectedClass) {
        "Class 5" -> listOf("Mathematics (Class 5)", "English for Today (Class 5)", "Islamic Moral Studies (Class 5)", "Bengali (Amar Bangla Boi)", "Elementary Science")
        "Class 8" -> listOf("Mathematics (Class 8)", "English Grammar & Comp (Class 8)", "Islamic & Moral Education", "Bengali Literature", "Science Inquiry")
        else -> listOf("Alphabet & Words Book", "Islamic Rhymes for Kids", "Spoken English Picture Book")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = Translations.get("nctb_books", lang),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(books) { book ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = book,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { onReadBook(book) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(Translations.get("read_book", lang), fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ExamSubTab(viewModel: MainViewModel, lang: String) {
    val qIndex by viewModel.quizQuestionIndex.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val completed by viewModel.quizCompleted.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Translations.get("exam_portal", lang),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (!completed) {
            val currentQ = viewModel.quizQuestions[qIndex]
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Question ${qIndex + 1} of ${viewModel.quizQuestions.size}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (lang == "bn") currentQ.questionBn else currentQ.questionEn,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    val options = if (lang == "bn") currentQ.optionsBn else currentQ.optionsEn
                    options.forEachIndexed { idx, opt ->
                        OutlinedButton(
                            onClick = { viewModel.answerQuizQuestion(idx) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = opt,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (lang == "bn") "পরীক্ষা সম্পন্ন হয়েছে!" else "Quiz Completed!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${Translations.get("amount", lang).substringBefore("(")}: $score / ${viewModel.quizQuestions.size}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (lang == "bn") {
                            "মাশাআল্লাহ! আপনি চমৎকার চেষ্টা করেছেন। নৈতিক ও সাধারণ শিক্ষায় আপনার মেধা বিকাশের পথ সুগম হোক।"
                        } else {
                            "Masha'Allah! You have put in an amazing effort. May Allah increase you in beneficial knowledge!"
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetQuiz() }) {
                        Text(if (lang == "bn") "আবার পরীক্ষা দিন" else "Retry Exam")
                    }
                }
            }
        }
    }
}

@Composable
fun ResultsSubTab(
    viewModel: MainViewModel,
    lang: String,
    role: String,
    selectedClass: String,
    students: List<Student>,
    onEnterResultClick: (Student) -> Unit
) {
    val classStudents = students.filter { it.className == selectedClass }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = Translations.get("result_processing", lang) + " ($selectedClass)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (role == "Teacher") {
            items(classStudents) { student ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = student.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Roll: ${student.roll} | ID: ${student.id}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Button(
                                onClick = { onEnterResultClick(student) },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text(if (lang == "bn") "নম্বর দিন" else "Add Marks", fontSize = 11.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Grades: ${student.examResults}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        } else {
            // Student / Guardian perspective: view report card
            val myStudent = classStudents.firstOrNull() // Simulate logging as the first student
            if (myStudent != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "ACADEMIC REPORT CARD",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = myStudent.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Class: ${myStudent.className} | Roll: ${myStudent.roll}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(12.dp))

                            myStudent.examResults.split(",").forEach { res ->
                                if (res.contains(":")) {
                                    val parts = res.split(":")
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(parts[0].trim(), fontWeight = FontWeight.Medium)
                                        Text(
                                            text = parts[1].trim() + "/100",
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No report card available for selected class.")
                    }
                }
            }
        }
    }
}

// ------------------- ISLAMIC TRACKER -------------------
@Composable
fun IslamicTrackerScreen(
    viewModel: MainViewModel,
    lang: String,
    salatLogs: List<SalatLog>,
    duas: List<DuaItem>,
    quranProgress: QuranProgress?,
    streak: Int
) {
    var activeSubTab by remember { mutableStateOf("salat") } // salat, quran, duas
    val todayDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val todaysLog = salatLogs.find { it.date == todayDateStr } ?: SalatLog(date = todayDateStr)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Streak Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (lang == "bn") "সালাত সিলসিলা" else "Salat Consistency",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "$streak ${Translations.get("streak_days", lang)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TabRow(
            selectedTabIndex = when (activeSubTab) {
                "salat" -> 0
                "quran" -> 1
                else -> 2
            },
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = activeSubTab == "salat",
                onClick = { activeSubTab = "salat" },
                text = { Text(Translations.get("salat_log", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "quran",
                onClick = { activeSubTab = "quran" },
                text = { Text(Translations.get("quran_recitation", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "duas",
                onClick = { activeSubTab = "duas" },
                text = { Text(Translations.get("dua_memorization", lang), fontSize = 11.sp, maxLines = 1) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (activeSubTab) {
                "salat" -> SalatLogSubTab(viewModel, lang, todaysLog)
                "quran" -> QuranProgressSubTab(viewModel, lang, quranProgress, todaysLog)
                "duas" -> DuasSubTab(viewModel, lang, duas)
            }
        }
    }
}

@Composable
fun SalatLogSubTab(viewModel: MainViewModel, lang: String, todaysLog: SalatLog) {
    val prayers = listOf(
        "Fajr" to todaysLog.fajr,
        "Dhuhr" to todaysLog.dhuhr,
        "Asr" to todaysLog.asr,
        "Maghrib" to todaysLog.maghrib,
        "Isha" to todaysLog.isha
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = Translations.get("salat_log", lang),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(prayers) { pair ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (pair.second) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    if (pair.second) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.outlineVariant,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = Translations.get(pair.first.lowercase(), lang),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Switch(
                        checked = pair.second,
                        onCheckedChange = { isChecked ->
                            viewModel.logDailySalat(pair.first, isChecked)
                        },
                        modifier = Modifier.testTag("salat_switch_${pair.first.lowercase()}")
                    )
                }
            }
        }
    }
}

@Composable
fun QuranProgressSubTab(viewModel: MainViewModel, lang: String, quranProgress: QuranProgress?, todaysLog: SalatLog) {
    var minutesText by remember { mutableStateOf("") }
    var pagesText by remember { mutableStateOf("") }

    var lastSurah by remember { mutableStateOf(quranProgress?.lastSurah ?: "Al-Baqarah") }
    var lastAyah by remember { mutableStateOf(quranProgress?.lastAyah?.toString() ?: "1") }
    var completedSurahs by remember { mutableStateOf(quranProgress?.completedSurahs ?: "") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Display Current Status
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Translations.get("quran_recitation", lang).uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "${Translations.get("last_read", lang)}:",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${quranProgress?.lastSurah ?: "Al-Fatihah"} : Ayah ${quranProgress?.lastAyah ?: 1}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${Translations.get("completed_surahs", lang)}: ${quranProgress?.completedSurahs ?: ""}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Daily Tracker Input
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Translations.get("log_reading", lang),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = minutesText,
                            onValueChange = { minutesText = it },
                            label = { Text("Minutes / মিনিট") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = pagesText,
                            onValueChange = { pagesText = it },
                            label = { Text("Pages / পৃষ্ঠা") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val mins = minutesText.toIntOrNull() ?: 0
                            val pgs = pagesText.toIntOrNull() ?: 0
                            if (mins > 0 || pgs > 0) {
                                viewModel.logQuranMinutes(mins, pgs)
                                minutesText = ""
                                pagesText = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(Translations.get("log_reading", lang))
                    }
                }
            }
        }

        // Update Target / Last Read Form
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (lang == "bn") "সর্বশেষ পঠিত সূচী আপডেট করুন" else "Update Reading Coordinates",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = lastSurah,
                        onValueChange = { lastSurah = it },
                        label = { Text("Surah / সূরা") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = lastAyah,
                        onValueChange = { lastAyah = it },
                        label = { Text("Ayah / আয়াত") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = completedSurahs,
                        onValueChange = { completedSurahs = it },
                        label = { Text("Completed Surahs (Separated by comma)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val ayahInt = lastAyah.toIntOrNull() ?: 1
                            viewModel.saveQuranProgress(lastSurah, ayahInt, completedSurahs)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (lang == "bn") "সংরক্ষণ করুন" else "Save Progress")
                    }
                }
            }
        }
    }
}

@Composable
fun DuasSubTab(viewModel: MainViewModel, lang: String, duas: List<DuaItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = Translations.get("dua_memorization", lang),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(duas) { dua ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (dua.isMemorized) MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.15f)
                    else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dua #${dua.id}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (dua.isMemorized) Translations.get("memorized", lang) else "Learning / শিখছি",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (dua.isMemorized) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Checkbox(
                                checked = dua.isMemorized,
                                onCheckedChange = { viewModel.toggleDuaMemorized(dua) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = dua.arabic,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (lang == "bn") dua.bengaliTranslation else dua.englishTranslation,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${if (lang == "bn") "ফজিলত" else "Benefit"}: ${dua.benefit}",
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ------------------- FINANCIAL PORTAL -------------------
@Composable
fun FinancialPortalScreen(
    viewModel: MainViewModel,
    lang: String,
    role: String,
    payments: List<FeePayment>
) {
    var selectedMethod by remember { mutableStateOf("bKash") }
    var billingMonth by remember { mutableStateOf("July 2026") }
    var amountText by remember { mutableStateOf("2500") }
    var accountNo by remember { mutableStateOf("") }
    var pinText by remember { mutableStateOf("") }

    var paymentReceiptNo by remember { mutableStateOf<String?>(null) }
    var showReceiptDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = Translations.get("financial_portal", lang),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (role == "Administrator") {
            AdminFinancialOversight(lang, payments)
        } else if (role == "Teacher") {
            TeacherSalarySlip(lang)
        } else {
            // Guardian / Student perspective: Pay School Fees
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Translations.get("mobile_banking", lang),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Billing Month dropdown placeholder
                    OutlinedTextField(
                        value = billingMonth,
                        onValueChange = { billingMonth = it },
                        label = { Text("Billing Month / বিল মাস") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Amount (Fixed demo values for simplicity)
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text(Translations.get("amount", lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Payment Methods Row
                    Text(
                        text = Translations.get("payment_method", lang),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("bKash", "Nagad", "Rocket").forEach { method ->
                            val isSelected = selectedMethod == method
                            Button(
                                onClick = { selectedMethod = method },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (isSelected) Color.White
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(method, fontSize = 12.sp)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = accountNo,
                        onValueChange = { accountNo = it },
                        label = { Text(Translations.get("account_no", lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = pinText,
                        onValueChange = { pinText = it },
                        label = { Text("PIN / পিন নম্বর") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val amount = amountText.toDoubleOrNull()
                            if (amount != null && accountNo.isNotEmpty() && pinText.isNotEmpty()) {
                                val receipt = viewModel.processFeePayment(billingMonth, amount, selectedMethod, accountNo)
                                paymentReceiptNo = receipt
                                showReceiptDialog = true
                                accountNo = ""
                                pinText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pay_fees_button")
                    ) {
                        Text(
                            text = Translations.get("pay_now", lang),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fee payment receipts
            Text(
                text = Translations.get("fee_receipts", lang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            payments.forEach { payment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = payment.billingMonth,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "BDT ${payment.amount}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "TXID: ${payment.transactionId} (${payment.method})",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = payment.paymentDate,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    // Invoice Dialog
    if (showReceiptDialog && paymentReceiptNo != null) {
        Dialog(onDismissRequest = { showReceiptDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "PAYMENT SUCCESSFUL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Shifa International School",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Receipt No:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(paymentReceiptNo!!, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Amount Paid:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("BDT $amountText.00", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Mobile Method:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(selectedMethod, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // Simulated Barcode
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.White)
                            .border(1.dp, Color.LightGray)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeWidth = 3f
                            var xOffset = 20f
                            while (xOffset < size.width - 20f) {
                                val barWidth = if (Math.random() > 0.5) 10f else 4f
                                drawRect(
                                    color = Color.Black,
                                    topLeft = Offset(xOffset, 5f),
                                    size = androidx.compose.ui.geometry.Size(barWidth, size.height - 10f)
                                )
                                xOffset += barWidth + 6f
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showReceiptDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherSalarySlip(lang: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = Translations.get("salary_mgmt", lang).uppercase(),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Salary Statement: June 2026",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            val breakdown = listOf(
                "Basic Pay" to "BDT 18,500.00",
                "MFA Allowance" to "BDT 2,500.00",
                "Provident Fund" to "BDT -1,500.00",
                "Tax Deduction" to "BDT 0.00",
                "Net Salary" to "BDT 19,500.00"
            )

            breakdown.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.first,
                        fontWeight = if (item.first == "Net Salary") FontWeight.Bold else FontWeight.Normal,
                        color = if (item.first == "Net Salary") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = item.second,
                        fontWeight = FontWeight.Bold,
                        color = if (item.first == "Net Salary") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment Status:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .background(Color(0xFF27AE60), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "PAID (bKash Corporate)",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AdminFinancialOversight(lang: String, payments: List<FeePayment>) {
    val totalRevenue = payments.sumOf { it.amount } + 38400.0 // Including demo base
    val totalExpenses = 28500.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = Translations.get("financial_oversight", lang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F8F5))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Total Income", fontSize = 11.sp, color = Color(0xFF117A65))
                        Text("BDT $totalRevenue", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF117A65))
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFADBD8))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Total Expenses", fontSize = 11.sp, color = Color(0xFF922B21))
                        Text("BDT $totalExpenses", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF922B21))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Beautiful Custom Jetpack Compose Canvas Chart
            Text(
                text = "Monthly Financial Breakdown (Canvas Chart)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                // Draw axes
                drawLine(
                    color = Color.Gray,
                    start = Offset(40f, size.height - 20f),
                    end = Offset(size.width - 20f, size.height - 20f),
                    strokeWidth = 2f
                )
                drawLine(
                    color = Color.Gray,
                    start = Offset(40f, 10f),
                    end = Offset(40f, size.height - 20f),
                    strokeWidth = 2f
                )

                // Render Bar Columns (demonstrating dynamic canvas coordinates)
                val spaceBetweenBars = (size.width - 80f) / 4
                val totalAmount = totalRevenue + totalExpenses
                val incomeHeight = ((size.height - 30f) * (totalRevenue / totalAmount)).toFloat()
                val expenseHeight = ((size.height - 30f) * (totalExpenses / totalAmount)).toFloat()

                // Bar 1: Income
                drawRect(
                    color = Color(0xFF117A65),
                    topLeft = Offset(80f, size.height - 20f - incomeHeight),
                    size = androidx.compose.ui.geometry.Size(50f, incomeHeight)
                )

                // Bar 2: Expenses
                drawRect(
                    color = Color(0xFF922B21),
                    topLeft = Offset(80f + spaceBetweenBars, size.height - 20f - expenseHeight),
                    size = androidx.compose.ui.geometry.Size(50f, expenseHeight)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).background(Color(0xFF117A65)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Income (Fees Collection)", fontSize = 10.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).background(Color(0xFF922B21)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Expenses (Teacher Salaries/Utilities)", fontSize = 10.sp)
                }
            }
        }
    }
}

// ------------------- SECURITY / ENGAGEMENT (GPS, CCTV, ADMISSION) -------------------
@Composable
fun SecurityEngagementScreen(
    viewModel: MainViewModel,
    lang: String,
    role: String,
    staff: List<StaffMember>
) {
    var activeSubTab by remember { mutableStateOf("gps") } // gps, cctv, directory, admission
    var searchQuery by remember { mutableStateOf("") }

    // Admission form states
    var applicantName by remember { mutableStateOf("") }
    var targetClass by remember { mutableStateOf("Class 1") }
    var guardianPhone by remember { mutableStateOf("") }
    var previousSchool by remember { mutableStateOf("") }
    var admissionSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (lang == "bn") "ডিজিটাল সুরক্ষা ও সহযোগিতা" else "Digital Campus Safety & Support",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Subtabs
        TabRow(
            selectedTabIndex = when (activeSubTab) {
                "gps" -> 0
                "cctv" -> 1
                "directory" -> 2
                else -> 3
            },
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = activeSubTab == "gps",
                onClick = { activeSubTab = "gps" },
                text = { Text(Translations.get("van_tracking", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "cctv",
                onClick = { activeSubTab = "cctv" },
                text = { Text(Translations.get("cctv_streams", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "directory",
                onClick = { activeSubTab = "directory" },
                text = { Text(Translations.get("staff_directory", lang), fontSize = 11.sp, maxLines = 1) }
            )
            Tab(
                selected = activeSubTab == "admission",
                onClick = { activeSubTab = "admission" },
                text = { Text(if (lang == "bn") "ভর্তি ফর্ম" else "Admission", fontSize = 11.sp, maxLines = 1) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (activeSubTab) {
                "gps" -> {
                    GpsTrackingSubTab(viewModel, lang)
                }
                "cctv" -> {
                    CctvAccessSubTab(viewModel, lang, role)
                }
                "directory" -> {
                    StaffDirectorySubTab(lang, staff, searchQuery, onQueryChange = { searchQuery = it })
                }
                "admission" -> {
                    AdmissionSubTab(
                        lang = lang,
                        applicantName = applicantName,
                        targetClass = targetClass,
                        guardianPhone = guardianPhone,
                        previousSchool = previousSchool,
                        admissionSuccess = admissionSuccess,
                        onNameChange = { applicantName = it },
                        onClassChange = { targetClass = it },
                        onPhoneChange = { guardianPhone = it },
                        onSchoolChange = { previousSchool = it },
                        onSubmit = {
                            if (applicantName.isNotEmpty() && guardianPhone.isNotEmpty()) {
                                viewModel.insertStudent(
                                    Student(
                                        id = "SIS-2026-0${(100..999).random()}",
                                        name = applicantName,
                                        className = targetClass,
                                        roll = (15..45).random()
                                    )
                                )
                                admissionSuccess = true
                                applicantName = ""
                                guardianPhone = ""
                                previousSchool = ""
                            }
                        },
                        onReset = { admissionSuccess = false }
                    )
                }
            }
        }
    }
}

@Composable
fun GpsTrackingSubTab(viewModel: MainViewModel, lang: String) {
    val vanX by viewModel.vanPositionX.collectAsState()
    val vanY by viewModel.vanPositionY.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = Translations.get("active_van", lang),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Canvas Map
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.primaryContainer)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Background Terrain
                drawRect(color = Color(0xFFEAEDED))

                // Draw roads
                // Siddhirganj Main Road
                drawLine(
                    color = Color.White,
                    start = Offset(0f, size.height * 0.4f),
                    end = Offset(size.width, size.height * 0.45f),
                    strokeWidth = 36f
                )
                drawLine(
                    color = Color(0xFFBDC3C7),
                    start = Offset(0f, size.height * 0.4f),
                    end = Offset(size.width, size.height * 0.45f),
                    strokeWidth = 30f
                )

                // Link Road to School
                drawLine(
                    color = Color.White,
                    start = Offset(size.width * 0.5f, size.height * 0.43f),
                    end = Offset(size.width * 0.55f, size.height),
                    strokeWidth = 24f
                )
                drawLine(
                    color = Color(0xFFBDC3C7),
                    start = Offset(size.width * 0.5f, size.height * 0.43f),
                    end = Offset(size.width * 0.55f, size.height),
                    strokeWidth = 18f
                )

                // School Area
                drawCircle(
                    color = Color(0xFF154360),
                    radius = 35f,
                    center = Offset(size.width * 0.55f, size.height * 0.85f)
                )

                // Moving Van Marker representing GPS Telemetry
                drawCircle(
                    color = Color(0xFFF1C40F),
                    radius = 12f,
                    center = Offset(size.width * vanX, size.height * vanY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 6f,
                    center = Offset(size.width * vanX, size.height * vanY)
                )
            }

            // Map HUD labels
            Text(
                text = "Shifa School Campus",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 26.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "${Translations.get("online", lang)} | Speed: 24 km/h",
                    color = Color.Green,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.DirectionsBus, null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Van Route: Muktinagar - Siddhirganj",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Driver: Brother Rahim | Contact: +880 1799-887766",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun CctvAccessSubTab(viewModel: MainViewModel, lang: String, role: String) {
    val activeCam by viewModel.activeCamera.collectAsState()
    val cameras = listOf(
        Translations.get("camera_gate", lang) to "Main Gate",
        Translations.get("camera_library", lang) to "E-Library",
        Translations.get("camera_staff", lang) to "Staff Room",
        Translations.get("camera_class", lang) to "Class 5A"
    )

    if (role != "Administrator") {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Lock, null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (lang == "bn") "অ্যাক্সেস অস্বীকার করা হয়েছে। কেবল প্রশাসকদের জন্য।" else "CCTV Access is restricted to Administrators.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Active Camera Feed
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .border(2.dp, Color.Red)
        ) {
            // Simulated CRT Scan Line Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Static Lines
                var yOffset = 0f
                while (yOffset < size.height) {
                    drawLine(
                        color = Color.White.copy(alpha = 0.05f),
                        start = Offset(0f, yOffset),
                        end = Offset(size.width, yOffset),
                        strokeWidth = 2f
                    )
                    yOffset += 8f
                }
            }

            // CCTV Details
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "REC",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "CAM - $activeCam",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1080P | 30 FPS", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                    Text("03 JUL 2026 14:50:00", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CCTV Camera list to select
        Text(
            text = Translations.get("cctv_access", lang),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cameras.size) { idx ->
                val cam = cameras[idx]
                val isSelected = activeCam == cam.second
                Card(
                    modifier = Modifier
                        .clickable { viewModel.setActiveCamera(cam.second) }
                        .height(60.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cam.first,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StaffDirectorySubTab(
    lang: String,
    staff: List<StaffMember>,
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    val filteredStaff = staff.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.designation.contains(searchQuery, ignoreCase = true) ||
        it.subject.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = { Text(if (lang == "bn") "শিক্ষক বা পদবি খুঁজুন..." else "Search staff directory...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredStaff) { member ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = member.name.take(1),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = member.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "${member.designation} (${member.subject})",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Contact: ${member.phone} | ${member.email}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdmissionSubTab(
    lang: String,
    applicantName: String,
    targetClass: String,
    guardianPhone: String,
    previousSchool: String,
    admissionSuccess: Boolean,
    onNameChange: (String) -> Unit,
    onClassChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSchoolChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onReset: () -> Unit
) {
    if (admissionSuccess) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = Translations.get("submit_success", lang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (lang == "bn") {
                        "ভর্তির আবেদনটি সফলভাবে জমা নেওয়া হয়েছে। স্কুল প্রশাসনিক টিম শীঘ্রই আপনার সাথে যোগাযোগ করবে।"
                    } else {
                        "The application for admission has been registered. Our campus administration will get in touch soon."
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onReset) {
                    Text(if (lang == "bn") "আরেকটি আবেদন করুন" else "Apply Again")
                }
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (lang == "bn") "অনলাইন ভর্তি ফর্ম" else "Online Admission Request",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = applicantName,
            onValueChange = onNameChange,
            label = { Text(if (lang == "bn") "শিক্ষার্থীর নাম" else "Student's Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = targetClass,
            onValueChange = onClassChange,
            label = { Text(if (lang == "bn") "কাঙ্ক্ষিত শ্রেণি" else "Target Class (Pre-Play to 10)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = guardianPhone,
            onValueChange = onPhoneChange,
            label = { Text(if (lang == "bn") "অভিভাবকের মোবাইল নম্বর" else "Guardian's Phone No") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = previousSchool,
            onValueChange = onSchoolChange,
            label = { Text(if (lang == "bn") "পূর্ববর্তী স্কুল (যদি থাকে)" else "Previous School (if any)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSubmit,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (lang == "bn") "আবেদন সাবমিট করুন" else "Submit Application")
        }
    }
}

// ------------------- ADMINISTRATOR PANEL -------------------
@Composable
fun AdminPanelScreen(
    viewModel: MainViewModel,
    lang: String,
    notices: List<Notice>,
    students: List<Student>,
    staff: List<StaffMember>
) {
    var noticeTitle by remember { mutableStateOf("") }
    var noticeContent by remember { mutableStateOf("") }
    var noticeAudience by remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Administrator Controls",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Publish Notices Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Translations.get("publish_notice", lang),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = noticeTitle,
                    onValueChange = { noticeTitle = it },
                    label = { Text(Translations.get("notice_title", lang)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = noticeContent,
                    onValueChange = { noticeContent = it },
                    label = { Text(Translations.get("notice_content", lang)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Target Audience
                Text(
                    text = Translations.get("notice_audience", lang),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("ALL", "TEACHER", "GUARDIAN", "STUDENT").forEach { aud ->
                        val isSelected = noticeAudience == aud
                        Button(
                            onClick = { noticeAudience = aud },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) Color.White
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(aud, fontSize = 10.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (noticeTitle.isNotEmpty() && noticeContent.isNotEmpty()) {
                            viewModel.publishNotice(noticeTitle, noticeContent, noticeAudience)
                            noticeTitle = ""
                            noticeContent = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(Translations.get("publish", lang))
                }
            }
        }

        // Quick Stats / Campus Management Overview
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Translations.get("manage_staff_students", lang),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = students.size.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (lang == "bn") "মোট শিক্ষার্থী" else "Total Students",
                            fontSize = 12.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = staff.size.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (lang == "bn") "মোট শিক্ষক ও স্টাফ" else "Total Teachers/Staff",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
