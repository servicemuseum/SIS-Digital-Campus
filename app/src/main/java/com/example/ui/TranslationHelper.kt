package com.example.ui

object Translations {
    private val en = mapOf(
        "app_name" to "SIS Digital Campus",
        "school_name" to "Shifa International School",
        "school_location" to "Muktinagar, Siddhirganj, Narayanganj",
        "established" to "Est. 2020",
        "principal" to "Principal: Md. Abdul Mannan",
        "curriculum" to "Curriculum: NCTB & Islamic Moral Education",
        "contact_info" to "Contact: info@shifaintschool.com | www.shifaintschool.com",
        "role_label" to "Active Role",
        "change_role" to "Switch Role",
        "notice_board" to "Notice Board",
        "publish_notice" to "Publish Notice",
        "academic_mgmt" to "Academic Management",
        "islamic_tracker" to "Islamic Tracker",
        "admin_portal" to "Administration",
        "financial_portal" to "Financial Portal",
        "engagement_resources" to "Engagement",
        
        // Navigation Tabs
        "tab_home" to "Home",
        "tab_academic" to "Academic",
        "tab_islamic" to "Islamic",
        "tab_finance" to "Finance",
        "tab_admin" to "Admin",

        // Actions
        "pay_fees" to "Pay Fees",
        "mark_attendance" to "Mark Attendance",
        "enter_results" to "Enter Results",
        "cctv_streams" to "CCTV Live",
        "add_homework" to "Add Homework",
        "submit_assignment" to "Submit Assignment",
        "van_tracking" to "Van Tracking",
        "staff_directory" to "Staff Directory",

        // Islamic
        "salat_log" to "Daily Salat Log",
        "fajr" to "Fajr",
        "dhuhr" to "Dhuhr",
        "asr" to "Asr",
        "maghrib" to "Maghrib",
        "isha" to "Isha",
        "quran_recitation" to "Quran Recitation Progress",
        "dua_memorization" to "Dua Memorization Checklist",
        "moral_education_reports" to "Moral Education Reports",
        "last_read" to "Last Read",
        "memorized" to "Memorized",
        "mark_memorized" to "Mark as Memorized",
        "completed_surahs" to "Completed Surahs",
        "log_reading" to "Log Reading Minutes",
        
        // Academic
        "class_routine" to "Digital Class Routine",
        "nctb_books" to "NCTB E-Book Integration",
        "exam_portal" to "Online Exam Portal",
        "result_processing" to "Result Processing",
        "read_book" to "Read E-Book",
        "spoken_english" to "Spoken English Audio Clips",
        "multimedia_resources" to "Multimedia Resources",

        // Financial
        "mobile_banking" to "Mobile Banking Payment",
        "fee_receipts" to "Fee Receipts",
        "salary_mgmt" to "Salary Management",
        "expense_tracking" to "Expense Tracking",
        "payment_method" to "Payment Method",
        "account_no" to "Mobile Account No",
        "amount" to "Amount (BDT)",
        "transaction_id" to "Transaction ID",
        "pay_now" to "Pay Now",
        "receipt_no" to "Receipt No",
        "payment_date" to "Date",
        "status" to "Status",

        // Admin
        "manage_staff_students" to "Manage Staff & Students",
        "financial_oversight" to "Financial Oversight",
        "cctv_access" to "Live CCTV Feeds",
        "camera_gate" to "Main Gate Camera",
        "camera_library" to "E-Library Camera",
        "camera_staff" to "Staff Room Camera",
        "camera_class" to "Class 5A Camera",
        "notice_title" to "Notice Title",
        "notice_content" to "Notice Content",
        "notice_audience" to "Target Audience",
        "publish" to "Publish Announcement",
        
        // Miscellaneous
        "select_class" to "Select Class",
        "streak_days" to "Days Streak",
        "attendance_rate" to "Attendance Rate",
        "active_van" to "GPS School Van Live Tracking",
        "no_homework" to "No homework assigned yet.",
        "due_date" to "Due Date",
        "submit_success" to "Submitted Successfully!",
        "english" to "English",
        "bengali" to "বাংলা",
        "online" to "LIVE",
        "offline" to "OFFLINE"
    )

    private val bn = mapOf(
        "app_name" to "এসআইএস ডিজিটাল ক্যাম্পাস",
        "school_name" to "শিফা ইন্টারন্যাশনাল স্কুল",
        "school_location" to "মুক্তিনগর, সিদ্ধিরগঞ্জ, নারায়ণগঞ্জ",
        "established" to "প্রতিষ্ঠিত: ২০২০",
        "principal" to "অধ্যক্ষ: মোঃ আব্দুল মান্নান",
        "curriculum" to "কারিকুলাম: এনসিটিবি এবং ইসলামিক নৈতিক শিক্ষা",
        "contact_info" to "যোগাযোগ: info@shifaintschool.com | www.shifaintschool.com",
        "role_label" to "সক্রিয় ভূমিকা",
        "change_role" to "ভূমিকা পরিবর্তন",
        "notice_board" to "নোটিশ বোর্ড",
        "publish_notice" to "নোটিশ প্রকাশ",
        "academic_mgmt" to "একাডেমিক ব্যবস্থাপনা",
        "islamic_tracker" to "ইসলামিক ট্র্যাকার",
        "admin_portal" to "প্রশাসনিক পোর্টাল",
        "financial_portal" to "আর্থিক পোর্টাল",
        "engagement_resources" to "অনলাইন যোগাযোগ",
        
        // Navigation Tabs
        "tab_home" to "হোম",
        "tab_academic" to "একাডেমিক",
        "tab_islamic" to "ইসলামিক",
        "tab_finance" to "আর্থিক",
        "tab_admin" to "প্রশাসন",

        // Actions
        "pay_fees" to "ফি প্রদান",
        "mark_attendance" to "উপস্থিতি চিহ্নিতকরণ",
        "enter_results" to "ফলাফল এন্ট্রি",
        "cctv_streams" to "লাইভ সিসিটিভি",
        "add_homework" to "হোমওয়ার্ক যোগ",
        "submit_assignment" to "অ্যাসাইনমেন্ট জমা",
        "van_tracking" to "ভ্যান ট্র্যাকিং",
        "staff_directory" to "স্টাফ ডিরেক্টরি",

        // Islamic
        "salat_log" to "দৈনিক সালাত লগ",
        "fajr" to "ফজর",
        "dhuhr" to "যোহর",
        "asr" to "আসর",
        "maghrib" to "মাগরিব",
        "isha" to "এশা",
        "quran_recitation" to "কুরআন তিলওয়াত অগ্রগতি",
        "dua_memorization" to "দুয়া মুখস্থকরণ তালিকা",
        "moral_education_reports" to "নৈতিক শিক্ষার প্রতিবেদন",
        "last_read" to "সর্বশেষ পঠিত",
        "memorized" to "মুখস্থ করা হয়েছে",
        "mark_memorized" to "মুখস্থ হিসেবে চিহ্নিত করুন",
        "completed_surahs" to "সম্পূর্ণ সূরাসমূহ",
        "log_reading" to "পড়ার সময় যুক্ত করুন",

        // Academic
        "class_routine" to "ডিজিটাল ক্লাস রুটিন",
        "nctb_books" to "এনসিটিবি ই-বুক ইন্টিগ্রেশন",
        "exam_portal" to "অনলাইন পরীক্ষা পোর্টাল",
        "result_processing" to "ফলাফল প্রক্রিয়াকরণ",
        "read_book" to "ই-বুক পড়ুন",
        "spoken_english" to "স্পোকেন ইংলিশ অডিও ক্লিপ",
        "multimedia_resources" to "মাল্টিমিডিয়া রিসোর্স",

        // Financial
        "mobile_banking" to "মোবাইল ব্যাংকিং পেমেন্ট",
        "fee_receipts" to "ফি রসিদ",
        "salary_mgmt" to "বেতন ব্যবস্থাপনা",
        "expense_tracking" to "ব্যয় ট্র্যাকিং",
        "payment_method" to "পেমেন্ট পদ্ধতি",
        "account_no" to "মোবাইল অ্যাকাউন্ট নম্বর",
        "amount" to "পরিমাণ (টাকা)",
        "transaction_id" to "ট্রানজেকশন আইডি",
        "pay_now" to "পেমেন্ট করুন",
        "receipt_no" to "রসিদ নম্বর",
        "payment_date" to "তারিখ",
        "status" to "অবস্থা",

        // Admin
        "manage_staff_students" to "শিক্ষক ও ছাত্র ব্যবস্থাপনা",
        "financial_oversight" to "আর্থিক পর্যবেক্ষণ",
        "cctv_access" to "লাইভ সিসিটিভি ফিড",
        "camera_gate" to "প্রধান ফটক ক্যামেরা",
        "camera_library" to "ই-লাইব্রেরি ক্যামেরা",
        "camera_staff" to "শিক্ষক কক্ষ ক্যামেরা",
        "camera_class" to "ক্লাস ৫এ ক্যামেরা",
        "notice_title" to "নোটিশের শিরোনাম",
        "notice_content" to "নোটিশের বিবরণ",
        "notice_audience" to "টার্গেট ভূমিকা",
        "publish" to "নোটিশ প্রকাশ করুন",

        // Miscellaneous
        "select_class" to "শ্রেণি নির্বাচন",
        "streak_days" to "দিন একটানা",
        "attendance_rate" to "উপস্থিতির হার",
        "active_van" to "জিপিএস স্কুল ভ্যান লাইভ ট্র্যাকিং",
        "no_homework" to "কোন হোমওয়ার্ক দেওয়া হয়নি।",
        "due_date" to "জমা দেওয়ার শেষ তারিখ",
        "submit_success" to "সফলভাবে জমা দেওয়া হয়েছে!",
        "english" to "English",
        "bengali" to "বাংলা",
        "online" to "লাইভ",
        "offline" to "অফলাইন"
    )

    fun get(key: String, lang: String): String {
        return if (lang == "bn") {
            bn[key] ?: en[key] ?: key
        } else {
            en[key] ?: key
        }
    }
}
