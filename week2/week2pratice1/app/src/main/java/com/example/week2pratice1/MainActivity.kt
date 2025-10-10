package com.example.week2pratice1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week2pratice1.ui.theme.Week2pratice1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week2pratice1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AgeClassifierScreen()
                }
            }
        }
    }
}

@Composable
fun AgeClassifierScreen() {
    // Các biến trạng thái để lưu trữ dữ liệu người dùng và kết quả
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // Giao diện chính sử dụng Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("THỰC HÀNH 01", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        // Khối giao diện nhập liệu (đã được tích hợp trực tiếp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.2f))
                .padding(16.dp)
        ) {
            // Trường nhập họ và tên
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // Trường nhập tuổi
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Tuổi") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(Modifier.height(32.dp))

        // Hàng chứa 2 nút "Kiểm tra" và "Xóa"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nút Kiểm tra
            Button(
                onClick = {
                    val ageInt = age.toIntOrNull()
                    result = when {
                        name.isBlank() -> "Lỗi: Vui lòng nhập họ và tên."
                        name.any { it.isDigit() } -> "Lỗi: Tên không được chứa số."
                        age.isBlank() -> "Lỗi: Vui lòng nhập tuổi."
                        ageInt == null -> "Lỗi: Tuổi phải là một con số."
                        ageInt < 0 -> "Lỗi: Tuổi không được là số âm."
                        ageInt > 65 -> "Xin chào $name, bạn là Người già."
                        ageInt in 7..65 -> "Xin chào $name, bạn là Người lớn."
                        ageInt in 2..6 -> "Xin chào $name, bạn là Trẻ em."
                        else -> "Xin chào $name, bạn là Em bé."
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Kiểm tra", fontSize = 18.sp)
            }

            // Nút Xóa
            Button(
                onClick = {
                    name = ""
                    age = ""
                    result = ""
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Xóa", fontSize = 18.sp)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Vùng hiển thị kết quả hoặc lỗi
        if (result.isNotBlank()) {
            val textColor = if (result.startsWith("Lỗi:")) {
                Color.Red
            } else {
                MaterialTheme.colorScheme.primary
            }
            Text(
                text = result,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

// Hàm Preview để xem trước giao diện trong Android Studio
@Preview(showBackground = true)
@Composable
fun AgeClassifierPreview() {
    Week2pratice1Theme {
        AgeClassifierScreen()
    }
}