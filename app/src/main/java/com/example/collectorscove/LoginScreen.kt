package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveSurface
import com.example.collectorscove.ui.auth.AuthViewModel

@Composable
fun LoginScreen(
    onSignIn: () -> Unit,
    onGoToSignup: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val viewModel = remember { AuthViewModel() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CoveBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(105.dp))

            LogoText()

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Sign in",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Put your credentials",
                fontSize = 15.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(34.dp))

            LoginInput(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email"
            )

            Spacer(modifier = Modifier.height(10.dp))

            LoginInput(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true
            )

            Text(
                text = "Forgot password?",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp, top = 4.dp)
                    .clickable { },
                fontSize = 13.sp,
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.login(email, password) { success, _ ->
                        if (success) {
                            onSignIn()
                        }
                    }
                },
                modifier = Modifier
                    .width(108.dp)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoveGold,
                    contentColor = CoveSurface
                )
            ) {
                Text("Sign in", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.padding(bottom = 180.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don’t have an account? ",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = "Sign up",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        onGoToSignup()
                    }
                )
            }
        }

        Text(
            text = "Sign in",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 2.dp, top = 4.dp),
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}

/* =======================
   ADDED MISSING FUNCTIONS
   (NO DESIGN CHANGES)
   ======================= */

@Composable
private fun LoginInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
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
            .height(52.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 14.sp),
        visualTransformation =
            if (isPassword) PasswordVisualTransformation()
            else androidx.compose.ui.text.input.VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CoveSurface,
            unfocusedContainerColor = CoveSurface,
            focusedIndicatorColor = CoveGold,
            unfocusedIndicatorColor = CoveBorder,
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun LogoText() {
    Box(
        modifier = Modifier.width(190.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterStart)
                .background(
                    CoveGold,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )

        Column(
            modifier = Modifier.padding(start = 44.dp)
        ) {
            Text(
                text = "Collectors",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 24.sp
            )
            Text(
                text = "Cove",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 24.sp
            )
            Text(
                text = "est. 2024",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 8.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black
            )
        }
    }
}