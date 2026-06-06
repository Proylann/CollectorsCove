package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import com.example.collectorscove.ui.auth.AuthViewModel
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface
import com.example.collectorscove.ui.theme.CoveTextSecondary
import kotlinx.coroutines.launch

@Composable
fun AppMenuDrawer(
    drawerState: DrawerState,
    viewModel: AuthViewModel,
    enabled: Boolean = true,
    onMenuItemClick: (String) -> Unit = {},
    onViewProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val user by viewModel.currentUser

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = enabled,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight(),
                drawerContainerColor = CoveSurface
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 28.dp)
                ) {
                    LogoMark()

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onViewProfileClick),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val initials = if (user != null) {
                            "${user!!.firstName.take(1)}${user!!.lastName.take(1)}"
                        } else "JL"

                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(CoveLightGray, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initials,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                text = if (user != null) "${user!!.firstName} ${user!!.lastName}" else "Loading...",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                "View Profile",
                                fontSize = 12.sp,
                                color = CoveGold,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    listOf("My Collection", "Sell Item").forEach { item ->
                        DrawerMenuItem(
                            label = item,
                            onClick = { onMenuItemClick(item) }
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    HorizontalDivider(color = CoveBorder)
                    Spacer(Modifier.height(8.dp))

                    DrawerMenuItem(
                        label = "Log out",
                        isDestructive = true,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onLogoutClick()
                        }
                    )
                }
            }
        },
        content = content
    )
}

@Composable
private fun DrawerMenuItem(
    label: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = if (isDestructive) Color(0xFFC62828) else Color.Black
    )
}
