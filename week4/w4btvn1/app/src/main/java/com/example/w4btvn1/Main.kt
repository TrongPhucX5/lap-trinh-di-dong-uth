package com.example.w4btvn1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.w4btvn1.Sach
import com.example.w4btvn1.SinhVien
import com.example.w4btvn1.Screen
import com.example.w4btvn1.QuanLyThuVienViewModel

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: QuanLyThuVienViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.QuanLy.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.QuanLy.route) {
                QuanLyScreen(viewModel = viewModel)
            }
            composable(Screen.DsSach.route) {
                DsSachScreen(danhSachSach = uiState.danhSachSach)
            }
            composable(Screen.SinhVien.route) {
                SinhVienScreen(danhSachSV = uiState.danhSachSV)
            }
        }
    }
}

/**
 * Màn hình chính: Quản lý mượn sách.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuanLyScreen(viewModel: QuanLyThuVienViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sinhVienHienTai = uiState.sinhVienHienTai
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hệ thống Quản lý Thư viện", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))

        // --- Phần chọn sinh viên sử dụng ExposedDropdownMenuBox ---
        Text("Sinh viên", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = sinhVienHienTai?.tenSV ?: "Chọn sinh viên",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                uiState.danhSachSV.forEach { sv ->
                    DropdownMenuItem(
                        text = { Text(sv.tenSV) },
                        onClick = {
                            viewModel.chonSinhVien(sv)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Phần danh sách sách ---
        Text("Danh sách sách", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.danhSachSach) { sach ->
                val isChecked = uiState.trangThaiChonSach[sach.id] ?: false
                SachRow(
                    sach = sach,
                    isChecked = isChecked,
                    onCheckedChange = { newCheckedState ->
                        viewModel.thayDoiLuaChonSach(sach.id, newCheckedState)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Nút Thêm/Cập nhật ---
        Button(
            onClick = { viewModel.capNhatSachMuon() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cập nhật", fontSize = 16.sp)
        }
    }
}

/**
 * Composable cho một hàng sách trong danh sách, bao gồm Checkbox và tên sách.
 */
@Composable
fun SachRow(sach: Sach, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = sach.tenSach, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

/**
 * Màn hình hiển thị danh sách tất cả các sách trong thư viện.
 */
@Composable
fun DsSachScreen(danhSachSach: List<Sach>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Tất cả sách trong thư viện",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        items(danhSachSach) { sach ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = sach.tenSach,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * Màn hình hiển thị danh sách sinh viên và số sách họ đã mượn.
 */
@Composable
fun SinhVienScreen(danhSachSV: List<SinhVien>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Danh sách sinh viên",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        items(danhSachSV) { sv ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = sv.tenSV,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Đã mượn: ${sv.sachDaMuon.size} quyển",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


/**
 * Thanh điều hướng dưới cùng của ứng dụng.
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.QuanLy, Screen.DsSach, Screen.SinhVien)
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    when (screen) {
                        Screen.QuanLy -> Icon(Icons.Filled.ManageSearch, contentDescription = screen.title)
                        Screen.DsSach -> Icon(Icons.Filled.MenuBook, contentDescription = screen.title)
                        Screen.SinhVien -> Icon(Icons.Filled.People, contentDescription = screen.title)
                    }
                }
            )
        }
    }
}