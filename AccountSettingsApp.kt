package com.example.accountsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ─────────────────────────────────────────────
// Theme Colors
// ─────────────────────────────────────────────
val MintBackground = Color(0xFFFFFFFF)
val GoldButton     = Color(0xFFB8970A)
val TextPrimary    = Color(0xFF1A1A1A)
val TextSecondary  = Color(0xFF777777)
val Divider        = Color(0xFFDDDDDD)
val White          = Color(0xFFFFFFFF)
val CardBorder     = Color(0xFFCCCCCC)

// ─────────────────────────────────────────────
// Navigation Routes
// ─────────────────────────────────────────────
object Routes {
    const val ACCOUNT_SETTINGS  = "account_settings"
    const val CHANGE_PASSWORD   = "change_password"
    const val BANK_CARDS        = "bank_cards"
}

// ─────────────────────────────────────────────
// Bottom Navigation Bar
// ─────────────────────────────────────────────
data class BottomNavItem(val label: String, val icon: ImageVector)

val bottomNavItems = listOf(
    BottomNavItem("Home",    Icons.Outlined.Home),
    BottomNavItem("Explore", Icons.Outlined.Explore),
    BottomNavItem("Orders",  Icons.Outlined.ShoppingCart),
    BottomNavItem("Chat",    Icons.Outlined.ChatBubbleOutline),
    BottomNavItem("Account", Icons.Outlined.Person),
)

@Composable
fun AppBottomBar(selectedIndex: Int = 4) {
    NavigationBar(
        containerColor = White,
        tonalElevation = 4.dp
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick  = {},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(item.label, fontSize = 10.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = TextPrimary,
                    selectedTextColor   = TextPrimary,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = Color.Transparent
                )
            )
        }
    }
}

// ─────────────────────────────────────────────
// Root Navigation
// ─────────────────────────────────────────────
@Composable
fun AccountSettingsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.ACCOUNT_SETTINGS) {
        composable(Routes.ACCOUNT_SETTINGS) { AccountSettingsScreen(navController) }
        composable(Routes.CHANGE_PASSWORD)  { ChangePasswordScreen(navController) }
        composable(Routes.BANK_CARDS)       { BankCardsScreen(navController) }
    }
}

// ─────────────────────────────────────────────
// Screen 1 – Account Settings
// ─────────────────────────────────────────────
@Composable
fun AccountSettingsScreen(navController: NavController) {
    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = MintBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            // Title
            Text(
                text = "Account Settings",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(16.dp))

            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar placeholder
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDDDDDD)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color(0xFF888888),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("John Lennon", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextPrimary)
                        Text("View Profile", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Basic Information")
            Spacer(Modifier.height(4.dp))

            InfoRow("Name",     "John Lennon")
            InfoRow("Gender",   "Male")
            InfoRow("Location", "Pasig City")
            InfoRow("Email",    "john.@gmail.com")

            Spacer(Modifier.height(20.dp))
            SectionLabel("Settings")
            Spacer(Modifier.height(4.dp))

            HorizontalDivider(color = Divider)
            SettingsRow("Change Password")  { navController.navigate(Routes.CHANGE_PASSWORD) }
            HorizontalDivider(color = Divider)
            SettingsRow("Bank Account / Cards") { navController.navigate(Routes.BANK_CARDS) }
            HorizontalDivider(color = Divider)
            SettingsRow("Privacy Settings") {}
            HorizontalDivider(color = Divider)
            SettingsRow("Payment History")  {}
            HorizontalDivider(color = Divider)

            Spacer(Modifier.height(24.dp))

            // Logout Button
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Text("Logout", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.Logout, contentDescription = "Logout", modifier = Modifier.size(18.dp))
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(value, fontSize = 14.sp, color = TextSecondary)
            Spacer(Modifier.width(6.dp))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
        }
    }
    HorizontalDivider(color = Divider)
}

@Composable
fun SettingsRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp, color = TextPrimary)
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
    }
}

// ─────────────────────────────────────────────
// Screen 2 – Change Password
// ─────────────────────────────────────────────
@Composable
fun ChangePasswordScreen(navController: NavController) {
    var currentPassword  by remember { mutableStateOf("") }
    var newPassword      by remember { mutableStateOf("") }
    var confirmPassword  by remember { mutableStateOf("") }
    var showCurrent      by remember { mutableStateOf(false) }
    var showNew          by remember { mutableStateOf(false) }
    var showConfirm      by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = MintBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Back + Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.popBackStack() },
                    tint = TextPrimary
                )
                Spacer(Modifier.width(4.dp))
                Text("Change Password", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            Spacer(Modifier.height(24.dp))

            PasswordField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = "Current Password",
                showPassword = showCurrent,
                onToggleVisibility = { showCurrent = !showCurrent }
            )
            Spacer(Modifier.height(12.dp))

            PasswordField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "New Password",
                showPassword = showNew,
                onToggleVisibility = { showNew = !showNew }
            )
            Spacer(Modifier.height(12.dp))

            PasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
                showPassword = showConfirm,
                onToggleVisibility = { showConfirm = !showConfirm }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Password should contain letters numbers\nand special characters",
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldButton)
            ) {
                Text(
                    "CHANGE PASSWORD",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = White,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    showPassword: Boolean,
    onToggleVisibility: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = TextSecondary, fontSize = 14.sp) },
        singleLine = true,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            Icon(
                imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = "Toggle password",
                modifier = Modifier.clickable { onToggleVisibility() },
                tint = TextSecondary
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor   = GoldButton,
            unfocusedBorderColor = CardBorder,
            focusedContainerColor   = White,
            unfocusedContainerColor = White
        )
    )
}

// ─────────────────────────────────────────────
// Screen 3 – Bank Account & Cards
// ─────────────────────────────────────────────
data class BankCard(val holderName: String, val maskedNumber: String)

@Composable
fun BankCardsScreen(navController: NavController) {
    var cards by remember {
        mutableStateOf(listOf(BankCard("John John B. Doe", "1234 **** **** 5687")))
    }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = MintBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Back + Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.popBackStack() },
                    tint = TextPrimary
                )
                Spacer(Modifier.width(4.dp))
                Text("Bank Account & Cards", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            Spacer(Modifier.height(20.dp))

            // Card List
            cards.forEach { card ->
                CardItem(card)
                Spacer(Modifier.height(12.dp))
            }

            // Add New Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAddDialog = true },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = TextPrimary, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Add New Card", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                }
            }
        }
    }

    // Simple Add Card Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Card") },
            text = { Text("Card addition flow goes here.") },
            confirmButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("OK") }
            }
        )
    }
}

@Composable
fun CardItem(card: BankCard) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Card icon
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(TextPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = "Card",
                    tint = White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(card.holderName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                Text(card.maskedNumber, fontSize = 12.sp, color = TextSecondary)
            }
        }
    }
}
