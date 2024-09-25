package com.moriawe.myandroiddiary.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moriawe.myandroiddiary.R
import com.moriawe.myandroiddiary.presentation.designsystem.Header
import com.moriawe.myandroiddiary.presentation.designsystem.theme.MyAndroidDiaryTheme

// Create root screen for the login screen so loginscreen can be previewed

@Composable
fun LoginScreenRoot(
    modifier : Modifier = Modifier,
    onLogin: () -> Unit,
    viewModel: LoginViewModel
) {
    LoginScreen(
        onAction = { action ->
            when (action) {
                is LoginAction.Login -> onLogin()
            }
        },
    )
}

@Composable
fun LoginScreen(
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(R.string.login_header)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onAction(LoginAction.Login) }
        ) {
            Text(text = "Login")
        }
    }

}

@Preview
@Composable
private fun LoginScreenPreview() {
    MyAndroidDiaryTheme() {
        LoginScreen(
            onAction = {}
        )
    }

}