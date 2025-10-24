package com.example.btvn1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OnboardingPageUI(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPageIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until pageCount) {
            val isSelected = i == currentPageIndex
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray)
            )
        }
    }
}

@Composable
fun OnboardingScreen(navController: NavController, pageIndex: Int) {
    val page = onboardingPages[pageIndex]
    val isLastPage = pageIndex == onboardingPages.lastIndex

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(pageCount = onboardingPages.size, currentPageIndex = pageIndex)
            TextButton(onClick = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.onboarding(0)) { inclusive = true }
                }
            }) {
                Text("skip")
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            OnboardingPageUI(page = page)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pageIndex > 0) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8EAF6))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            // *** THAY ĐỔI LOGIC NÚT BẤM CHÍNH NẰM Ở ĐÂY ***
            Button(
                onClick = {
                    if (isLastPage) {
                        // Quay về trang giới thiệu đầu tiên (index = 0)
                        navController.navigate(Routes.onboarding(0)) {
                            // Xóa các trang 1 và 2 khỏi lịch sử điều hướng để vòng lặp "sạch" hơn
                            popUpTo(Routes.onboarding(0)) { inclusive = true }
                        }
                    } else {
                        // Chuyển đến trang tiếp theo
                        navController.navigate(Routes.onboarding(pageIndex + 1))
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(if (isLastPage) "Get Started" else "Next")
            }
        }
    }
}