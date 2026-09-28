package com.klolatoko.loyaltymerchant.presentation.auth

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
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.klolatoko.loyaltymerchant.presentation.theme.*

@Composable
fun AuthHeaderSection(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    headerHeight: Dp = 500.dp,
    visibleHeaderHeight: Dp = 260.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Emerald600,
                        Emerald500
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                radius = w * 0.45f,
                center = Offset(w * 0.85f, h * 0.25f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.04f),
                radius = w * 0.65f,
                center = Offset(w * 0.85f, h * 0.25f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.06f),
                radius = w * 0.35f,
                center = Offset(w * 0.1f, h * 0.8f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(visibleHeaderHeight)
                .statusBarsPadding()
                .padding(horizontal = 22.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1.1f)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 21.sp,
                    color = Color.White,
                    lineHeight = 26.sp
                )
                if (subtitle != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.82f),
                        lineHeight = 17.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .height(170.dp),
                contentAlignment = Alignment.Center
            ) {
                AuthHeroIllustration()
            }
        }
    }
}

@Composable
fun AuthHeroIllustration() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.12f))
        )

        Surface(
            modifier = Modifier
                .width(98.dp)
                .height(150.dp)
                .graphicsLayer {
                    rotationZ = -6f
                    shadowElevation = 10f
                },
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(1.5.dp, Color.White.copy(alpha = 0.8f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Emerald50,
                                Color.White
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(30.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Slate800)
                        .align(Alignment.TopCenter)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 18.dp, start = 6.dp, end = 6.dp, bottom = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Emerald600
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                        ) {
                            Text(
                                text = "MERCHANT",
                                fontSize = 6.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Amber500,
                                modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }

                    Spacer(Modifier.height(6.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp),
                        shape = RoundedCornerShape(5.dp),
                        color = Slate100
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(6) {
                                Box(
                                    modifier = Modifier
                                        .width(if (it % 2 == 0) 3.dp else 1.5.dp)
                                        .fillMaxHeight()
                                        .background(Slate800)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(CircleShape)
                            .background(Emerald600)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .offset(x = (-38).dp, y = (-44).dp)
                .size(34.dp)
                .graphicsLayer { rotationZ = 8f },
            shape = CircleShape,
            color = Color(0xFFE0F2FE),
            shadowElevation = 6.dp,
            border = BorderStroke(1.5.dp, Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color(0xFF0284C7),
                    modifier = Modifier.size(17.dp)
                )
            }
        }

        Surface(
            modifier = Modifier
                .offset(x = 38.dp, y = 34.dp)
                .size(36.dp)
                .graphicsLayer { rotationZ = -10f },
            shape = CircleShape,
            color = Color(0xFFFEF3C7),
            shadowElevation = 8.dp,
            border = BorderStroke(1.5.dp, Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.CardGiftcard,
                    contentDescription = null,
                    tint = Amber800,
                    modifier = Modifier.size(19.dp)
                )
            }
        }

        Surface(
            modifier = Modifier
                .offset(x = 36.dp, y = (-28).dp)
                .size(28.dp),
            shape = CircleShape,
            color = Emerald100,
            shadowElevation = 4.dp,
            border = BorderStroke(1.5.dp, Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Emerald600,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun BoxScope.AuthFormGridCanvas(modifier: Modifier = Modifier) {
    val bgBgColor = Color(0xFFEFF2EF)
    val giftImageUrl = "https://images.unsplash.com/photo-1513201099705-a9746e1e201f?q=80&w=600"

    Box(
        modifier = modifier
            .matchParentSize()
            .background(bgBgColor)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 90.dp, y = 20.dp)
                .size(340.dp)
                .clip(CircleShape)
                .graphicsLayer { alpha = 0.12f }
        ) {
            AsyncImage(
                model = giftImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                bgBgColor,
                                bgBgColor.copy(alpha = 0.85f),
                                bgBgColor.copy(alpha = 0.40f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridSpacing = 20.dp.toPx()
            val lineColor = Color(0xFFCBD5E1).copy(alpha = 0.35f)
            val strokeWidth = 0.35.dp.toPx()

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
    }
}

@Composable
fun AuthCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    enabled: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label, color = Slate400, fontSize = 14.sp) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Emerald600,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password",
                        tint = Slate400,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else null,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Emerald600,
            unfocusedBorderColor = Slate200,
            disabledBorderColor = Slate200,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedTextColor = Slate800,
            unfocusedTextColor = Slate800
        )
    )
}

@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val scaleX = size.width / 24f
        val scaleY = size.height / 24f

        drawPath(
            path = Path().apply {
                moveTo(23.745f * scaleX, 12.27f * scaleY)
                cubicTo(23.745f * scaleX, 11.57f * scaleY, 23.685f * scaleX, 10.87f * scaleY, 23.555f * scaleX, 10.2f * scaleY)
                lineTo(12f * scaleX, 10.2f * scaleY)
                lineTo(12f * scaleX, 14.71f * scaleY)
                lineTo(18.6f * scaleX, 14.71f * scaleY)
                cubicTo(18.31f * scaleX, 16.23f * scaleY, 17.46f * scaleX, 17.53f * scaleY, 16.2f * scaleX, 18.39f * scaleY)
                lineTo(16.2f * scaleX, 21.44f * scaleY)
                lineTo(20.08f * scaleX, 21.44f * scaleY)
                cubicTo(22.35f * scaleX, 19.35f * scaleY, 23.745f * scaleX, 16.27f * scaleY, 23.745f * scaleX, 12.27f * scaleY)
                close()
            },
            color = Color(0xFF4285F4)
        )

        drawPath(
            path = Path().apply {
                moveTo(12f * scaleX, 24f * scaleY)
                cubicTo(15.24f * scaleX, 24f * scaleY, 17.95f * scaleX, 22.92f * scaleY, 19.93f * scaleX, 21.09f * scaleY)
                lineTo(16.05f * scaleX, 18.04f * scaleY)
                cubicTo(14.97f * scaleX, 18.76f * scaleY, 13.6f * scaleX, 19.2f * scaleY, 12f * scaleX, 19.2f * scaleY)
                cubicTo(8.88f * scaleX, 19.2f * scaleY, 6.23f * scaleX, 17.09f * scaleY, 5.28f * scaleX, 14.24f * scaleY)
                lineTo(1.29f * scaleX, 14.24f * scaleY)
                lineTo(1.29f * scaleX, 17.37f * scaleY)
                cubicTo(3.26f * scaleX, 21.3f * scaleY, 7.31f * scaleX, 24f * scaleY, 12f * scaleX, 24f * scaleY)
                close()
            },
            color = Color(0xFF34A853)
        )

        drawPath(
            path = Path().apply {
                moveTo(5.28f * scaleX, 14.24f * scaleY)
                cubicTo(5.03f * scaleX, 13.52f * scaleY, 4.9f * scaleX, 12.75f * scaleY, 4.9f * scaleX, 12f * scaleY)
                cubicTo(4.9f * scaleX, 11.25f * scaleY, 5.03f * scaleX, 10.48f * scaleY, 5.28f * scaleX, 9.76f * scaleY)
                lineTo(5.28f * scaleX, 6.63f * scaleY)
                lineTo(1.29f * scaleX, 6.63f * scaleY)
                cubicTo(0.47f * scaleX, 8.24f * scaleY, 0f, 10.06f * scaleY, 0f, 12f * scaleY)
                cubicTo(0f, 13.94f * scaleY, 0.47f * scaleX, 15.76f * scaleY, 1.29f * scaleX, 17.37f * scaleY)
                lineTo(5.28f * scaleX, 14.24f * scaleY)
                close()
            },
            color = Color(0xFFFBBC05)
        )

        drawPath(
            path = Path().apply {
                moveTo(12f * scaleX, 4.75f * scaleY)
                cubicTo(13.77f * scaleX, 4.75f * scaleY, 15.35f * scaleX, 5.36f * scaleY, 16.6f * scaleX, 6.55f * scaleY)
                lineTo(20.02f * scaleX, 3.13f * scaleY)
                cubicTo(17.95f * scaleX, 1.19f * scaleY, 15.24f * scaleX, 0f, 12f * scaleX, 0f)
                cubicTo(7.31f * scaleX, 0f, 3.26f * scaleX, 2.7f * scaleY, 1.29f * scaleX, 6.63f * scaleY)
                lineTo(5.28f * scaleX, 9.76f * scaleY)
                cubicTo(6.23f * scaleX, 6.91f * scaleY, 8.88f * scaleX, 4.75f * scaleY, 12f * scaleX, 4.75f * scaleY)
                close()
            },
            color = Color(0xFFEA4335)
        )
    }
}

@Composable
fun SignInScreen(
    isLoading: Boolean = false,
    onSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var identity by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2EF))
    ) {
        AuthHeaderSection(
            title = "Masuk Kasir & Dashboard Merchant",
            subtitle = "Kelola poin, stamp, dan approval klaim pelanggan Toko Anda.",
            modifier = Modifier.align(Alignment.TopCenter),
            headerHeight = 550.dp,
            visibleHeaderHeight = 260.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(250.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 700.dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color(0xFFEFF2EF),
                shadowElevation = 12.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AuthFormGridCanvas()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Login Merchant",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Slate800,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Belum Daftarkan Toko? ",
                                fontSize = 13.sp,
                                color = Slate500,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Daftar",
                                fontSize = 13.sp,
                                color = Emerald600,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { onNavigateToSignUp() }
                            )
                        }

                        Spacer(Modifier.height(22.dp))

                        AuthCustomTextField(
                            value = identity,
                            onValueChange = { identity = it },
                            label = "No. Handphone / Email Merchant",
                            icon = Icons.Default.Person,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(14.dp))

                        AuthCustomTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Kata Sandi",
                            icon = Icons.Default.Lock,
                            isPassword = true,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(4.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            TextButton(
                                onClick = onNavigateToForgotPassword,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Lupa Kata Sandi?",
                                    fontSize = 13.sp,
                                    color = Emerald600,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        Button(
                            onClick = onSignIn,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                            enabled = !isLoading,
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(22.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Login",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = Slate200
                            )
                            Text(
                                text = "Atau Masuk Dengan",
                                modifier = Modifier.padding(horizontal = 12.dp),
                                fontSize = 12.sp,
                                color = Slate400,
                                fontWeight = FontWeight.Medium
                            )
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = Slate200
                            )
                        }

                        Surface(
                            onClick = onSignIn,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate200),
                            shadowElevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GoogleLogoIcon(modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "Lanjutkan dengan Google",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Slate800
                                )
                            }
                        }

                        Spacer(Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    isLoading: Boolean = false,
    onSignUp: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var storeName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2EF))
    ) {
        AuthHeaderSection(
            title = "Daftarkan Toko & Nikmati Loyalty System",
            subtitle = "Kembangkan bisnis kuliner & UMKM Anda dengan stamp digital.",
            modifier = Modifier.align(Alignment.TopCenter),
            headerHeight = 500.dp,
            visibleHeaderHeight = 210.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(200.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 700.dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color(0xFFEFF2EF),
                shadowElevation = 12.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AuthFormGridCanvas()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Registrasi Merchant",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Slate800,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sudah Punya Akun? ",
                                fontSize = 13.sp,
                                color = Slate500,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Masuk",
                                fontSize = 13.sp,
                                color = Emerald600,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { onNavigateToSignIn() }
                            )
                        }

                        Spacer(Modifier.height(22.dp))

                        AuthCustomTextField(
                            value = storeName,
                            onValueChange = { storeName = it },
                            label = "Nama Toko / Usaha",
                            icon = Icons.Default.Store,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(14.dp))

                        AuthCustomTextField(
                            value = ownerName,
                            onValueChange = { ownerName = it },
                            label = "Nama Owner / Penanggung Jawab",
                            icon = Icons.Default.Person,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(14.dp))

                        AuthCustomTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = "Nomor WhatsApp Toko",
                            icon = Icons.Default.Phone,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(14.dp))

                        AuthCustomTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Email Usaha",
                            icon = Icons.Default.Email,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(14.dp))

                        AuthCustomTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Kata Sandi",
                            icon = Icons.Default.Lock,
                            isPassword = true,
                            enabled = !isLoading
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = onSignUp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                            enabled = !isLoading,
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(22.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Daftarkan Merchant",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = Slate200
                            )
                            Text(
                                text = "Atau Masuk Dengan",
                                modifier = Modifier.padding(horizontal = 12.dp),
                                fontSize = 12.sp,
                                color = Slate400,
                                fontWeight = FontWeight.Medium
                            )
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = Slate200
                            )
                        }

                        Surface(
                            onClick = onSignUp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate200),
                            shadowElevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GoogleLogoIcon(modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "Lanjutkan dengan Google",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Slate800
                                )
                            }
                        }

                        Spacer(Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    isLoading: Boolean = false,
    onResetPassword: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var identity by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2EF))
    ) {
        AuthHeaderSection(
            title = "Lupa Kata Sandi Merchant?",
            subtitle = "Masukkan No. Handphone atau Email terdaftar untuk pemulihan akun Toko.",
            modifier = Modifier.align(Alignment.TopCenter),
            headerHeight = 450.dp,
            visibleHeaderHeight = 350.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(310.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 500.dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color(0xFFEFF2EF),
                shadowElevation = 12.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AuthFormGridCanvas()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Reset Kata Sandi",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Slate800,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sudah ingat kata sandi? ",
                                fontSize = 13.sp,
                                color = Slate500,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Masuk",
                                fontSize = 13.sp,
                                color = Emerald600,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { onNavigateToSignIn() }
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Kami akan mengirimkan kode OTP atau instruksi pemulihan kata sandi.",
                            fontSize = 12.sp,
                            color = Slate500,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )

                        Spacer(Modifier.height(22.dp))

                        AuthCustomTextField(
                            value = identity,
                            onValueChange = { identity = it },
                            label = "No. Handphone / Email Toko",
                            icon = Icons.Default.Person,
                            enabled = !isLoading && !isSubmitted
                        )

                        Spacer(Modifier.height(22.dp))

                        Button(
                            onClick = {
                                isSubmitted = true
                                onResetPassword()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                            enabled = !isLoading && identity.isNotBlank(),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = "Kirim Kode OTP",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        onSignIn = {},
        onNavigateToSignUp = {},
        onNavigateToForgotPassword = {}
    )
}
