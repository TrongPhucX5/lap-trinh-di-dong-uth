package com.example.week2pratice2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week2pratice2.ui.theme.Week2pratice2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week2pratice2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmailValidatorScreen()
                }
            }
        }
    }
}

@Composable
fun EmailValidatorScreen() {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var messageColor by remember { mutableStateOf(Color.Red) }

    fun validateEmail(input: String) {
        when {
            input.isBlank() -> {
                message = "Email không hợp lệ"
                messageColor = Color.Red
            }
            !input.contains("@") -> {
                message = "Email không đúng định dạng"
                messageColor = Color.Red
            }
            else -> {
                message = "Bạn đã nhập email hợp lệ"
                messageColor = Color(0xFF006400)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("THỰC HÀNH 02", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        message?.let {
            Text(
                text = it,
                color = messageColor,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { validateEmail(email) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiểm tra")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Week2pratice2Theme {
        EmailValidatorScreen()
    }
}
