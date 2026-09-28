package com.klolatoko.loyaltymerchant.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klolatoko.loyaltymerchant.domain.model.Claim
import com.klolatoko.loyaltymerchant.presentation.theme.*

@Composable
fun ApprovalTab(
    pendingClaims: List<Claim>,
    onSimulateClaim: () -> Unit,
    onOpenDetailModal: (Claim) -> Unit,
    onQuickApprove: (Claim) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Persetujuan Klaim",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Slate800,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Antrean Verifikasi Struk",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate800
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(color = Rose100, shape = CircleShape) {
                        Text(
                            text = "${pendingClaims.size} Pending",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            color = Rose700,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
                Text(
                    text = "Verifikasi struk & nominal belanja yang diajukan pelanggan",
                    fontSize = 11.sp,
                    color = Slate500
                )
            }

            Button(
                onClick = onSimulateClaim,
                colors = ButtonDefaults.buttonColors(containerColor = Emerald100, contentColor = Emerald800),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("+Simulasi Klaim", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (pendingClaims.isEmpty()) {
            Surface(
                color = Slate50,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Emerald100),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Emerald600,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Semua Klaim Sudah Dikonfirmasi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate800
                    )
                    Text(
                        text = "Tidak ada pengajuan poin baru dari pelanggan saat ini.",
                        fontSize = 12.sp,
                        color = Slate400
                    )
                }
            }
        } else {
            pendingClaims.forEach { claim ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Rose200),
                    shadowElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Rose100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = claim.userName.take(2).uppercase(),
                                        color = Rose700,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = claim.userName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                    Text(
                                        text = "${claim.phone} • ${claim.time}",
                                        fontSize = 11.sp,
                                        color = Slate400
                                    )
                                }
                            }
                            Surface(color = Rose100, shape = CircleShape) {
                                Text(
                                    text = "Perlu Verification",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    color = Rose800,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                        Surface(
                            color = Slate50,
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
                                Column {
                                    Text("Nominal Struk", fontSize = 10.sp, color = Slate400, fontWeight = FontWeight.Medium)
                                    Text("Rp ${claim.amountSpent}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Slate800)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Perolehan", fontSize = 10.sp, color = Emerald600, fontWeight = FontWeight.Medium)
                                    Text("+${claim.calculatedPts} Pts", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Emerald600)
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onOpenDetailModal(claim) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Slate100, contentColor = Slate700),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Slate200),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Lihat Struk & Detail", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { onQuickApprove(claim) },
                                colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(text = "Setujui", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
