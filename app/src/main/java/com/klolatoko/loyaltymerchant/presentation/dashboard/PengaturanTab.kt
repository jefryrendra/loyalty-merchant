package com.klolatoko.loyaltymerchant.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var dropdownExpanded by remember { mutableStateOf(false) }

    val options = listOf(
        "stamp" to "Stamp / Stiker Digital (Cocok untuk Martabak/Kuliner)",
        "points" to "Dynamic Points & Level Tiering"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Pengaturan Loyalty",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate800,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Atur skema reward & bonus untuk usaha martabak kamu",
                fontSize = 12.sp,
                color = Slate500
            )
        }

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Slate200),
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dropdown Selector
                Column {
                    Text(
                        text = "Tipe Loyalty System",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { dropdownExpanded = true },
                            color = Slate50,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Slate200)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = options.find { it.first == selectedType }?.second ?: options[0].second,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Slate800,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Slate600
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            options.forEach { (typeVal, label) ->
                                DropdownMenuItem(
                                    text = { Text(label, fontSize = 12.sp) },
                                    onClick = {
                                        selectedType = typeVal
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Target Stamps Input
                Column {
                    Text(
                        text = "Target Stiker Per Hadiah",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = targetStampsText,
                        onValueChange = { targetStampsText = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Emerald500,
                            unfocusedBorderColor = Slate200,
                            focusedContainerColor = Slate50,
                            unfocusedContainerColor = Slate50
                        )
                    )
                    Text(
                        text = "Misal: 10 Stiker dapat 1 bonus martabak manis",
                        fontSize = 10.sp,
                        color = Slate400,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Reward Title Input
                Column {
                    Text(
                        text = "Nama Hadiah Bonus",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = rewardTitleText,
                        onValueChange = { rewardTitleText = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Emerald500,
                            unfocusedBorderColor = Slate200,
                            focusedContainerColor = Slate50,
                            unfocusedContainerColor = Slate50
                        )
                    )
                }

                HorizontalDivider(color = Slate100)

                // Auto Reset Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Reset PIN Harian Otomatis",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                        Text(
                            text = "Ganti PIN tiap pukul 00:00 untuk cegah kecurangan",
                            fontSize = 10.sp,
                            color = Slate400
                        )
                    }
                    Switch(
                        checked = autoResetPinChecked,
                        onCheckedChange = { autoResetPinChecked = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Emerald600
                        )
                    )
                }

                Button(
                    onClick = {
                        val parsedTarget = targetStampsText.toIntOrNull() ?: 10
                        onSaveSettings(selectedType, parsedTarget, rewardTitleText, autoResetPinChecked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Simpan Perubahan Settings",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
