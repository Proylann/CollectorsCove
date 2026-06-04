package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.ui.auth.AuthViewModel

@Composable
fun SignUpProfileScreen(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    onFinish: () -> Unit
) {
    val viewModel = remember { AuthViewModel() }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 28.dp, vertical = 28.dp)
    ) {
        Text(
            text = "‹",
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable { },
            fontSize = 42.sp,
            color = Color.Black
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(105.dp))

            Text(
                text = "Sign up",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Create an account",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(65.dp))

            Text(
                text = "Profile Information",
                modifier = Modifier.align(Alignment.Start),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(10.dp))

            SignUpProfileInput(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = "Phone Number"
            )

            Spacer(modifier = Modifier.height(10.dp))

            SignUpProfileInput(
                value = address,
                onValueChange = { address = it },
                placeholder = "Address"
            )

            Spacer(modifier = Modifier.height(10.dp))

            DropdownInput(
                value = nationality,
                placeholder = "Nationality",
                options = listOf("Filipino", "American", "Japanese", "Korean", "Chinese"),
                onSelect = { nationality = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            DropdownInput(
                value = gender,
                placeholder = "Gender",
                options = listOf("Male", "Female", "Prefer not to say"),
                onSelect = { gender = it }
            )

            Spacer(modifier = Modifier.height(44.dp))

            Button(
                onClick = {

                    viewModel.register(
                        email = email,
                        password = password,
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                        address = address,
                        nationality = nationality,
                        gender = gender
                    ) { success, error ->

                        if (success) {
                            onFinish()
                        }
                    }

                }
            ) {
                Text(
                    text = "Next",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SignUpProfileInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 14.sp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun DropdownInput(
    value: String,
    placeholder: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            trailingIcon = {
                Text(
                    text = "⌄",
                    modifier = Modifier.clickable { expanded = true },
                    fontSize = 22.sp,
                    color = Color.Black
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable { expanded = true },
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}