package com.klolatoko.loyaltymerchant.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.klolatoko.loyaltymerchant.presentation.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.75f,
        animationSpec = tween(durationMillis = 900),
        label = "splashScale"
    )
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "splashAlpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2200)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val centerOffset = Offset(w / 2f, h * 0.42f)

            drawCircle(
                color = Color.White.copy(alpha = 0.06f),
                radius = w * 0.95f,
                center = centerOffset
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = w * 0.72f,
                center = centerOffset
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.10f),
                radius = w * 0.50f,
                center = centerOffset
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.12f),
                radius = w * 0.32f,
                center = centerOffset
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .graphicsLayer {
                    scaleX = scaleAnimation
                    scaleY = scaleAnimation
                    alpha = alphaAnimation
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SplashHeroIllustration()
                }

                Spacer(Modifier.height(28.dp))

                Text(
                    text = "KLOLA MERCHANT",
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp,
                    color = Color.White,
                    letterSpacing = 3.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Dashboard Kasir & Kelola Loyalty Merchant",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Emerald200,
                    strokeWidth = 2.5.dp
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "v1.0.0",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun SplashHeroIllustration() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
        )

        Surface(
            modifier = Modifier
                .width(115.dp)
                .height(175.dp)
                .graphicsLayer {
                    rotationZ = -6f
                    shadowElevation = 16f
                },
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(2.dp, Color.White.copy(alpha = 0.85f))
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
                        .padding(top = 6.dp)
                        .width(36.dp)
                        .height(7.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Slate800)
                        .align(Alignment.TopCenter)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 22.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Emerald600
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                        ) {
                            Text(
                                text = "MERCHANT",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Amber500,
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = Slate100
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(6) {
                                Box(
                                    modifier = Modifier
                                        .width(if (it % 2 == 0) 4.dp else 2.dp)
                                        .fillMaxHeight()
                                        .background(Slate800)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(CircleShape)
                            .background(Emerald600)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .offset(x = (-46).dp, y = (-52).dp)
                .size(40.dp)
                .graphicsLayer { rotationZ = 8f },
            shape = CircleShape,
            color = Color(0xFFE0F2FE),
            shadowElevation = 8.dp,
            border = BorderStroke(1.5.dp, Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color(0xFF0284C7),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Surface(
            modifier = Modifier
                .offset(x = 46.dp, y = 42.dp)
                .size(42.dp)
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
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Surface(
            modifier = Modifier
                .offset(x = 42.dp, y = (-36).dp)
                .size(34.dp),
            shape = CircleShape,
            color = Emerald100,
            shadowElevation = 6.dp,
            border = BorderStroke(1.5.dp, Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Emerald600,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
