package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = VibrantDarkPrimary,
    onPrimary = VibrantDarkOnPrimary,
    primaryContainer = VibrantDarkPrimaryContainer,
    onPrimaryContainer = Color(0xFFD7E8CD),
    secondary = Color(0xFFBFC8B9),
    onSecondary = Color(0xFF1E3524),
    secondaryContainer = Color(0xFF2E382A),
    onSecondaryContainer = Color(0xFFD7E8CD),
    tertiary = Color(0xFF80D5C9),
    onTertiary = Color(0xFF003732),
    background = VibrantDarkBg,
    surface = VibrantDarkSurface,
    surfaceVariant = VibrantDarkSurfaceVariant,
    outline = Color(0xFF8D9286),
    outlineVariant = Color(0xFF43493E),
    onBackground = Color(0xFFE2E3DC),
    onSurface = Color(0xFFE2E3DC),
    onSurfaceVariant = Color(0xFFC3C8BC),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = VibrantGreen,
    onPrimary = Color.White,
    primaryContainer = VibrantSageContainer,
    onPrimaryContainer = VibrantDarkGreen,
    secondary = VibrantMutedText,
    onSecondary = Color.White,
    secondaryContainer = VibrantSageLight,
    onSecondaryContainer = VibrantDarkGreen,
    tertiary = Color(0xFF386663),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFBCEBE5),
    onTertiaryContainer = Color(0xFF00201E),
    background = VibrantCanvas,
    surface = Color.White,
    surfaceVariant = VibrantSageLight,
    outline = VibrantBorder,
    outlineVariant = VibrantBorderLight,
    onBackground = VibrantDarkGreen,
    onSurface = Color(0xFF1A1C18),
    onSurfaceVariant = VibrantMutedText,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Preserve our handcrafted Vibrant Palette design theme
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}


