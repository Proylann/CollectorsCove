package com.example.collectorscove

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface
import com.example.collectorscove.ui.theme.CoveTextSecondary

private const val MAX_PHOTOS = 5

@Composable
fun SellItemScreen(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onListingPosted: () -> Unit = {}
) {
    var itemName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedCondition by remember { mutableStateOf("") }
    var listingType by remember { mutableStateOf("Fixed Price") }
    val photoUris = remember {
        mutableStateListOf<Uri?>().apply { repeat(MAX_PHOTOS) { add(null) } }
    }
    var pickingSlotIndex by remember { mutableIntStateOf(-1) }
    var showPostedMessage by remember { mutableStateOf(false) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null && pickingSlotIndex in 0 until MAX_PHOTOS) {
            photoUris[pickingSlotIndex] = uri
        }
        pickingSlotIndex = -1
    }

    fun openPhotoPicker(slotIndex: Int) {
        pickingSlotIndex = slotIndex
        pickImageLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    fun removePhoto(slotIndex: Int) {
        if (slotIndex in 0 until MAX_PHOTOS) {
            photoUris[slotIndex] = null
        }
    }

    val photoCount = photoUris.count { it != null }
    val coverUri = photoUris.firstOrNull { it != null }
    val isFormValid = photoCount > 0 &&
        itemName.isNotBlank() &&
        selectedCategory.isNotBlank() &&
        price.isNotBlank() &&
        selectedCondition.isNotBlank()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CoveBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationsClick = onNotificationsClick
            )
        }
        item {
            Column {
                Text(
                    text = "Sell Item",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "List your collectible for sale",
                    fontSize = 13.sp,
                    color = CoveTextSecondary
                )
            }
        }

        item {
            FormSectionLabel(title = "Photos", helper = "Add up to $MAX_PHOTOS photos")
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryPhotoUpload(
                imageUri = photoUris[0],
                onClick = { openPhotoPicker(0) },
                onRemove = { removePhoto(0) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(photoUris.toList()) { index, uri ->
                    ThumbnailSlot(
                        imageUri = uri,
                        isCover = index == 0 && uri != null,
                        onClick = { openPhotoPicker(index) },
                        onRemove = if (uri != null) ({ removePhoto(index) }) else null
                    )
                }
            }
        }

        item {
            FormSectionLabel(title = "Item Details")
            Spacer(modifier = Modifier.height(8.dp))
            SellTextField(
                value = itemName,
                onValueChange = { itemName = it },
                placeholder = "e.g. Nike SB Dunk Low Heineken"
            )
            Spacer(modifier = Modifier.height(12.dp))
            SellTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = "Describe condition, authenticity, and details…",
                singleLine = false,
                minLines = 3
            )
        }

        item {
            FormSectionLabel(title = "Category")
            Spacer(modifier = Modifier.height(8.dp))
            SelectableChips(
                options = listOf("Shoes", "Cards", "Toys", "Antiques"),
                selected = selectedCategory,
                onSelected = { selectedCategory = it }
            )
        }

        item {
            FormSectionLabel(title = "Price")
            Spacer(modifier = Modifier.height(8.dp))
            SellTextField(
                value = price,
                onValueChange = { price = it.filter { c -> c.isDigit() || c == '.' || c == ',' } },
                placeholder = "0.00",
                prefix = "P",
                keyboardType = KeyboardType.Decimal
            )
        }

        item {
            FormSectionLabel(title = "Condition")
            Spacer(modifier = Modifier.height(8.dp))
            SelectableChips(
                options = listOf("New", "Like New", "Good", "Fair"),
                selected = selectedCondition,
                onSelected = { selectedCondition = it }
            )
        }

        item {
            FormSectionLabel(title = "Listing Type")
            Spacer(modifier = Modifier.height(4.dp))
            ListingTypeSelector(
                selected = listingType,
                onSelected = { listingType = it }
            )
        }

        item {
            SectionHeader(title = "Preview")
            Spacer(modifier = Modifier.height(8.dp))
            ListingPreviewCard(
                itemName = itemName.ifBlank { "Your item name" },
                price = price.ifBlank { "0.00" }.let { "P$it" },
                imageUri = coverUri
            )
        }

        item {
            Button(
                onClick = {
                    showPostedMessage = true
                    onListingPosted()
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoveGold,
                    contentColor = Color.White,
                    disabledContainerColor = CoveLightGray,
                    disabledContentColor = Color.Gray
                )
            ) {
                Text(
                    text = if (showPostedMessage) "Listing Posted!" else "Post Listing",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            TextButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save as Draft",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun ListingPhoto(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    contentDescription: String
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier.clip(shape),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun FormSectionLabel(title: String, helper: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        if (helper != null) {
            Text(
                text = helper,
                fontSize = 12.sp,
                color = CoveTextSecondary
            )
        }
    }
}

@Composable
private fun PrimaryPhotoUpload(
    imageUri: Uri?,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(shape)
            .background(if (imageUri != null) Color.Transparent else CoveLightGray)
            .border(1.dp, CoveBorder, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            ListingPhoto(
                imageUri = imageUri,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Cover photo"
            )
            CoverBadge(modifier = Modifier.align(Alignment.TopStart))
            RemovePhotoLabel(
                modifier = Modifier.align(Alignment.BottomEnd),
                onRemove = onRemove
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("Add Photo", fontSize = 13.sp, color = Color.DarkGray)
                Text("Tap to choose from gallery", fontSize = 11.sp, color = CoveTextSecondary)
            }
        }
    }
}

@Composable
private fun ThumbnailSlot(
    imageUri: Uri?,
    isCover: Boolean,
    onClick: () -> Unit,
    onRemove: (() -> Unit)?
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(shape)
            .background(if (imageUri != null) Color.Transparent else CoveSurface)
            .border(1.dp, CoveBorder, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            ListingPhoto(
                imageUri = imageUri,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Listing photo"
            )
            if (isCover) {
                CoverBadge(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 4.dp),
                    compact = true
                )
            }
            if (onRemove != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(18.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(onClick = onRemove),
                    contentAlignment = Alignment.Center
                ) {
                    Text("×", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Text("+", fontSize = 20.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun CoverBadge(modifier: Modifier = Modifier, compact: Boolean = false) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(CoveGold, RoundedCornerShape(if (compact) 4.dp else 8.dp))
            .padding(
                horizontal = if (compact) 4.dp else 8.dp,
                vertical = if (compact) 2.dp else 4.dp
            )
    ) {
        Text(
            text = "Cover",
            fontSize = if (compact) 7.sp else 10.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RemovePhotoLabel(modifier: Modifier = Modifier, onRemove: () -> Unit) {
    Text(
        text = "Remove",
        modifier = modifier
            .padding(12.dp)
            .clickable(onClick = onRemove),
        fontSize = 12.sp,
        color = Color.White,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun SellTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    prefix: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder, fontSize = 13.sp, color = Color.Gray)
        },
        prefix = prefix?.let {
            {
                Text(text = it, fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = minLines,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CoveGold,
            unfocusedBorderColor = CoveBorder,
            focusedContainerColor = CoveSurface,
            unfocusedContainerColor = CoveSurface,
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun SelectableChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(options.size) { index ->
            val option = options[index]
            val isSelected = option == selected

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .background(
                        color = if (isSelected) CoveGold else CoveLightGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) CoveGold else CoveBorder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelected(option) }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    fontSize = 12.sp,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
private fun ListingTypeSelector(
    selected: String,
    onSelected: (String) -> Unit
) {
    Column {
        listOf("Fixed Price", "Auction").forEach { type ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelected(type) }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected == type,
                    onClick = { onSelected(type) },
                    colors = RadioButtonDefaults.colors(selectedColor = CoveGold)
                )
                Text(text = type, fontSize = 13.sp, color = Color.Black)
            }
        }
    }
}

@Composable
private fun ListingPreviewCard(
    itemName: String,
    price: String,
    imageUri: Uri?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CoveBorder),
        colors = CardDefaults.cardColors(containerColor = CoveSurface)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .background(CoveLightGray),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    ListingPhoto(
                        imageUri = imageUri,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Listing preview"
                    )
                } else {
                    Text(
                        text = "No photo",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = itemName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = price,
                    fontSize = 12.sp,
                    color = CoveGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
