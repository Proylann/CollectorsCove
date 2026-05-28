package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val GoldButton = Color(0xFFB8970A)
private val TextPrimary = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF777777)
private val DividerColor = Color(0xFFDDDDDD)
private val CardBorder = Color(0xFFCCCCCC)

private enum class AccountPage {
    Settings,
    ChangePassword,
    BankCards,
    PrivacySettings,
    PaymentHistory
}

@Composable
fun AccountSettingsApp() {
    var page by remember { mutableStateOf(AccountPage.Settings) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    when (page) {
        AccountPage.Settings -> AppMenuDrawer(drawerState = drawerState) {
            AccountSettingsScreen(
                onMenuClick = { scope.launch { drawerState.open() } },
                onChangePassword = { page = AccountPage.ChangePassword },
                onBankCards = { page = AccountPage.BankCards },
                onPrivacySettings = { page = AccountPage.PrivacySettings },
                onPaymentHistory = { page = AccountPage.PaymentHistory }
            )
        }

        AccountPage.ChangePassword -> ChangePasswordScreen(
            onBack = { page = AccountPage.Settings }
        )

        AccountPage.BankCards -> BankCardsScreen(
            onBack = { page = AccountPage.Settings }
        )

        AccountPage.PrivacySettings -> PrivacySettingsScreen(
            onBack = { page = AccountPage.Settings }
        )

        AccountPage.PaymentHistory -> PaymentHistoryScreen(
            onBack = { page = AccountPage.Settings }
        )
    }
}

@Composable
private fun AccountSettingsScreen(
    onMenuClick: () -> Unit,
    onChangePassword: () -> Unit,
    onBankCards: () -> Unit,
    onPrivacySettings: () -> Unit,
    onPaymentHistory: () -> Unit
) {
    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "☰",
                    modifier = Modifier.clickable(onClick = onMenuClick),
                    fontSize = 28.sp,
                    color = TextPrimary
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = "Account Settings",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFFDDDDDD), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 26.sp)
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text("John Lennon", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Text("View Profile", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Basic Information")
            Spacer(Modifier.height(4.dp))

            InfoRow("Name", "John Lennon")
            InfoRow("Gender", "Male")
            InfoRow("Location", "Pasig City")
            InfoRow("Email", "john@gmail.com")

            Spacer(Modifier.height(20.dp))
            SectionLabel("Settings")
            Spacer(Modifier.height(4.dp))

            HorizontalDivider(color = DividerColor)
            SettingsRow("Change Password", onChangePassword)
            SettingsRow("Bank Account / Cards", onBankCards)
            SettingsRow("Privacy Settings", onPrivacySettings)
            SettingsRow("Payment History", onPaymentHistory)

            Spacer(Modifier.height(24.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Text("Logout", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ChangePasswordScreen(onBack: () -> Unit) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "‹",
                    modifier = Modifier.clickable(onClick = onBack),
                    fontSize = 40.sp,
                    color = TextPrimary
                )
                Spacer(Modifier.width(4.dp))
                Text("Change Password", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(24.dp))

            PasswordField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = "Current Password",
                showPassword = showPassword,
                onToggleVisibility = { showPassword = !showPassword }
            )
            Spacer(Modifier.height(12.dp))

            PasswordField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "New Password",
                showPassword = showPassword,
                onToggleVisibility = { showPassword = !showPassword }
            )
            Spacer(Modifier.height(12.dp))

            PasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
                showPassword = showPassword,
                onToggleVisibility = { showPassword = !showPassword }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Password should contain letters, numbers, and special characters",
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
                Text("CHANGE PASSWORD", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun BankCardsScreen(onBack: () -> Unit) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "‹",
                    modifier = Modifier.clickable(onClick = onBack),
                    fontSize = 40.sp,
                    color = TextPrimary
                )
                Spacer(Modifier.width(4.dp))
                Text("Bank Account & Cards", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))
            CardItem("John John B. Doe", "1234 **** **** 5687")
            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAddDialog = true },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("+ Add New Card", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Card") },
            text = { Text("Card addition flow goes here.") },
            confirmButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun PrivacySettingsScreen(onBack: () -> Unit) {
    var messageChoice by remember { mutableStateOf("No one") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var locationEnabled by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(18.dp))

            AccountHeader(title = "Privacy Settings", onBack = onBack)

            Spacer(Modifier.height(36.dp))

            Text(
                text = "Set who can message you",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(18.dp))

            PrivacyRadioRow(
                label = "No one",
                selected = messageChoice == "No one",
                onClick = { messageChoice = "No one" }
            )

            PrivacyRadioRow(
                label = "Everyone",
                selected = messageChoice == "Everyone",
                onClick = { messageChoice = "Everyone" }
            )

            Spacer(Modifier.height(22.dp))

            Text(
                text = "App Permissions",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(12.dp))

            PermissionRow(
                label = "Allow Collector's Cove to send notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            PermissionRow(
                label = "Allow Collector's Cove to access location",
                checked = locationEnabled,
                onCheckedChange = { locationEnabled = it }
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Delete my Account",
                    fontSize = 12.sp,
                    color = Color.Red
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "Delete",
                    fontSize = 11.sp,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
private fun PaymentHistoryScreen(onBack: () -> Unit) {
    var hasHistory by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { AppBottomBar(selectedIndex = 4) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "<",
                    modifier = Modifier.clickable(onClick = onBack),
                    fontSize = 34.sp,
                    color = TextPrimary
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = "Payments",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "Clear History",
                    modifier = Modifier.clickable { hasHistory = false },
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            if (hasHistory) {
                Spacer(Modifier.height(52.dp))

                repeat(3) {
                    PaymentHistoryCard()
                    Spacer(Modifier.height(12.dp))
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Recent History",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountHeader(title: String, onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "<",
            modifier = Modifier.clickable(onClick = onBack),
            fontSize = 34.sp,
            color = TextPrimary
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

@Composable
private fun PrivacyRadioRow(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(label, fontSize = 12.sp, color = TextPrimary)
    }
}

@Composable
private fun PermissionRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 11.sp,
            color = TextPrimary
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.scale(0.65f)
        )
    }
}

@Composable
private fun PaymentHistoryCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(1.dp, DividerColor),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(28.dp)
                    .background(Color(0xFFEFEFEF))
                    .border(1.dp, Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Card", fontSize = 5.sp, color = GoldButton)
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Bid Won!",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "An amount of P5,731.63 has been\ndeducted to your account",
                    fontSize = 10.sp,
                    color = TextPrimary,
                    lineHeight = 11.sp
                )
            }

            Text(
                text = "05/11/2025",
                fontSize = 9.sp,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        Text("$value  ›", fontSize = 14.sp, color = TextSecondary)
    }
    HorizontalDivider(color = DividerColor)
}

@Composable
private fun SettingsRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp, color = TextPrimary)
        Text("›", fontSize = 22.sp, color = TextSecondary)
    }
    HorizontalDivider(color = DividerColor)
}

@Composable
private fun PasswordField(
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
            Text(
                text = if (showPassword) "Hide" else "Show",
                modifier = Modifier.clickable(onClick = onToggleVisibility),
                fontSize = 12.sp,
                color = TextSecondary
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldButton,
            unfocusedBorderColor = CardBorder,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
private fun CardItem(holderName: String, maskedNumber: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(TextPrimary, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Card", fontSize = 9.sp, color = Color.White)
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(holderName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(maskedNumber, fontSize = 12.sp, color = TextSecondary)
            }
        }
    }
}

@Composable
private fun AppBottomBar(selectedIndex: Int = 4) {
    val items = listOf(
        "⌂" to "Home",
        "◉" to "Explore",
        "▾" to "Orders",
        "☏" to "Chat",
        "♡" to "Account"
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        items.forEachIndexed { index, item ->
            AppBottomItem(
                icon = item.first,
                label = item.second,
                selected = index == selectedIndex
            )
        }
    }
}

@Composable
private fun RowScope.AppBottomItem(icon: String, label: String, selected: Boolean) {
    NavigationBarItem(
        selected = selected,
        onClick = {},
        icon = {
            Text(
                text = icon,
                fontSize = 22.sp,
                color = if (selected) TextPrimary else TextSecondary
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 9.sp,
                color = if (selected) TextPrimary else TextSecondary
            )
        },
        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
    )
}
