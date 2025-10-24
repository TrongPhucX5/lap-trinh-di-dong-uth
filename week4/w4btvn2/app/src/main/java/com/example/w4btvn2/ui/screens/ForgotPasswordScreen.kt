@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.w4btvn2.ui.screens

import android.util.Patterns // << 1. IMPORT CÔNG CỤ KIỂM TRA EMAIL
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.w4btvn2.R

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    // << 2. TỰ ĐỘNG KIỂM TRA EMAIL MỖI KHI NGƯỜI DÙNG NHẬP >>
    // Biến này sẽ là `true` nếu email hợp lệ, và `false` nếu ngược lại.
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quên Mật Khẩu") },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ... Phần UI logo và tiêu đề không thay đổi
            Spacer(modifier = Modifier.height(64.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "W4BTVN2 Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "SmartTasks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Quên mật khẩu?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Nhập Email của bạn, chúng tôi sẽ gửi mã xác thực.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // << 3. CẬP NHẬT OUTLINEDTEXTFIELD ĐỂ HIỂN THỊ LỖI >>
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email của bạn") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                // `isError` sẽ là true nếu người dùng đã nhập gì đó nhưng email lại không hợp lệ
                isError = email.isNotEmpty() && !isEmailValid,
                // Hiển thị thông báo lỗi nếu `isError` là true
                supportingText = {
                    if (email.isNotEmpty() && !isEmailValid) {
                        Text("Vui lòng nhập email hợp lệ", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // << 4. CẬP NHẬT BUTTON ĐỂ BẬT/TẮT DỰA VÀO EMAIL >>
            Button(
                onClick = { navController.navigate("verification_screen/$email") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                // Nút chỉ được bật (`enabled = true`) khi email hợp lệ
                enabled = isEmailValid
            ) {
                Text("Tiếp tục", fontSize = 18.sp)
            }
        }
    }
}