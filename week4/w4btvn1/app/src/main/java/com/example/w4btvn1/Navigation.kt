package com.example.w4btvn1

sealed class Screen(val route: String, val title: String) {
    object QuanLy : Screen("quan_ly", "Quản lý")
    object DsSach : Screen("ds_sach", "DS Sách")
    object SinhVien : Screen("sinh_vien", "Sinh viên")
}