package com.klolatoko.loyaltymerchant.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klolatoko.loyaltymerchant.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class DashboardUiState(
    val merchantProfile: MerchantProfile = MerchantProfile(),
    val statsStampsToday: Int = 28,
    val statsVouchersToday: Int = 4,
    val members: List<Member> = listOf(
        Member("Jefri Rendra", "081234567890", stamps = 7, points = 480, totalVisits = 12, lastVisit = "Hari ini"),
        Member("Budi Santoso", "085711223344", stamps = 9, points = 150, totalVisits = 9, lastVisit = "Kemarin"),
        Member("Siti Rahma", "081988776655", stamps = 3, points = 310, totalVisits = 3, lastVisit = "5 hari lalu"),
        Member("Rian Hidayat", "082144556677", stamps = 1, points = 50, totalVisits = 1, lastVisit = "18 hari lalu")
    ),
    val pendingClaims: List<Claim> = listOf(
        Claim(
            id = "CLM-101",
            userName = "Jefri Rendra",
            phone = "081234567890",
            amountSpent = 85000,
            calculatedPts = 85,
            time = "5 Menit lalu",
            refId = "TRX-882190",
            receiptImg = "https://placehold.co/400x300/f1f5f9/334155?text=Struk+Wonder+Bakery+Rp85.000",
            items = listOf(
                ClaimItem("2x Roti Sobek Cokelat", 50000),
                ClaimItem("1x Choco Croissant", 35000)
            )
        ),
        Claim(
            id = "CLM-102",
            userName = "Siti Rahma",
            phone = "081988776655",
            amountSpent = 120000,
            calculatedPts = 120,
            time = "18 Menit lalu",
            refId = "TRX-990123",
            receiptImg = "https://placehold.co/400x300/fef3c7/92400e?text=Struk+Kopi+Senja+Rp120.000",
            items = listOf(
                ClaimItem("2x Hazelnut Latte", 70000),
                ClaimItem("1x Cheese Cake Sourdough", 50000)
            )
        )
    ),
    val activityLogs: List<ActivityLog> = listOf(
        ActivityLog(1, "stamp", "+1 Stamp ke Jefri Rendra", "081234567890", "10 menit lalu"),
        ActivityLog(2, "voucher", "Tukar Voucher Gratis Martabak", "085711223344", "1 jam lalu"),
        ActivityLog(3, "stamp", "+2 Stamp ke Budi Santoso", "085711223344", "3 jam lalu")
    ),
    val settings: MerchantSettings = MerchantSettings(),
    val selectedTab: MerchantTab = MerchantTab.KASIR,
    val stampQty: Int = 1,
    val inputPhone: String = "",
    val searchMemberQuery: String = "",
    val showOtpQrModal: Boolean = false,
    val showRedeemVoucherModal: Boolean = false,
    val voucherCodeInput: String = "",
    val showApprovalDetailModal: Boolean = false,
    val selectedClaim: Claim? = null,
    val rejectReason: String = "",
    val showRejectReasonBox: Boolean = false,
    val toastMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        startOtpTimer()
    }

    private fun startOtpTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { state ->
                    val profile = state.merchantProfile
                    if (profile.otpExpiresInSeconds <= 1) {
                        val newOtp = generateNewOtpCode()
                        state.copy(
                            merchantProfile = profile.copy(
                                otpCode = newOtp,
                                otpExpiresInSeconds = 30
                            )
                        )
                    } else {
                        state.copy(
                            merchantProfile = profile.copy(
                                otpExpiresInSeconds = profile.otpExpiresInSeconds - 1
                            )
                        )
                    }
                }
            }
        }
    }

    private fun generateNewOtpCode(): String {
        val part1 = Random.nextInt(100, 999)
        val part2 = Random.nextInt(100, 999)
        return "$part1 $part2"
    }

    fun openOtpQrModal() {
        _uiState.update { it.copy(showOtpQrModal = true) }
    }

    fun closeOtpQrModal() {
        _uiState.update { it.copy(showOtpQrModal = false) }
    }

    fun regenerateOtpQr() {
        val newOtp = generateNewOtpCode()
        _uiState.update { state ->
            state.copy(
                merchantProfile = state.merchantProfile.copy(
                    otpCode = newOtp,
                    otpExpiresInSeconds = 30
                ),
                toastMessage = "🔑 OTP QR Diperbarui: $newOtp"
            )
        }
    }

    fun selectTab(tab: MerchantTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun toggleStoreStatus() {
        _uiState.update { state ->
            val newStatus = !state.merchantProfile.isStoreOpen
            val msg = if (newStatus) "Toko Siap Menerima Transaksi Stamp" else "Sistem Loyalty Sementara Ditutup"
            state.copy(
                merchantProfile = state.merchantProfile.copy(isStoreOpen = newStatus),
                toastMessage = msg
            )
        }
    }

    fun setInputPhone(phone: String) {
        _uiState.update { it.copy(inputPhone = phone) }
    }

    fun adjustStampQty(delta: Int) {
        _uiState.update { state ->
            val newQty = (state.stampQty + delta).coerceAtLeast(1)
            state.copy(stampQty = newQty)
        }
    }

    fun submitGiveStamp() {
        val currentState = _uiState.value
        val phoneRaw = currentState.inputPhone.trim()
        if (phoneRaw.length < 8) {
            _uiState.update { it.copy(toastMessage = "⚠️ Masukkan nomor WhatsApp pelanggan yang valid") }
            return
        }

        val fullPhone = if (phoneRaw.startsWith("0")) phoneRaw else "0$phoneRaw"
        val qty = currentState.stampQty
        val targetStamps = currentState.settings.targetStamps

        val existingMemberIndex = currentState.members.indexOfFirst { it.phone == fullPhone }
        val updatedMembers = currentState.members.toMutableList()
        var toastText = "✅ Berhasil menambahkan +$qty Stamp ke $fullPhone"
        var vouchersAdded = 0

        if (existingMemberIndex >= 0) {
            val member = updatedMembers[existingMemberIndex]
            val newStamps = member.stamps + qty
            val finalStamps = newStamps % targetStamps
            if (newStamps >= targetStamps) {
                vouchersAdded = newStamps / targetStamps
                toastText = "🎉 Target $targetStamps Stamp tercapai! Voucher bonus diterbitkan."
            }
            updatedMembers[existingMemberIndex] = member.copy(
                stamps = finalStamps,
                totalVisits = member.totalVisits + 1,
                lastVisit = "Hari ini"
            )
        } else {
            val finalStamps = qty % targetStamps
            if (qty >= targetStamps) {
                vouchersAdded = qty / targetStamps
                toastText = "🎉 Target $targetStamps Stamp tercapai! Voucher bonus diterbitkan."
            }
            updatedMembers.add(
                0,
                Member(
                    name = "Pelanggan (${fullPhone.takeLast(4)})",
                    phone = fullPhone,
                    stamps = finalStamps,
                    points = 0,
                    totalVisits = 1,
                    lastVisit = "Hari ini"
                )
            )
        }

        val memberName = updatedMembers.find { it.phone == fullPhone }?.name ?: fullPhone
        val newLog = ActivityLog(
            id = System.currentTimeMillis(),
            type = "stamp",
            title = "+$qty Stamp ke $memberName",
            phone = fullPhone,
            time = "Baru Saja"
        )
        val updatedLogs = (listOf(newLog) + currentState.activityLogs).take(10)

        _uiState.update { state ->
            state.copy(
                members = updatedMembers,
                statsStampsToday = state.statsStampsToday + qty,
                statsVouchersToday = state.statsVouchersToday + vouchersAdded,
                activityLogs = updatedLogs,
                inputPhone = "",
                stampQty = 1,
                toastMessage = toastText
            )
        }
    }

    fun openRedeemVoucherModal() {
        _uiState.update { it.copy(showRedeemVoucherModal = true, voucherCodeInput = "") }
    }

    fun closeRedeemVoucherModal() {
        _uiState.update { it.copy(showRedeemVoucherModal = false, voucherCodeInput = "") }
    }

    fun setVoucherCodeInput(code: String) {
        _uiState.update { it.copy(voucherCodeInput = code) }
    }

    fun confirmRedeemVoucher() {
        val code = _uiState.value.voucherCodeInput.trim()
        if (code.isEmpty()) {
            _uiState.update { it.copy(toastMessage = "⚠️ Masukkan kode voucher terlebih dahulu") }
            return
        }

        val newLog = ActivityLog(
            id = System.currentTimeMillis(),
            type = "voucher",
            title = "Verifikasi Voucher: ${code.uppercase()}",
            phone = "Diverifikasi Kasir",
            time = "Baru Saja"
        )

        _uiState.update { state ->
            state.copy(
                showRedeemVoucherModal = false,
                voucherCodeInput = "",
                statsVouchersToday = state.statsVouchersToday + 1,
                activityLogs = (listOf(newLog) + state.activityLogs).take(10),
                toastMessage = "✅ Voucher ${code.uppercase()} Sah! Siapkan Porsi Bonus Martabak."
            )
        }
    }

    fun openApprovalDetailModal(claim: Claim) {
        _uiState.update {
            it.copy(
                selectedClaim = claim,
                showApprovalDetailModal = true,
                rejectReason = "",
                showRejectReasonBox = false
            )
        }
    }

    fun closeApprovalDetailModal() {
        _uiState.update {
            it.copy(
                showApprovalDetailModal = false,
                selectedClaim = null,
                rejectReason = "",
                showRejectReasonBox = false
            )
        }
    }

    fun quickApproveClaim(claim: Claim) {
        val currentState = _uiState.value
        val updatedClaims = currentState.pendingClaims.filterNot { it.id == claim.id }

        val updatedMembers = currentState.members.toMutableList()
        val mIndex = updatedMembers.indexOfFirst { it.phone == claim.phone }
        if (mIndex >= 0) {
            val m = updatedMembers[mIndex]
            updatedMembers[mIndex] = m.copy(
                points = m.points + claim.calculatedPts,
                totalVisits = m.totalVisits + 1,
                lastVisit = "Baru Saja"
            )
        } else {
            updatedMembers.add(
                0,
                Member(
                    name = claim.userName,
                    phone = claim.phone,
                    stamps = 0,
                    points = claim.calculatedPts,
                    totalVisits = 1,
                    lastVisit = "Baru Saja"
                )
            )
        }

        val newLog = ActivityLog(
            id = System.currentTimeMillis(),
            type = "stamp",
            title = "Approval: +${claim.calculatedPts} Pts ke ${claim.userName}",
            phone = claim.phone,
            time = "Baru Saja"
        )

        _uiState.update { state ->
            state.copy(
                pendingClaims = updatedClaims,
                members = updatedMembers,
                activityLogs = (listOf(newLog) + state.activityLogs).take(10),
                toastMessage = "✅ Klaim ${claim.userName} Disetujui (+${claim.calculatedPts} Poin)!"
            )
        }
    }

    fun setRejectReason(reason: String) {
        _uiState.update { it.copy(rejectReason = reason) }
    }

    fun toggleRejectReasonBox() {
        _uiState.update { it.copy(showRejectReasonBox = !it.showRejectReasonBox) }
    }

    fun confirmRejectClaim() {
        val currentState = _uiState.value
        val claim = currentState.selectedClaim ?: return
        val reason = currentState.rejectReason.ifBlank { "Struk tidak valid" }

        val updatedClaims = currentState.pendingClaims.filterNot { it.id == claim.id }
        val newLog = ActivityLog(
            id = System.currentTimeMillis(),
            type = "voucher",
            title = "Penolakan Klaim ${claim.userName}",
            phone = "${claim.phone} ($reason)",
            time = "Baru Saja"
        )

        _uiState.update { state ->
            state.copy(
                showApprovalDetailModal = false,
                selectedClaim = null,
                rejectReason = "",
                showRejectReasonBox = false,
                pendingClaims = updatedClaims,
                activityLogs = (listOf(newLog) + state.activityLogs).take(10),
                toastMessage = "❌ Klaim ${claim.userName} Ditolak: $reason"
            )
        }
    }

    fun simulateIncomingClaim() {
        val dummyNames = listOf("Rizky Pratama", "Dewi Anggraini", "Agus Setiawan")
        val randomName = dummyNames.random()
        val amount = Random.nextInt(3, 11) * 10000L
        val pts = (amount / 1000).toInt()
        val claimId = "CLM-${Random.nextInt(100, 999)}"

        val newClaim = Claim(
            id = claimId,
            userName = randomName,
            phone = "0812${Random.nextInt(10000000, 99999999)}",
            amountSpent = amount,
            calculatedPts = pts,
            time = "Baru Saja",
            refId = "TRX-${Random.nextInt(100000, 999999)}",
            receiptImg = "https://placehold.co/400x300/e0f2fe/0369a1?text=Struk+Pembelian+Rp${amount}",
            items = listOf(
                ClaimItem("Roti Spesial Oven", amount - 10000),
                ClaimItem("Teh Manis", 10000)
            )
        )

        _uiState.update { state ->
            state.copy(
                pendingClaims = listOf(newClaim) + state.pendingClaims,
                toastMessage = "📩 Klaim Baru Masuk dari $randomName (Rp $amount)"
            )
        }
    }

    fun setSearchMemberQuery(query: String) {
        _uiState.update { it.copy(searchMemberQuery = query) }
    }

    fun saveSettings(type: String, targetStamps: Int, rewardTitle: String, autoResetPin: Boolean) {
        _uiState.update { state ->
            state.copy(
                settings = MerchantSettings(
                    type = type,
                    targetStamps = targetStamps,
                    rewardTitle = rewardTitle,
                    autoResetPin = autoResetPin
                ),
                toastMessage = "⚙️ Pengaturan Program Loyalti Disimpan!"
            )
        }
    }

    fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
