package com.example.w4btvn1

data class Sach(
    val id: Int,
    val tenSach: String
)

data class SinhVien(
    val id: Int,
    val tenSV: String,
    val sachDaMuon: List<Sach> = emptyList() // Mặc định là một danh sách rỗng
)