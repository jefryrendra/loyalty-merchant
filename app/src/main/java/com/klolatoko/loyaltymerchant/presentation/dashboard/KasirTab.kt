package com.klolatoko.loyaltymerchant.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klolatoko.loyaltymerchant.domain.model.ActivityLog
import com.klolatoko.loyaltymerchant.domain.model.MerchantProfile
import com.klolatoko.loyaltymerchant.presentation.theme.*

@Composable
fun KasirTab(
    merchantProfile: MerchantProfile,
    stampsCount: Int,
    vouchersCount: Int,
    membersCount: Int,
    pendingCount: Int,
    targetStamps: Int,
    inputPhone: String,
    stampQty: Int,
    activityLogs: List<ActivityLog>,
    onToggleStoreStatus: () -> Unit,
    onOpenOtpQrModal: () -> Unit,
    onPhoneChange: (String) -> Unit,
    onAdjustQty: (Int) -> Unit,
    onSubmitGiveStamp: () -> Unit,
    onOpenRedeemModal: () -> Unit,
    onSwitchToApproval: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // RADIAL GRADIENT GREEN HEADER SECTION (ONLY ON KASIR TAB)
        KasirGreenHeader(
            storeName = merchantProfile.storeName,
            branchName = merchantProfile.branchName,
            isStoreOpen = merchantProfile.isStoreOpen,
            otpCode = merchantProfile.otpCode,
            expiresInSeconds = merchantProfile.otpExpiresInSeconds,
            onToggleStoreStatus = onToggleStoreStatus,
            onOpenOtpQrModal = onOpenOtpQrModal
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Quick Stats Bar
            QuickStatsBar(
                stampsCount = stampsCount,
                vouchersCount = vouchersCount,
                membersCount = membersCount,
                pendingCount = pendingCount,
                onApprovalClick = onSwitchToApproval
            )

            // Antrean Approval Quick Widget
            Surface(
                color = Rose50,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Rose200)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Rose500),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.FactCheck,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Antrean Approval Struk",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Slate800
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(color = Rose500, shape = CircleShape) {
                                    Text(
                                        text = "$pendingCount",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                            Text(
                                text = "Ada permintaan klaim poin belanja dengan struk",
                                fontSize = 10.sp,
                                color = Slate500
                            )
                        }
                    }

                    Button(
                        onClick = onSwitchToApproval,
                        colors = ButtonDefaults.buttonColors(containerColor = Rose600),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Periksa",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Fast Stamp / Poin Issuance Card
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(2.dp, Emerald500.copy(alpha = 0.3f)),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = null,
                                tint = Emerald600,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Beri Stamp / Poin Cepat",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Slate800
                            )
                        }
                        Surface(color = Emerald100, shape = CircleShape) {
                            Text(
                                text = "Mode Stamp ($targetStamps Slot)",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Emerald800,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    HorizontalDivider(color = Slate100)

                    Column {
                        Text(
                            text = "Nomor WhatsApp Pelanggan",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = inputPhone,
                            onValueChange = onPhoneChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text("8123456789", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Slate400)
                            },
                            leadingIcon = {
                                Text("+62", color = Slate400, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            },
                            trailingIcon = if (inputPhone.isNotEmpty()) {
                                {
                                    IconButton(onClick = { onPhoneChange("") }) {
                                        Icon(
                                            Icons.Default.Cancel,
                                            contentDescription = "Hapus",
                                            tint = Slate400,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            } else null,
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Emerald500,
                                unfocusedBorderColor = Slate200,
                                focusedContainerColor = Slate50,
                                unfocusedContainerColor = Slate50
                            )
                        )
                        Text(
                            text = "*Pelanggan baru akan otomatis terdaftar sebagai member",
                            fontSize = 10.sp,
                            color = Slate400,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Surface(
                        color = Slate50,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Slate100)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Jumlah Stamp Diberikan:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate700
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                IconButton(
                                    onClick = { onAdjustQty(-1) },
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.White)
                                        .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                                ) {
                                    Text("-", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Slate700)
                                }
                                Text(
                                    text = "$stampQty",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Emerald600
                                )
                                IconButton(
                                    onClick = { onAdjustQty(1) },
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Emerald600)
                                ) {
                                    Text("+", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = onSubmitGiveStamp,
                            modifier = Modifier
                                .weight(3f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Tambah Stamp Now", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onOpenRedeemModal,
                            modifier = Modifier
                                .weight(2f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Icon(Icons.Default.ConfirmationNumber, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Tukar Voucher", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Cashier Recent Activity Log
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Aktivitas Kasir Terakhir",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate800
                    )
                    Text(
                        text = "Realtime Update",
                        fontSize = 10.sp,
                        color = Slate400
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    activityLogs.take(5).forEach { log ->
                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Slate100)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (log.type == "stamp") Emerald100 else Amber100),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = if (log.type == "stamp") "+" else "🎁",
                                            color = if (log.type == "stamp") Emerald700 else Amber700,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = log.title,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Slate800
                                        )
                                        Text(
                                            text = "${log.phone} • ${log.time}",
                                            fontSize = 10.sp,
                                            color = Slate400
                                        )
                                    }
                                }
                                Text(
                                    text = "Sukses",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate400
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KasirGreenHeader(
    storeName: String,
    branchName: String,
    isStoreOpen: Boolean,
    otpCode: String,
    expiresInSeconds: Int,
    onToggleStoreStatus: () -> Unit,
    onOpenOtpQrModal: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Emerald400,
                        Emerald500,
                        Emerald600,
                        Emerald700
                    ),
                    radius = 1200f
                )
            )
    ) {
        // Ambient Canvas Circles (from loyalty-app SplashScreen style)
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            val centerOffset = Offset(w * 0.85f, h * 0.3f)

            drawCircle(color = Color.White.copy(alpha = 0.06f), radius = w * 0.95f, center = centerOffset)
            drawCircle(color = Color.White.copy(alpha = 0.08f), radius = w * 0.72f, center = centerOffset)
            drawCircle(color = Color.White.copy(alpha = 0.10f), radius = w * 0.50f, center = centerOffset)
            drawCircle(color = Color.White.copy(alpha = 0.12f), radius = w * 0.32f, center = centerOffset)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 12.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "Dashboard Kasir",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🥞", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Surface(
                            color = Emerald900.copy(alpha = 0.5f),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Emerald400.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "MODE MERCHANT STANDALONE",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Emerald200,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = storeName,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = branchName,
                            color = Emerald100,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    onClick = onToggleStoreStatus,
                    color = if (isStoreOpen) Emerald800.copy(alpha = 0.6f) else Rose900.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isStoreOpen) Emerald400.copy(alpha = 0.4f) else Rose500.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isStoreOpen) Emerald300 else Rose500)
                        )
                        Text(
                            text = if (isStoreOpen) "Toko Buka" else "Toko Tutup",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OTP QR ROW WITH "SHOW QR CODE" BUTTON -> MODAL
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "OTP QR Kasir Verification",
                            color = Emerald100,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = otpCode,
                                color = Amber300,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 2.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = Color.Black.copy(alpha = 0.25f),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "⏱️ ${expiresInSeconds}s",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    color = Emerald100,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Button(
                        onClick = onOpenOtpQrModal,
                        colors = ButtonDefaults.buttonColors(containerColor = Amber400),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = null,
                            tint = Amber950,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Show QR Code", color = Amber950, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
