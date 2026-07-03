package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.*

class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val lang by viewModel.language.collectAsState()
        val role by viewModel.currentRole.collectAsState()
        val activeTab by viewModel.currentTab.collectAsState()

        val notices by viewModel.notices.collectAsState()
        val salatLogs by viewModel.salatLogs.collectAsState()
        val quranProgress by viewModel.quranProgress.collectAsState()
        val students by viewModel.students.collectAsState()
        val staff by viewModel.staff.collectAsState()
        val payments by viewModel.payments.collectAsState()
        val homeworks by viewModel.homeworks.collectAsState()
        val duas by viewModel.duas.collectAsState()
        val streak by viewModel.salatStreak.collectAsState()
        val selectedClass by viewModel.selectedClass.collectAsState()

        var showRoleSelectorDialog by remember { mutableStateOf(false) }

        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            TopAppBar(
              title = {
                Column {
                  Text(
                    text = Translations.get("app_name", lang),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                  )
                  Text(
                    text = Translations.get("school_name", lang),
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              },
              actions = {
                // Language Switcher Badge
                FilledTonalButton(
                  onClick = { viewModel.toggleLanguage() },
                  modifier = Modifier
                    .padding(end = 8.dp)
                    .height(36.dp)
                    .testTag("lang_toggle_button"),
                  contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = if (lang == "en") "বাংলা" else "English",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                }

                // Active Role Profile Badge
                IconButton(
                  onClick = { showRoleSelectorDialog = true },
                  modifier = Modifier.testTag("role_badge_button")
                ) {
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = when (role) {
                        "Administrator" -> Icons.Default.AdminPanelSettings
                        "Teacher" -> Icons.Default.School
                        "Guardian" -> Icons.Default.SupervisorAccount
                        else -> Icons.Default.Person
                      },
                      contentDescription = "Change Role",
                      tint = MaterialTheme.colorScheme.primary,
                      modifier = Modifier.size(20.dp)
                    )
                  }
                }
              },
              colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
              )
            )
          },
          bottomBar = {
            NavigationBar(
              modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
              val homeLabel = Translations.get("tab_home", lang)
              NavigationBarItem(
                selected = activeTab == "home",
                onClick = { viewModel.setTab("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = homeLabel) },
                label = { Text(homeLabel, fontSize = 10.sp, maxLines = 1) },
                modifier = Modifier.testTag("nav_home")
              )

              val acadLabel = Translations.get("tab_academic", lang)
              NavigationBarItem(
                selected = activeTab == "academic",
                onClick = { viewModel.setTab("academic") },
                icon = { Icon(Icons.Default.AutoStories, contentDescription = acadLabel) },
                label = { Text(acadLabel, fontSize = 10.sp, maxLines = 1) },
                modifier = Modifier.testTag("nav_academic")
              )

              val islamicLabel = Translations.get("tab_islamic", lang)
              NavigationBarItem(
                selected = activeTab == "islamic",
                onClick = { viewModel.setTab("islamic") },
                icon = { Icon(Icons.Default.Mosque, contentDescription = islamicLabel) },
                label = { Text(islamicLabel, fontSize = 10.sp, maxLines = 1) },
                modifier = Modifier.testTag("nav_islamic")
              )

              val financeLabel = Translations.get("tab_finance", lang)
              NavigationBarItem(
                selected = activeTab == "finance",
                onClick = { viewModel.setTab("finance") },
                icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = financeLabel) },
                label = { Text(financeLabel, fontSize = 10.sp, maxLines = 1) },
                modifier = Modifier.testTag("nav_finance")
              )

              // Security & Support Navigation Item
              val safetyLabel = if (lang == "bn") "সুরক্ষা" else "Safety"
              NavigationBarItem(
                selected = activeTab == "safety",
                onClick = { viewModel.setTab("safety") },
                icon = { Icon(Icons.Default.Shield, contentDescription = safetyLabel) },
                label = { Text(safetyLabel, fontSize = 10.sp, maxLines = 1) },
                modifier = Modifier.testTag("nav_safety")
              )

              // Conditional Admin Panel tab (only visible or enabled if Administrator)
              if (role == "Administrator") {
                val adminLabel = Translations.get("tab_admin", lang)
                NavigationBarItem(
                  selected = activeTab == "admin",
                  onClick = { viewModel.setTab("admin") },
                  icon = { Icon(Icons.Default.Settings, contentDescription = adminLabel) },
                  label = { Text(adminLabel, fontSize = 10.sp, maxLines = 1) },
                  modifier = Modifier.testTag("nav_admin")
                )
              }
            }
          }
        ) { innerPadding ->
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding)
          ) {
            ProfileCard(lang = lang, role = role, onSwitchRoleClick = {
              showRoleSelectorDialog = true
            })

            Box(modifier = Modifier.weight(1f)) {
              when (activeTab) {
                "home" -> HomeScreen(
                  viewModel = viewModel,
                  lang = lang,
                  notices = notices,
                  role = role,
                  onNavigateToTab = { tab -> viewModel.setTab(tab) }
                )
                "academic" -> AcademicScreen(
                  viewModel = viewModel,
                  lang = lang,
                  role = role,
                  selectedClass = selectedClass,
                  students = students,
                  homeworks = homeworks
                )
                "islamic" -> IslamicTrackerScreen(
                  viewModel = viewModel,
                  lang = lang,
                  salatLogs = salatLogs,
                  duas = duas,
                  quranProgress = quranProgress,
                  streak = streak
                )
                "finance" -> FinancialPortalScreen(
                  viewModel = viewModel,
                  lang = lang,
                  role = role,
                  payments = payments
                )
                "safety" -> SecurityEngagementScreen(
                  viewModel = viewModel,
                  lang = lang,
                  role = role,
                  staff = staff
                )
                "admin" -> {
                  if (role == "Administrator") {
                    AdminPanelScreen(
                      viewModel = viewModel,
                      lang = lang,
                      notices = notices,
                      students = students,
                      staff = staff
                    )
                  } else {
                    // Fallback to home if they switch role while on this tab
                    LaunchedEffect(Unit) {
                      viewModel.setTab("home")
                    }
                  }
                }
              }
            }
          }
        }

        // Beautiful Dialog for selecting Role Profile
        if (showRoleSelectorDialog) {
          Dialog(onDismissRequest = { showRoleSelectorDialog = false }) {
            Card(
              shape = RoundedCornerShape(24.dp),
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
              Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(
                  text = if (lang == "bn") "ভূমিকা নির্বাচন করুন" else "Select User Profile",
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = if (lang == "bn") "সব মডিউল ও পারমিশন পরীক্ষা করার জন্য ভূমিকাটি পরিবর্তন করুন" else "Switch role to test all modules & access privileges",
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                val roles = listOf(
                  RoleOption("Student", if (lang == "bn") "শিক্ষার্থী (Student)" else "Student", Icons.Default.Person, "Salat logs, E-Books, Exam Portal"),
                  RoleOption("Guardian", if (lang == "bn") "অভিভাবক (Guardian)" else "Guardian", Icons.Default.SupervisorAccount, "Fee Payment, Van GPS, Reports"),
                  RoleOption("Teacher", if (lang == "bn") "শিক্ষক (Teacher)" else "Teacher", Icons.Default.School, "Mark Attendance, Results, Homework"),
                  RoleOption("Administrator", if (lang == "bn") "প্রশাসক (Admin)" else "Administrator", Icons.Default.AdminPanelSettings, "Live CCTV, Notice Board, Finances")
                )

                LazyVerticalGrid(
                  columns = GridCells.Fixed(2),
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                  verticalArrangement = Arrangement.spacedBy(10.dp),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  items(roles.size) { idx ->
                    val opt = roles[idx]
                    val isSelected = role == opt.key
                    Card(
                      modifier = Modifier
                        .clickable {
                          viewModel.setRole(opt.key)
                          showRoleSelectorDialog = false
                        }
                        .height(110.dp)
                        .testTag("role_option_${opt.key.lowercase()}"),
                      colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                      ),
                      border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                      )
                    ) {
                      Column(
                        modifier = Modifier
                          .fillMaxSize()
                          .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                      ) {
                        Icon(
                          imageVector = opt.icon,
                          contentDescription = opt.label,
                          tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                          modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                          text = opt.label,
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Bold,
                          textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                          text = opt.desc,
                          fontSize = 9.sp,
                          color = MaterialTheme.colorScheme.onSurfaceVariant,
                          textAlign = TextAlign.Center,
                          lineHeight = 11.sp
                        )
                      }
                    }
                  }
                }

                Spacer(modifier = Modifier.height(20.dp))
                TextButton(onClick = { showRoleSelectorDialog = false }) {
                  Text("Close")
                }
              }
            }
          }
        }
      }
    }
  }
}

data class RoleOption(
  val key: String,
  val label: String,
  val icon: androidx.compose.ui.graphics.vector.ImageVector,
  val desc: String
)
