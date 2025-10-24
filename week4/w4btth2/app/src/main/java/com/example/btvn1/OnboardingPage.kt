package com.example.btvn1

import androidx.annotation.DrawableRes

data class OnboardingPage(
    @DrawableRes val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.image1, // Đảm bảo bạn có file image1.png trong res/drawable
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and planning for statesman that are more important."
    ),
    OnboardingPage(
        imageRes = R.drawable.image2, // Đảm bảo bạn có file image2.png trong res/drawable
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve."
    ),
    OnboardingPage(
        imageRes = R.drawable.image3, // Đảm bảo bạn có file image3.png trong res/drawable
        title = "Reminder Notification",
        description = "The advantage of this application is that it can provide reminders for you so you don't forget to keep doing your assignments and you can check them at the time you have set."
    )
)