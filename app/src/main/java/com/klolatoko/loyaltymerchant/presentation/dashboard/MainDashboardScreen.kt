package com.klolatoko.loyaltymerchant.presentation.dashboard

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.klolatoko.loyaltymerchant.domain.model.MerchantTab
import com.klolatoko.loyaltymerchant.presentation.theme.*
import kotlinx.coroutines.delay

@Composable
fun GridBackground(
    isCrispGrid: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bgBgColor = Color(0xFFEFF2EF)

    Box(modifier = modifier.fillMaxSize().background(bgBgColor)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridSpacing = if (isCrispGrid) 24.dp.toPx() else 20.dp.toPx()
            val lineColor = if (isCrispGrid) {
                Color(0xFF94A3B8).copy(alpha = 0.55f)
            } else {
                Color(0xFFCBD5E1).copy(alpha = 0.35f)
            }
            val strokeWidth = if (isCrispGrid) 0.8.dp.toPx() else 0.35.dp.toPx()

            var x = 0f
            while (x < size.width) {
                drawLine(lineColor, Offset(x, 0f), Offset(x, size.height), strokeWidth)
                x += gridSpacing
            }
            var y = 0f
            while (y < size.height) {
                drawLine(lineColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
                y += gridSpacing
            }
        }

        if (!isCrispGrid) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                bgBgColor.copy(alpha = 0.25f),
                                Color.Transparent,
                                bgBgColor.copy(alpha = 0.20f)
                            )
                        )
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(
    viewModel: DashboardViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val view = LocalView.current

    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val threshold = with(density) { 80.dp.toPx() }
    val collapseProgress = (scrollState.value / threshold).coerceIn(0f, 1f)

    var activePengaturanSubScreen by remember { mutableStateOf("main") }

    LaunchedEffect(uiState.selectedTab) {
        if (uiState.selectedTab != MerchantTab.PENGATURAN) {
            activePengaturanSubScreen = "main"
        }
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            val isSubScreen = uiState.selectedTab == MerchantTab.PENGATURAN && activePengaturanSubScreen != "main"
            insetsController.isAppearanceLightStatusBars = isSubScreen
        }
    }

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(2800)
            viewModel.clearToastMessage()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            if (uiState.selectedTab == MerchantTab.PENGATURAN && activePengaturanSubScreen != "main") {
                val subTitle = when (activePengaturanSubScreen) {
                    "account" -> "Pengaturan Akun"
                    "loyalty" -> "Pengaturan Skema Loyalty"
                    "store" -> "Pengaturan Store & POS"
                    "about" -> "Tentang Aplikasi"
                    "privacy" -> "Kebijakan Privasi"
                    else -> "Pengaturan"
                }
                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    title = { Text(subTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    navigationIcon = {
                        IconButton(onClick = { activePengaturanSubScreen = "main" }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali",
                                tint = Slate800
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Slate800,
                        navigationIconContentColor = Slate800
                    )
                )
            } else {
                MerchantHeaderSection(
                    storeName = uiState.merchantProfile.storeName,
                    pendingCount = uiState.pendingClaims.size,
                    collapseProgress = collapseProgress,
                    onNotificationClick = { viewModel.selectTab(MerchantTab.APPROVAL) }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = uiState.selectedTab,
                pendingCount = uiState.pendingClaims.size,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        containerColor = Color(0xFFEFF2EF)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GridBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                when (uiState.selectedTab) {
                    MerchantTab.KASIR -> KasirTab(
                        merchantProfile = uiState.merchantProfile,
                        stampsCount = uiState.statsStampsToday,
                        vouchersCount = uiState.statsVouchersToday,
                        membersCount = uiState.members.size,
                        pendingCount = uiState.pendingClaims.size,
                        targetStamps = uiState.settings.targetStamps,
                        inputPhone = uiState.inputPhone,
                        stampQty = uiState.stampQty,
                        activityLogs = uiState.activityLogs,
                        onToggleStoreStatus = { viewModel.toggleStoreStatus() },
                        onOpenOtpQrModal = { viewModel.openOtpQrModal() },
                        onPhoneChange = { viewModel.setInputPhone(it) },
                        onAdjustQty = { viewModel.adjustStampQty(it) },
                        onSubmitGiveStamp = { viewModel.submitGiveStamp() },
                        onOpenRedeemModal = { viewModel.openRedeemVoucherModal() },
                        onSwitchToApproval = { viewModel.selectTab(MerchantTab.APPROVAL) }
                    )

                    MerchantTab.APPROVAL -> ApprovalTab(
                        pendingClaims = uiState.pendingClaims,
                        onSimulateClaim = { viewModel.simulateIncomingClaim() },
                        onOpenDetailModal = { viewModel.openApprovalDetailModal(it) },
                        onQuickApprove = { viewModel.quickApproveClaim(it) }
                    )

                    MerchantTab.PELANGGAN -> PelangganTab(
                        members = uiState.members,
                        searchQuery = uiState.searchMemberQuery,
                        targetStamps = uiState.settings.targetStamps,
                        onSearchChange = { viewModel.setSearchMemberQuery(it) }
                    )

                    MerchantTab.PENGATURAN -> PengaturanTab(
                        settings = uiState.settings,
                        activeSubScreen = activePengaturanSubScreen,
                        onActiveSubScreenChange = { activePengaturanSubScreen = it },
                        onSaveSettings = { type, target, title, autoReset ->
                            viewModel.saveSettings(type, target, title, autoReset)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Toast Floating Notification
            AnimatedVisibility(
                visible = uiState.toastMessage != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                uiState.toastMessage?.let { msg ->
                    Surface(
                        color = Slate800,
                        shape = CircleShape,
                        shadowElevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Emerald400,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = msg,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }

    // Modals
    if (uiState.showOtpQrModal) {
        OtpQrModal(
            storeName = uiState.merchantProfile.storeName,
            otpCode = uiState.merchantProfile.otpCode,
            expiresInSeconds = uiState.merchantProfile.otpExpiresInSeconds,
            onRegenerateOtpQr = { viewModel.regenerateOtpQr() },
            onDismiss = { viewModel.closeOtpQrModal() }
        )
    }

    if (uiState.showRedeemVoucherModal) {
        RedeemVoucherModal(
            voucherCode = uiState.voucherCodeInput,
            onCodeChange = { viewModel.setVoucherCodeInput(it) },
            onDismiss = { viewModel.closeRedeemVoucherModal() },
            onConfirm = { viewModel.confirmRedeemVoucher() }
        )
    }

    if (uiState.showApprovalDetailModal && uiState.selectedClaim != null) {
        ApprovalDetailModal(
            claim = uiState.selectedClaim!!,
            rejectReason = uiState.rejectReason,
            showRejectReasonBox = uiState.showRejectReasonBox,
            onRejectReasonChange = { viewModel.setRejectReason(it) },
            onToggleRejectBox = { viewModel.toggleRejectReasonBox() },
            onConfirmReject = { viewModel.confirmRejectClaim() },
            onConfirmApprove = { viewModel.quickApproveClaim(uiState.selectedClaim!!) },
            onDismiss = { viewModel.closeApprovalDetailModal() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerchantHeaderSection(
    storeName: String,
    pendingCount: Int,
    collapseProgress: Float,
    onNotificationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets.statusBars,
        title = {
            AnimatedContent(
                targetState = collapseProgress > 0.6f,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "TitleTransition"
            ) { isCollapsed ->
                if (isCollapsed) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color.White.copy(0.15f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("🥞", fontSize = 14.sp)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = storeName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else {
                    Text(
                        text = "KLOLA LOYALTY",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("JR", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                BadgedBox(
                    badge = {
                        if (pendingCount > 0) {
                            Badge { Text("$pendingCount") }
                        } else {
                            Badge { Text("2") }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Emerald600,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
fun QuickStatsBar(
    stampsCount: Int,
    vouchersCount: Int,
    membersCount: Int,
    pendingCount: Int,
    onApprovalClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard("Stamp / Poin", "$stampsCount", Emerald50, Emerald600, Modifier.weight(1f))
        StatCard("Voucher Tukar", "$vouchersCount", Amber50, Amber600, Modifier.weight(1f))
        StatCard("Member Total", "$membersCount", Teal50, Teal600, Modifier.weight(1f))
        StatCard(
            label = "Perlu Approval",
            value = "$pendingCount",
            bgColor = Rose50,
            textColor = Rose600,
            modifier = Modifier.weight(1f),
            isAlert = pendingCount > 0,
            onClick = onApprovalClick
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    bgColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    isAlert: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier.then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        color = bgColor,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, if (isAlert) Rose200 else Emerald100.copy(alpha = 0.5f))
    ) {
        Box {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = textColor
                )
            }
            if (isAlert) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(Rose500)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: MerchantTab,
    pendingCount: Int,
    onTabSelected: (MerchantTab) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.height(72.dp)
    ) {
        MerchantTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Box {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            modifier = Modifier.size(22.dp)
                        )
                        if (tab == MerchantTab.APPROVAL && pendingCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Rose500)
                                    .align(Alignment.TopEnd)
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Emerald600,
                    selectedTextColor = Emerald600,
                    unselectedIconColor = Slate400,
                    unselectedTextColor = Slate400,
                    indicatorColor = Emerald50.copy(0.5f)
                )
            )
        }
    }
}
