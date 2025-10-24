@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.w4btvn2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.w4btvn2.R

@Composable
fun VerificationScreen(navController: NavController, email: String?) {
    val defaultVerificationCode = "123456"
    var code by remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = remember { List(6) { FocusRequester() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Xác thực") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
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
                text = "Xác thực mã",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Nhập 6 số chúng tôi đã gửi đến email:",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            email?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                code.forEachIndexed { index, digit ->
                    OutlinedTextField(
                        value = digit,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1) {
                                val newCode = code.toMutableList()
                                newCode[index] = newValue
                                code = newCode
                                if (newValue.isNotEmpty() && index < 5) {
                                    focusRequesters[index + 1].requestFocus()
                                } else if (newValue.isEmpty() && index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp).focusRequester(focusRequesters[index]),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    val enteredCode = code.joinToString("")
                    if (enteredCode == defaultVerificationCode) {
                        navController.navigate("createNewPassword_screen/$email")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Tiếp tục", fontSize = 18.sp)
            }
        }
    }
}