package com.klolatoko.loyaltymerchant.presentation.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
    activeSubScreen: String,
    onActiveSubScreenChange: (String) -> Unit,
    onSaveSettings: (type: String, targetStamps: Int, rewardTitle: String, autoResetPin: Boolean) -> Unit
) {
    var selectedType by remember(settings.type) { mutableStateOf(settings.type) }
    var targetStampsText by remember(settings.targetStamps) { mutableStateOf(settings.targetStamps.toString()) }
    var rewardTitleText by remember(settings.rewardTitle) { mutableStateOf(settings.rewardTitle) }
    var autoResetPinChecked by remember(settings.autoResetPin) { mutableStateOf(settings.autoResetPin) }

    var storeNameText by remember { mutableStateOf("Martabak Bangka Jaya") }
    var phoneText by remember { mutableStateOf("+62 812-3456-7890") }
    var emailText by remember { mutableStateOf("merchant@klolatoko.com") }

    var posIntegrationChecked by remember { mutableStateOf(true) }
    var notificationsChecked by remember { mutableStateOf(true) }

    // Dialog States
    var showTypeDialog by remember { mutableStateOf(false) }
    var showTargetDialog by remember { mutableStateOf(false) }
    var showRewardTitleDialog by remember { mutableStateOf(false) }
    var showStoreNameDialog by remember { mutableStateOf(false) }

    val options = listOf(
        "stamp" to "Stamp / Stiker Digital",
        "points" to "Dynamic Points & Level Tiering"
    )

    val currentTypeLabel = options.find { it.first == selectedType }?.second ?: options[0].second

    if (activeSubScreen != "main") {
        BackHandler { onActiveSubScreenChange("main") }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (activeSubScreen == "main") {
            // ==================== MAIN MENU VIEW (MATCHES SCREENSHOT) ====================
            
            // 1. PROFIL MERCHANT CARD
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate100),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.clickable { showStoreNameDialog = true }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(Emerald600, Teal600))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "JR",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .background(Emerald600)
                                .border(1.5.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    Spacer(Modifier.width(20.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = storeNameText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Slate800
                        )
                        Text(
                            text = phoneText,
                            fontSize = 13.sp,
                            color = Slate500
                        )
                        Spacer(Modifier.height(6.dp))
                        Surface(color = Emerald100, shape = CircleShape) {
                            Text(
                                text = "MERCHANT ID: #78219",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                color = Emerald700,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // 2. RINGKASAN AKTIVITAS
            Text(
                text = "Ringkasan Aktivitas",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Slate400,
                letterSpacing = 1.sp
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(value = "128", label = "Total Member", color = Emerald600)
                StatCard(value = "42", label = "Stamp Hari Ini", color = Amber500)
            }

            // 3. PENGATURAN & INFORMASI CARD
            Text(
                text = "Pengaturan & Informasi",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Slate400,
                letterSpacing = 1.sp
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate100)
            ) {
                Column {
                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        label = "Pengaturan Akun",
                        onClick = { onActiveSubScreenChange("account") }
                    )
                    HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuItem(
                        icon = Icons.Default.Loyalty,
                        label = "Pengaturan Skema Loyalty",
                        onClick = { onActiveSubScreenChange("loyalty") }
                    )
                    HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuItem(
                        icon = Icons.Default.Storefront,
                        label = "Pengaturan Store & POS",
                        onClick = { onActiveSubScreenChange("store") }
                    )
                    HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuItem(
                        icon = Icons.Default.Info,
                        label = "Tentang Aplikasi",
                        onClick = { onActiveSubScreenChange("about") }
                    )
                    HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuItem(
                        icon = Icons.Default.VerifiedUser,
                        label = "Kebijakan Privasi",
                        onClick = { onActiveSubScreenChange("privacy") }
                    )
                }
            }

            // 4. VERSI APLIKASI
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate100)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Versi Aplikasi", fontSize = 11.sp, color = Slate400, fontWeight = FontWeight.Bold)
                    Text("1.0.0 (Build 20251101)", fontSize = 13.sp, color = Slate800, fontWeight = FontWeight.ExtraBold)
                }
            }

            // 5. KELUAR DARI AKUN
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.2f)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "KELUAR DARI AKUN",
                        color = Color(0xFFEF4444),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        } else {
            // ==================== SUB-SCREEN VIEWS ====================
            when (activeSubScreen) {
                "account" -> {
                    SettingsGroup(title = "Profil Toko") {
                        SettingsItem(
                            label = "Nama Toko / Merchant",
                            value = storeNameText,
                            onClick = { showStoreNameDialog = true }
                        )
                        HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsItem(
                            label = "Email Akun",
                            value = emailText
                        )
                        HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsItem(
                            label = "Nomor Telepon",
                            value = phoneText
                        )
                    }

                    SettingsGroup(title = "Keamanan") {
                        SettingsItem(
                            label = "Ganti Kata Sandi",
                            icon = Icons.Default.Security
                        )
                        HorizontalDivider(color = Slate100, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsItem(
                            label = "PIN Operasional Kasir",
                            value = "****",
                            icon = Icons.Default.VpnKey
                        )
                    }
                }

                "loyalty" -> {
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

                    SettingsGroup(title = "Keamanan Kasir") {
                        SettingsToggleItem(
                            label = "Reset PIN Harian Otomatis",
                            description = "Ganti PIN tiap pukul 00:00 untuk cegah kecurangan",
                            icon = Icons.Default.Lock,
                            enabled = autoResetPinChecked,
                            onCheckedChange = { autoResetPinChecked = it }
                        )
                    }

                    Button(
                        onClick = {
                            val parsedTarget = targetStampsText.toIntOrNull() ?: 10
                            onSaveSettings(selectedType, parsedTarget, rewardTitleText, autoResetPinChecked)
                            onActiveSubScreenChange("main")
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
                }

                "store" -> {
                    SettingsGroup(title = "Integrasi POS") {
                        SettingsToggleItem(
                            label = "Integrasi POS Otomatis",
                            description = "Poin otomatis terisi dari transaksi POS",
                            icon = Icons.Default.Sync,
                            enabled = posIntegrationChecked,
                            onCheckedChange = { posIntegrationChecked = it }
                        )
                    }

                    SettingsGroup(title = "Notifikasi Transaksi") {
                        SettingsToggleItem(
                            label = "Notifikasi Klaim Baru",
                            description = "Terima pemberitahuan klaim baru dari pelanggan",
                            icon = Icons.Default.Notifications,
                            enabled = notificationsChecked,
                            onCheckedChange = { notificationsChecked = it }
                        )
                    }
                }

                "about" -> {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate100)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text("Klola Loyalty Merchant", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Slate800)
                            Spacer(Modifier.height(4.dp))
                            Text("Aplikasi pengelolaan program loyalitas dan stiker reward digital untuk merchant & UMKM Indonesia.", fontSize = 13.sp, color = Slate500, lineHeight = 18.sp)
                        }
                    }
                }

                "privacy" -> {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate100)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text("Kebijakan Privasi", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Slate800)
                            Spacer(Modifier.height(4.dp))
                            Text("Data toko, transaksi kasir, dan pelanggan Anda dilindungi dengan enkripsi standar industri.", fontSize = 13.sp, color = Slate500, lineHeight = 18.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // ==================== DIALOGS FOR EDITING ====================
    if (showStoreNameDialog) {
        var tempName by remember { mutableStateOf(storeNameText) }
        AlertDialog(
            onDismissRequest = { showStoreNameDialog = false },
            title = { Text("Ubah Nama Toko", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { tempName = it },
                    label = { Text("Nama Toko") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        storeNameText = tempName
                        showStoreNameDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStoreNameDialog = false }) {
                    Text("Batal", color = Slate400)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp)
        )
    }

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
fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Slate50),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Slate500
                    )
                }
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Slate800
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Slate300,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun RowScope.StatCard(value: String, label: String, color: Color) {
    Surface(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate100),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Black, color = color)
            Text(label, fontSize = 11.sp, color = Slate500, fontWeight = FontWeight.Bold)
        }
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
