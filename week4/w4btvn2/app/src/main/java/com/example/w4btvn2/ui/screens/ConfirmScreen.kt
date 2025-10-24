@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.w4btvn2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility // << THÊM IMPORT
import androidx.compose.material.icons.filled.VisibilityOff // << THÊM IMPORT
import androidx.compose.material3.*
import androidx.compose.runtime.* // << THÊM IMPORT
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation // << THÊM IMPORT
import androidx.compose.ui.text.input.VisualTransformation // << THÊM IMPORT
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.w4btvn2.R

@Composable
fun ConfirmScreen(navController: NavController, email: String?, password: String?) {

    // GHI CHÚ: Thêm 1 biến state để quản lý việc ẩn/hiện mật khẩu
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            text = "Xác nhận",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Đây là thông tin của bạn!",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Trường Email (không đổi)
        OutlinedTextField(
            value = email ?: "Không có email",
            onValueChange = {},
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            leadingIcon = {
                Icon(Icons.Default.AlternateEmail, contentDescription = "Email Icon")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Trường Mã xác thực (không đổi)
        OutlinedTextField(
            value = "123456",
            onValueChange = {},
            placeholder = { Text("Mã xác thực") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            leadingIcon = {
                Icon(Icons.Default.Password, contentDescription = "Verification Code Icon")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // GHI CHÚ: Cập nhật trường Mật khẩu
        OutlinedTextField(
            value = password ?: "Không có mật khẩu",
            onValueChange = {},
            placeholder = { Text("Mật khẩu mới") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password Icon")
            },
            // 1. Dùng visualTransformation để che mật khẩu
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            // 2. Thêm icon con mắt (trailingIcon)
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, "Toggle password visibility")
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("forgot_password_screen") {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Hoàn tất", fontSize = 18.sp)
        }
    }
}