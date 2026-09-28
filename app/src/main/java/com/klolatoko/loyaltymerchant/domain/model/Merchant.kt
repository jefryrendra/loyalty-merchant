package com.klolatoko.loyaltymerchant.domain.model

data class MerchantProfile(
    val storeName: String = "Martabak Bangka Lekker",
    val branchName: String = "Cabang Surabaya Pusat",
    val isStoreOpen: Boolean = true,
    val otpCode: String = "849 201",
    val otpExpiresInSeconds: Int = 30
)

data class ClaimItem(
    val name: String,
    val price: Long
)

data class Claim(
    val id: String,
    val userName: String,
    val phone: String,
    val amountSpent: Long,
    val calculatedPts: Int,
    val time: String,
    val refId: String,
    val receiptImg: String,
    val items: List<ClaimItem>
)

data class Member(
    val name: String,
    val phone: String,
    val stamps: Int,
    val points: Int,
    val totalVisits: Int,
    val lastVisit: String
)

data class ActivityLog(
    val id: Long,
    val type: String, // "stamp" or "voucher"
    val title: String,
    val phone: String,
    val time: String
)

data class MerchantSettings(
    val type: String = "stamp",
    val targetStamps: Int = 10,
    val rewardTitle: String = "Martabak Manis Medium Gratis",
    val autoResetPin: Boolean = true
)
