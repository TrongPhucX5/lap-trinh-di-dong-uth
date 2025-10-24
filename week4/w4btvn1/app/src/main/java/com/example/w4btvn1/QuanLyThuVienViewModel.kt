package com.example.w4btvn1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.w4btvn1.Sach
import com.example.w4btvn1.SinhVien

/**
 * Lớp trạng thái (State) chứa tất cả dữ liệu mà giao diện (UI) cần để hiển thị.
 * Việc gom tất cả vào một data class giúp quản lý trạng thái tập trung.
 */
data class ThuVienUiState(
    val danhSachSV: List<SinhVien> = emptyList(),
    val danhSachSach: List<Sach> = emptyList(),
    val sinhVienHienTai: SinhVien? = null,
    val trangThaiChonSach: Map<Int, Boolean> = emptyMap() // Key: id sách, Value: đã chọn hay chưa
)

class QuanLyThuVienViewModel : ViewModel() {

    // `_uiState` là trạng thái nội bộ, chỉ có thể thay đổi bên trong ViewModel.
    private val _uiState = MutableStateFlow(ThuVienUiState())

    // `uiState` là phiên bản chỉ đọc của `_uiState`, được cung cấp cho UI để lắng nghe.
    val uiState: StateFlow<ThuVienUiState> = _uiState.asStateFlow()

    // Khối `init` được chạy một lần khi ViewModel được tạo ra.
    init {
        khoiTaoDuLieu()
    }

    // Hàm tạo dữ liệu mẫu cho ứng dụng.
    private fun khoiTaoDuLieu() {
        val dsSach = listOf(
            Sach(1, "Sách 01 - Lập trình Kotlin"),
            Sach(2, "Sách 02 - Jetpack Compose"),
            Sach(3, "Sách 03 - Cấu trúc dữ liệu"),
            Sach(4, "Sách 04 - Thuật toán")
        )

        // Dữ liệu mẫu với model bất biến
        val sachMuonCuaA = listOf(dsSach[0], dsSach[1])
        val sachMuonCuaB = listOf(dsSach[0])

        val svA = SinhVien(101, "Nguyễn Văn A", sachDaMuon = sachMuonCuaA)
        val svB = SinhVien(102, "Nguyễn Thị B", sachDaMuon = sachMuonCuaB)
        val svC = SinhVien(103, "Nguyễn Văn C")
        val dsSinhVien = listOf(svA, svB, svC)

        // Cập nhật trạng thái ban đầu cho StateFlow
        _uiState.value = ThuVienUiState(
            danhSachSach = dsSach,
            danhSachSV = dsSinhVien,
            sinhVienHienTai = dsSinhVien.first(), // Chọn SV đầu tiên làm mặc định
            trangThaiChonSach = capNhatTrangThaiChon(dsSinhVien.first(), dsSach)
        )
    }

    // Hàm được gọi khi người dùng chọn một sinh viên khác từ dropdown.
    fun chonSinhVien(sv: SinhVien) {
        _uiState.update { currentState ->
            currentState.copy(
                sinhVienHienTai = sv,
                trangThaiChonSach = capNhatTrangThaiChon(sv, currentState.danhSachSach)
            )
        }
    }

    // Hàm được gọi khi người dùng tick hoặc bỏ tick một checkbox.
    fun thayDoiLuaChonSach(sachId: Int, duocChon: Boolean) {
        _uiState.update { currentState ->
            val trangThaiMoi = currentState.trangThaiChonSach.toMutableMap()
            trangThaiMoi[sachId] = duocChon
            currentState.copy(trangThaiChonSach = trangThaiMoi)
        }
    }

    // Hàm xử lý logic cho nút "Thêm", tuân thủ nguyên tắc bất biến.
    fun capNhatSachMuon() {
        _uiState.update { currentState ->
            val svHienTai = currentState.sinhVienHienTai ?: return@update currentState

            // 1. Tạo một danh sách sách mượn MỚI dựa trên trạng thái checkbox.
            val sachMuonMoi = currentState.danhSachSach.filter { sach ->
                currentState.trangThaiChonSach[sach.id] == true
            }

            // 2. Tạo một đối tượng SinhVien MỚI bằng hàm copy(), thay thế danh sách sách mượn.
            val svCapNhat = svHienTai.copy(sachDaMuon = sachMuonMoi)

            // 3. Tạo một danh sách sinh viên MỚI, thay thế sinh viên cũ bằng sinh viên đã cập nhật.
            val dsSinhVienMoi = currentState.danhSachSV.map { sv ->
                if (sv.id == svCapNhat.id) svCapNhat else sv
            }

            // 4. Trả về một trạng thái (State) hoàn toàn MỚI để cập nhật UI.
            currentState.copy(
                danhSachSV = dsSinhVienMoi,
                sinhVienHienTai = svCapNhat
            )
        }
    }

    // Hàm tiện ích để đồng bộ trạng thái checkbox với danh sách sách đã mượn của sinh viên.
    private fun capNhatTrangThaiChon(sv: SinhVien, dsSach: List<Sach>): Map<Int, Boolean> {
        val idSachDaMuon = sv.sachDaMuon.map { it.id }.toSet()
        return dsSach.associate { it.id to (it.id in idSachDaMuon) }
    }
}