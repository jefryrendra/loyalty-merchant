package com.klolatoko.loyaltymerchant.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klolatoko.loyaltymerchant.domain.model.MerchantSettings
import com.klolatoko.loyaltymerchant.presentation.theme.*

@Composable
fun PengaturanTab(
    settings: MerchantSettings,
    onSaveSettings: (type: String, targetStamps: Int, rewardTitle: String, autoResetPin: Boolean) -> Unit
) {
    var selectedType by remember(settings.type) { mutableStateOf(settings.type) }
    var targetStampsText by remember(settings.targetStamps) { mutableStateOf(settings.targetStamps.toString()) }
    var rewardTitleText by remember(settings.rewardTitle) { mutableStateOf(settings.rewardTitle) }
    var autoResetPinChecked by remember(settings.autoResetPin) { mutableStateOf(settings.autoResetPin) }

    var posIntegrationChecked by remember { mutableStateOf(true) }
    var notificationsChecked by remember { mutableStateOf(true) }

    // Dialog States
    var showTypeDialog by remember { mutableStateOf(false) }
    var showTargetDialog by remember { mutableStateOf(false) }
    var showRewardTitleDialog by remember { mutableStateOf(false) }

    val options = listOf(
        "stamp" to "Stamp / Stiker Digital",
        "points" to "Dynamic Points & Level Tiering"
    )

    val currentTypeLabel = options.find { it.first == selectedType }?.second ?: options[0].second

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Pengaturan Toko",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate800,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Kelola skema reward, keamanan kasir, dan integrasi toko",
                fontSize = 12.sp,
                color = Slate500
            )
        }

        // GROUP 1: SKEMA REWARD & LOYALTY
        SettingsGroup(title = "Skema Reward & Loyalty") {
            SettingsItem(
                label = "Tipe Loyalty System",
                value = currentTypeLabel,
                icon = Icons.Default.Loyalty,
                onClick = { showTypeDialog = true }
            )
            HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                label = "Target Stiker Per Hadiah",
                value = "$targetStampsText Stiker",
                description = "Jumlah stiker untuk klaim 1 bonus",
                icon = Icons.Default.Star,
                onClick = { showTargetDialog = true }
            )
            HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                label = "Nama Hadiah Bonus",
                value = rewardTitleText,
                description = "Nama produk reward pelanggan",
                icon = Icons.Default.CardGiftcard,
                onClick = { showRewardTitleDialog = true }
            )
        }

        // GROUP 2: KEAMANAN KASIR
        SettingsGroup(title = "Keamanan Kasir") {
            SettingsToggleItem(
                label = "Reset PIN Harian Otomatis",
                description = "Ganti PIN tiap pukul 00:00 untuk cegah kecurangan",
                icon = Icons.Default.Lock,
                enabled = autoResetPinChecked,
                onCheckedChange = { autoResetPinChecked = it }
            )
            HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                label = "PIN Operasional Kasir",
                value = "****",
                description = "Atur PIN verifikasi transaksi kasir",
                icon = Icons.Default.VpnKey
            )
        }

        // GROUP 3: TOKO & INTEGRASI POS
        SettingsGroup(title = "Toko & Integrasi POS") {
            SettingsItem(
                label = "Profil Toko",
                value = "Martabak Bangka Jaya",
                description = "Nama toko & informasi cabang",
                icon = Icons.Default.Storefront
            )
            HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            SettingsToggleItem(
                label = "Integrasi POS Otomatis",
                description = "Poin otomatis terisi dari transaksi POS",
                icon = Icons.Default.Sync,
                enabled = posIntegrationChecked,
                onCheckedChange = { posIntegrationChecked = it }
            )
            HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            SettingsToggleItem(
                label = "Notifikasi Transaksi",
                description = "Terima pemberitahuan klaim baru dari pelanggan",
                icon = Icons.Default.Notifications,
                enabled = notificationsChecked,
                onCheckedChange = { notificationsChecked = it }
            )
        }

        // GROUP 4: INFORMASI APLIKASI
        SettingsGroup(title = "Informasi Aplikasi") {
            SettingsItem(
                label = "Versi Aplikasi",
                value = "1.0.0 (Build 20251101)",
                icon = Icons.Default.Info
            )
            HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            SettingsItem(
                label = "Syarat & Ketentuan Merchant",
                icon = Icons.Default.Description
            )
        }

        // SAVE BUTTON
        Button(
            onClick = {
                val parsedTarget = targetStampsText.toIntOrNull() ?: 10
                onSaveSettings(selectedType, parsedTarget, rewardTitleText, autoResetPinChecked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "SIMPAN PERUBAHAN SETTINGS",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    // DIALOGS FOR EDITING SETTINGS
    if (showTypeDialog) {
        AlertDialog(
            onDismissRequest = { showTypeDialog = false },
            title = { Text("Pilih Tipe Loyalty System", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    options.forEach { (typeVal, label) ->
                        Surface(
                            onClick = {
                                selectedType = typeVal
                                showTypeDialog = false
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedType == typeVal) Emerald50 else Slate50,
                            border = BorderStroke(1.dp, if (selectedType == typeVal) Emerald500 else Slate200),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedType == typeVal,
                                    onClick = {
                                        selectedType = typeVal
                                        showTypeDialog = false
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = Emerald600)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Slate800
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTypeDialog = false }) {
                    Text("Tutup", color = Slate500, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp)
        )
    }

    if (showTargetDialog) {
        var tempTarget by remember { mutableStateOf(targetStampsText) }
        AlertDialog(
            onDismissRequest = { showTargetDialog = false },
            title = { Text("Target Stiker Per Hadiah", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempTarget,
                    onValueChange = { tempTarget = it },
                    label = { Text("Jumlah Stiker") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        targetStampsText = tempTarget
                        showTargetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTargetDialog = false }) {
                    Text("Batal", color = Slate400)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp)
        )
    }

    if (showRewardTitleDialog) {
        var tempRewardTitle by remember { mutableStateOf(rewardTitleText) }
        AlertDialog(
            onDismissRequest = { showRewardTitleDialog = false },
            title = { Text("Nama Hadiah Bonus", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempRewardTitle,
                    onValueChange = { tempRewardTitle = it },
                    label = { Text("Nama Bonus / Produk") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        rewardTitleText = tempRewardTitle
                        showRewardTitleDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRewardTitleDialog = false }) {
                    Text("Batal", color = Slate400)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = title.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = Slate400,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Slate100),
            shadowElevation = 1.dp
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsItem(
    label: String,
    value: String? = null,
    description: String? = null,
    icon: ImageVector? = null,
    color: Color = Slate800,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Slate50),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = Emerald600)
                    }
                    Spacer(Modifier.width(12.dp))
                }
                Column {
                    Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
                    if (description != null) {
                        Text(description, fontSize = 11.sp, color = Slate400, lineHeight = 15.sp)
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (value != null) {
                    Text(value, fontSize = 13.sp, color = Slate500, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.width(8.dp))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Slate300, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun SettingsToggleItem(
    label: String,
    description: String? = null,
    icon: ImageVector? = null,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f).padding(end = 12.dp)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Slate50),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = Emerald600)
                }
                Spacer(Modifier.width(12.dp))
            }
            Column {
                Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate800)
                if (description != null) {
                    Text(description, fontSize = 11.sp, color = Slate400, lineHeight = 15.sp)
                }
            }
        }
        Switch(
            checked = enabled,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Emerald600,
                uncheckedThumbColor = Slate200,
                uncheckedTrackColor = Slate50
            )
        )
    }
}
