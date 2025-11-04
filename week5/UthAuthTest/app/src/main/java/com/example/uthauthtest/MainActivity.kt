// File: com/example/uthauthtest/MainActivity.kt
package com.example.uthauthtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uthauthtest.auth.Login // <-- Import từ package 'auth'
import com.example.uthauthtest.profile.Profile // <-- Import từ package 'profile'
import com.example.uthauthtest.ui.theme.UthAuthTestTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Khởi tạo Auth và Google Client
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // !!! DÁN WEB CLIENT ID CỦA BẠN VÀO ĐÂY !!!
            .requestIdToken("481949480943-2v3d2em3qnlk5ejbk8mi7d307q62hbn1.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            UthAuthTestTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // 2. Gọi Composable Điều hướng
                    AppNavigation(
                        auth = auth,
                        googleSignInClient = googleSignInClient
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient) {

    // 3. Tạo NavController
    val navController = rememberNavController()

    // 4. Quyết định màn hình bắt đầu (đã đăng nhập hay chưa)
    val startDestination = if (auth.currentUser != null) "profile" else "login"

    // 5. Tạo NavHost (Sân khấu)
    NavHost(navController = navController, startDestination = startDestination) {

        // Màn hình "login"
        composable(route = "login") {
            Login( // <-- Gọi Composable 'Login'
                googleSignInClient = googleSignInClient,
                auth = auth,
                onSignInSuccess = {
                    // Khi đăng nhập xong, chuyển đến 'profile'
                    // và xoá 'login' khỏi lịch sử (để không back lại được)
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Màn hình "profile"
        composable(route = "profile") {
            Profile( // <-- Gọi Composable 'Profile'
                auth = auth,
                onSignOut = {
                    // Khi đăng xuất, quay về 'login'
                    auth.signOut()
                    googleSignInClient.signOut()
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }
    }
}


// 6. HÀM HỖ TRỢ FIREBASE
// (Bỏ 'private' để file Login.kt có thể gọi được)
fun firebaseAuthWithGoogle(
    idToken: String,
    auth: FirebaseAuth,
    onResult: (Boolean, String?) -> Unit
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, auth.currentUser?.displayName)
            } else {
                onResult(false, task.exception?.message)
            }
        }
}