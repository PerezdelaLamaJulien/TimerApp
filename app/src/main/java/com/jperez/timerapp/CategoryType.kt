package com.jperez.timerapp

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.Binary
import com.composables.icons.lucide.Book
import com.composables.icons.lucide.BriefcaseBusiness
import com.composables.icons.lucide.ChefHat
import com.composables.icons.lucide.Clock
import com.composables.icons.lucide.Dumbbell
import com.composables.icons.lucide.Gamepad2
import com.composables.icons.lucide.GraduationCap
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Music
import com.composables.icons.lucide.Sprout


enum class CategoryType(val icon: ImageVector) {
    DEFAULT(Lucide.Clock),
    WORKOUT(Lucide.Dumbbell),
    WORK(Lucide.BriefcaseBusiness),
    STUDY(Lucide.GraduationCap),
    READ(Lucide.Book),
    MUSIC(Lucide.Music),
    GAME(Lucide.Gamepad2),
    DEV(Lucide.Binary),
    GARDENING(Lucide.Sprout),
    COOK(Lucide.ChefHat),
}