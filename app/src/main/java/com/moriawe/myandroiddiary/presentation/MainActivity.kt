package com.moriawe.myandroiddiary.presentation

import MainViewModel
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.moriawe.myandroiddiary.domain.biometric.BiometricPromptManager
import com.moriawe.myandroiddiary.domain.biometric.BiometricPromptManager.BiometricPromptResult
import com.moriawe.myandroiddiary.navigation.NavigationRoot
import com.moriawe.myandroiddiary.presentation.designsystem.theme.MyAndroidDiaryTheme

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            MyAndroidDiaryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { /* TopBar(navController) */ }
                    ) { innerPadding ->

                        val biometricResult by promptManager.promptResults.collectAsState(initial = null)
                        val enrollLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartActivityForResult(),
                            onResult = {
                                println("Activity Result: $it")
                            }
                        )
                        LaunchedEffect(biometricResult) {
                            if(biometricResult is BiometricPromptResult.AuthenticationNotSet) {
                                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                    putExtra(
                                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                    )
                                }
                                enrollLauncher.launch(enrollIntent)
                            }
                        }

                        NavigationRoot(navController, innerPadding, mainViewModel, onAuthentication = {
                            promptManager.showBiometricPrompt(
                                title = "Biometric Authentication",
                                description = "Please authenticate to continue"
                            )
                        })

                        biometricResult?.let { result ->
                            mainViewModel.handleBiometricResult(result)
                        }

//                        biometricResult?.let { result ->
//                            val message: String
//                            when(result) {
//                                is BiometricPromptResult.AuthenticationSucceeded -> {
//                                    message = "Authentication Succeeded"
//                                    mainViewModel.setAuthenticated(true)
//                                }
//                                is BiometricPromptResult.AuthenticationFailed -> message = "Authentication Failed"
//                                is BiometricPromptResult.AuthenticationError -> message = "Authentication Error: ${result.error}"
//                                is BiometricPromptResult.HardwareUnavailable -> message = "Hardware Unavailable"
//                                is BiometricPromptResult.FeatureUnavailable -> message = "Feature Unavailable"
//                                is BiometricPromptResult.AuthenticationNotSet -> message = "Authentication Not Set"
//                            }
//                            Text(text = message)
//                        }
                    }
                }
            }
        }
    }
}

