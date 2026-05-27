package com.collectorscove.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// ─────────────────────────────────────────────
//  THEME / DESIGN TOKENS
// ─────────────────────────────────────────────

private val BackgroundColor  = Color(0xFFF0F9FA)   // light teal-ish
private val SurfaceColor     = Color(0xFFFFFFFF)
private val AccentGold       = Color(0xFFD4A843)   // Collectors Cove gold
private val TextPrimary      = Color(0xFF111827)
private val TextSecondary    = Color(0xFF6B7280)
private val PriceColor       = Color(0xFF111827)
private val BottomNavBg      = Color(0xFFFFFFFF)
private val HighlightBg      = Color(0xFFE8F7F8)

// ─────────────────────────────────────────────
//  DATA MODELS
// ─────────────────────────────────────────────

data class CollectorItem(
    val id: Int,
    val name: String,
    val price: String,
    val imageUrl: String,
    val badge: String? = null
)

data class HeroItem(
    val name: String,
    val subtitle: String,
    val imageUrl: String,
    val edition: String
)

// ─────────────────────────────────────────────
//  SAMPLE DATA  (replace with real API/ViewModel)
// ─────────────────────────────────────────────

private val heroItems = listOf(
    HeroItem(
        name = "MSCHF x INRI x Nike Air Max 97",
        subtitle = "Holy Water Edition",
        imageUrl = "https://images.stockx.com/images/Nike-Air-Max-97-MSCHF-x-INRI-Jesus-Shoes.jpg",
        edition = "MT · 54/55"
    )
)

private val shoeItems = listOf(
    CollectorItem(1, "Nike SB Dunk Low Heineken", "₱335,800.00",
        "https://images.stockx.com/images/Nike-Dunk-Low-Pro-SB-Heineken.jpg"),
    CollectorItem(2, "7-Eleven x Nike SB Dunk", "₱310,169.34",
        "https://images.stockx.com/images/Nike-SB-Dunk-Low-7-Eleven.jpg"),
    CollectorItem(3, "Nike Dunk High Coraline", "₱333,900.65",
        "https://images.stockx.com/images/Nike-Dunk-High-Coraline.jpg"),
    CollectorItem(4, "Travis Scott x Jordan 1", "₱420,000.00",
        "https://images.stockx.com/images/Air-Jordan-1-Retro-High-OG-SP-Travis-Scott.jpg"),
)

private val cardItems = listOf(
    CollectorItem(1, "Charizard-Holo 2016...", "₱143,671.00",
        "https://images.pokemontcg.io/xy1/11_hires.png", badge = "PSA"),
    CollectorItem(2, "Pokemon Fossil Rare...", "₱10,891.18",
        "https://images.pokemontcg.io/base2/4_hires.png"),
    CollectorItem(3, "Solgaleo-GX 155/149...", "₱3,954.86",
        "https://images.pokemontcg.io/sm3/173_hires.png"),
    CollectorItem(4, "Pikachu Illustrator", "₱5,200,000.00",
        "https://images.pokemontcg.io/bwp/BW31_hires.png", badge = "PSA"),
)

// ─────────────────────────────────────────────
//  MAIN SCREEN
// ─────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorsCoveHomeScreen(
    onItemClick: (CollectorItem) -> Unit = {},
    onNavClick: (String) -> Unit = {}
) {
    var selectedNav by remember { mutableStateOf("home") }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = { CollectorsCoveTopBar() },
        bottomBar = {
            CollectorsCoveBottomNav(
                selected = selectedNav,
                onItemClick = {
                    selectedNav = it
                    onNavClick(it)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // ── Collector Highlight ──────────────────────
            SectionHeader(title = "Collector Highlight")
            Spacer(modifier = Modifier.height(8.dp))
            HeroCarousel(items = heroItems)

            Spacer(modifier = Modifier.height(20.dp))

            // ── Shoes ────────────────────────────────────
            SectionHeader(
                title = "Shoes",
                onSeeAll = { onNavClick("shoes") }
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalItemRow(items = shoeItems, onItemClick = onItemClick)

            Spacer(modifier = Modifier.height(20.dp))

            // ── Cards ────────────────────────────────────
            SectionHeader(
                title = "Cards",
                onSeeAll = { onNavClick("cards") }
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalItemRow(items = cardItems, onItemClick = onItemClick)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────
//  TOP BAR
// ─────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollectorsCoveTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor
        ),
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = TextPrimary
                )
            }
        },
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Logo circle
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(AccentGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("CC", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "Collectors",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            lineHeight = 16.sp
                        )
                        Text(
                            text = "Cove",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            lineHeight = 16.sp
                        )
                        Text(
                            text = "est. 2024",
                            fontSize = 8.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = TextPrimary)
            }
            IconButton(onClick = {}) {
                BadgedBox(badge = {
                    Badge(containerColor = AccentGold) { Text("3") }
                }) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = TextPrimary)
                }
            }
        }
    )
}

// ─────────────────────────────────────────────
//  SECTION HEADER
// ─────────────────────────────────────────────

@Composable
private fun SectionHeader(
    title: String,
    onSeeAll: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        if (onSeeAll != null) {
            Text(
                text = "See all →",
                fontSize = 13.sp,
                color = AccentGold,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onSeeAll() }
            )
        }
    }
}

// ─────────────────────────────────────────────
//  HERO CAROUSEL
// ─────────────────────────────────────────────

@Composable
private fun HeroCarousel(items: List<HeroItem>) {
    Column {
        items.forEach { item ->
            HeroCard(item = item)
        }
        // Page indicator dots
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 8.dp)
                        .size(if (index == 0) 20.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (index == 0) AccentGold else Color(0xFFD1D5DB))
                )
            }
        }
    }
}

@Composable
private fun HeroCard(item: HeroItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(210.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Gradient background tint
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFFE8F7FA), Color(0xFFFFFFFF)),
                            radius = 600f
                        )
                    )
            )
            // Edition badge top-right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(Color(0xFF111827).copy(alpha = 0.75f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(item.edition, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
            // Shoe image
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )
        }
    }
    // Title below card
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = item.name,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

// ─────────────────────────────────────────────
//  HORIZONTAL PRODUCT ROW
// ─────────────────────────────────────────────

@Composable
private fun HorizontalItemRow(
    items: List<CollectorItem>,
    onItemClick: (CollectorItem) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            ProductCard(item = item, onClick = { onItemClick(item) })
        }
    }
}

@Composable
private fun ProductCard(item: CollectorItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() }
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFF8F9FA))
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
                // PSA badge
                if (item.badge != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                            .background(Color(0xFFDC2626), RoundedCornerShape(4.dp))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = item.badge,
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.name,
                    fontSize = 11.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.price,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PriceColor
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
//  BOTTOM NAVIGATION
// ─────────────────────────────────────────────

private data class NavItem(val id: String, val label: String, val icon: ImageVector, val selectedIcon: ImageVector)

private val navItems = listOf(
    NavItem("home",    "Home",    Icons.Outlined.Home,           Icons.Filled.Home),
    NavItem("explore", "Explore", Icons.Outlined.Explore,        Icons.Filled.Explore),
    NavItem("orders",  "Orders",  Icons.Outlined.ShoppingCart,   Icons.Filled.ShoppingCart),
    NavItem("chat",    "Chat",    Icons.Outlined.ChatBubble,     Icons.Filled.ChatBubble),
    NavItem("account", "Account", Icons.Outlined.AccountCircle,  Icons.Filled.AccountCircle),
)

@Composable
private fun CollectorsCoveBottomNav(
    selected: String,
    onItemClick: (String) -> Unit
) {
    NavigationBar(
        containerColor = BottomNavBg,
        tonalElevation = 8.dp,
        modifier = Modifier.shadow(8.dp)
    ) {
        navItems.forEach { item ->
            val isSelected = selected == item.id
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.id) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AccentGold,
                    selectedTextColor = AccentGold,
                    indicatorColor = AccentGold.copy(alpha = 0.12f),
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}

// ─────────────────────────────────────────────
//  PREVIEW
// ─────────────────────────────────────────────

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun CollectorsCovePreview() {
    MaterialTheme {
        CollectorsCoveHomeScreen()
    }
}
