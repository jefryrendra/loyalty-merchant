package com.klolatoko.loyaltymerchant.presentation.dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klolatoko.loyaltymerchant.domain.model.Member
import com.klolatoko.loyaltymerchant.presentation.theme.*

@Composable
fun PelangganTab(
    members: List<Member>,
    searchQuery: String,
    targetStamps: Int,
    onSearchChange: (String) -> Unit
) {
    val context = LocalContext.current
    val filteredMembers = members.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.phone.contains(searchQuery)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Direktori Pelanggan",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate800,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Daftar pelanggan terdaftar & re-engage lewat WhatsApp",
                fontSize = 12.sp,
                color = Slate500
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Cari nama atau no. HP...", fontSize = 12.sp, color = Slate400) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Slate400,
                    modifier = Modifier.size(18.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Emerald500,
                unfocusedBorderColor = Slate200,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (filteredMembers.isEmpty()) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Member tidak ditemukan.",
                            fontSize = 12.sp,
                            color = Slate400
                        )
                    }
                }
            } else {
                filteredMembers.forEach { m ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Slate200),
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Emerald100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = m.name.take(2).uppercase(),
                                        color = Emerald800,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = m.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                    Text(
                                        text = m.phone,
                                        fontSize = 11.sp,
                                        color = Slate400
                                    )
                                    Row(
                                        modifier = Modifier.padding(top = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Surface(
                                            color = Emerald50,
                                            shape = CircleShape,
                                            border = BorderStroke(1.dp, Emerald200)
                                        ) {
                                            Text(
                                                text = "${m.stamps}/$targetStamps Stamp",
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                                color = Emerald700,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Text(
                                            text = "Total Beli: ${m.totalVisits}x",
                                            fontSize = 10.sp,
                                            color = Slate400
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    val formattedPhone = if (m.phone.startsWith("0")) m.phone.replaceFirst("0", "62") else m.phone
                                    val message = Uri.encode("Halo ${m.name}! 🥞 Ada promo spesial dari Martabak Bangka Lekker nih. Stiker kamu sudah terkumpul, yuk mampir lagi!")
                                    val url = "https://wa.me/$formattedPhone?text=$message"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Emerald50, contentColor = Emerald700),
                                border = BorderStroke(1.dp, Emerald200),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = null,
                                    tint = Emerald600,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "WA", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
