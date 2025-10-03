package com.example.contactcardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactcardcompose.ui.theme.ContactCardComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactCardComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ContactCardScreen()
                }
            }
        }
    }
}

@Composable
fun ContactCardScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header nằm trên cùng
        ContactHeader(
            onBackClick = { /* TODO: xử lý quay lại */ },
            onEditClick = { /* TODO: xử lý chỉnh sửa */ }
        )

        // Nội dung chính nằm giữa màn hình
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContactCard(name = "Johan Smith", status = "California, USA")
        }
    }
}

@Composable
fun ContactHeader(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
    }
}

@Composable
fun ContactCard(name: String, status: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_avatar_placeholder),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = name, fontSize = 24.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = status, fontSize = 16.sp, color = Color.Gray)
    }
}
// Hàm ContactCardPreview dùng để xem trước UI trong Android Studio
@Preview(showBackground = true)
@Composable
fun ContactCardPreview() {
    ContactCardComposeTheme {
        ContactCardScreen()
    }
}
