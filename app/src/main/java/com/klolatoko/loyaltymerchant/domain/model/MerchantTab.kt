package com.klolatoko.loyaltymerchant.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class MerchantTab(val label: String, val icon: ImageVector) {
    KASIR("Kasir", Icons.Default.Receipt),
    APPROVAL("Approval", Icons.AutoMirrored.Filled.FactCheck),
    PELANGGAN("Pelanggan", Icons.Default.Group),
    PENGATURAN("Pengaturan", Icons.Default.Settings)
}
