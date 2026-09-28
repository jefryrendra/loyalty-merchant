package com.klolatoko.loyaltymerchant.presentation.dashboard

import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.klolatoko.loyaltymerchant.domain.model.Claim
import com.klolatoko.loyaltymerchant.presentation.theme.*

fun generateRealQrCodeBitmap(content: String, size: Int = 512): ImageBitmap? {
    return try {
        val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                val pixelColor = if (bitMatrix.get(x, y)) AndroidColor.BLACK else AndroidColor.WHITE
                bitmap.setPixel(x, y, pixelColor)
            }
        }
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}

@Composable
fun OtpQrModal(
    storeName: String,
    otpCode: String,
    expiresInSeconds: Int,
    onRegenerateOtpQr: () -> Unit,
    onDismiss: () -> Unit
) {
    val qrBitmap = remember(otpCode) {
        val qrData = "https://loyalty.app/claim?store=${storeName.replace(" ", "_")}&otp=${otpCode.replace(" ", "")}"
        generateRealQrCodeBitmap(qrData, 512)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Slate100)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = Slate600,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(2.dp, Emerald500),
                        color = Color.Transparent
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Emerald50.copy(alpha = 0.5f), Color.White)
                                    )
                                )
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Emerald600),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🥞", fontSize = 22.sp)
                            }

                            Text(
                                text = storeName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Slate800,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "OTP QR KASIR (VERIFIKASI DINAMIS)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Emerald700,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp
                            )

                            // Real Scannable QR Code Image
                            Surface(
                                modifier = Modifier.padding(vertical = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Slate200),
                                color = Color.White
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (qrBitmap != null) {
                                        Image(
                                            bitmap = qrBitmap,
                                            contentDescription = "Real QR Code",
                                            modifier = Modifier.size(190.dp)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(190.dp)
                                                .background(Slate100),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(color = Emerald600)
                                        }
                                    }

                                    Spacer(Modifier.height(10.dp))

                                    Text(
                                        text = otpCode,
                                        fontSize = 24.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Black,
                                        color = Emerald800,
                                        letterSpacing = 3.sp
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Surface(
                                        color = Slate100,
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            text = "⏱️ Refresh otomatis dalam ${expiresInSeconds}s",
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Slate600
                                        )
                                    }
                                }
                            }

                            // Instructions Box
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = Slate50,
                                border = BorderStroke(1.dp, Slate100)
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Cara Penggunaan:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                    Text("1. Tunjukkan QR ini kepada pelanggan di kasir.", fontSize = 10.sp, color = Slate600)
                                    Text("2. Pelanggan scan QR via HP untuk verifikasi transaksi.", fontSize = 10.sp, color = Slate600)
                                    Text("3. Kode QR diperbarui otomatis tiap 30 detik.", fontSize = 10.sp, color = Slate600)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onRegenerateOtpQr,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Amber400, contentColor = Amber950)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Refresh OTP Baru", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Slate100, contentColor = Slate600)
                        ) {
                            Text("Tutup", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RedeemVoucherModal(
    voucherCode: String,
    onCodeChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Slate100)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = Slate600,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Amber100),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = Amber600,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Verifikasi Penukaran Voucher",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Slate800,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Masukkan 5-digit Kode Unik yang ada di layar HP Pembeli",
                            fontSize = 12.sp,
                            color = Slate500,
                            textAlign = TextAlign.Center
                        )
                    }

                    OutlinedTextField(
                        value = voucherCode,
                        onValueChange = { onCodeChange(it.uppercase()) },
                        placeholder = { Text("MTRK-889", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Amber700.copy(alpha = 0.4f)) },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            color = Amber950,
                            letterSpacing = 2.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Amber400,
                            unfocusedBorderColor = Amber300,
                            focusedContainerColor = Amber50.copy(alpha = 0.5f),
                            unfocusedContainerColor = Amber50.copy(alpha = 0.3f)
                        )
                    )

                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                    ) {
                        Text("Verifikasi & Potong Porsi Bonus", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ApprovalDetailModal(
    claim: Claim,
    rejectReason: String,
    showRejectReasonBox: Boolean,
    onRejectReasonChange: (String) -> Unit,
    onToggleRejectBox: () -> Unit,
    onConfirmReject: () -> Unit,
    onConfirmApprove: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .defaultMinSize(minHeight = 400.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Slate100)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = Slate600,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(color = Rose100, shape = CircleShape) {
                            Text(
                                text = "PERLU KONFIRMASI KASIR",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                color = Rose700,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = claim.userName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Slate800
                        )
                        Text(
                            text = "Mengajukan klaim via Web PWA • ${claim.time}",
                            fontSize = 11.sp,
                            color = Slate400
                        )
                    }

                    Surface(
                        color = Emerald50,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Emerald200)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Total Diajukan:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Slate500)
                                Text("Rp ${claim.amountSpent}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Slate800)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Estimasi Poin:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Emerald700)
                                Text("+${claim.calculatedPts} Pts", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Emerald600)
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Bukti Struk & Detail Transaksi API", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate700)

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Slate900
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = claim.receiptImg,
                                    contentDescription = "Foto Struk",
                                    modifier = Modifier.fillMaxSize()
                                )
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp),
                                    color = Slate800.copy(alpha = 0.85f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "Foto Struk Pelanggan",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = Slate200,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = Slate50,
                            border = BorderStroke(1.dp, Slate200)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Ref ID: ${claim.refId}", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Slate600)
                                    Text("Merchant Toko", fontSize = 11.sp, color = Slate500)
                                }
                                HorizontalDivider(color = Slate200)
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    claim.items.forEach { item ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(item.name, fontSize = 11.sp, color = Slate700)
                                            Text("Rp ${item.price}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate800)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (showRejectReasonBox) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Alasan Penolakan Klaim", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Rose700)
                            OutlinedTextField(
                                value = rejectReason,
                                onValueChange = onRejectReasonChange,
                                placeholder = { Text("Contoh: Foto struk buram / Struk sudah pernah diclaim", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Rose500,
                                    unfocusedBorderColor = Rose200
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (!showRejectReasonBox) {
                                    onToggleRejectBox()
                                } else {
                                    onConfirmReject()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (showRejectReasonBox) Rose600 else Slate100,
                                contentColor = if (showRejectReasonBox) Color.White else Rose600
                            ),
                            border = if (!showRejectReasonBox) BorderStroke(1.dp, Slate200) else null
                        ) {
                            Text(if (showRejectReasonBox) "Konfirmasi Tolak" else "Tolak Klaim", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onConfirmApprove,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                        ) {
                            Text("Setujui & Tambah Poin", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
