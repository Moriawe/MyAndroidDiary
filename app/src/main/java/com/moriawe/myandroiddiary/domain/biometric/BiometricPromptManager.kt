package com.moriawe.myandroiddiary.domain.biometric

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BiometricPromptManager(
    private val activity: AppCompatActivity
) {
    private val _resultChannel = Channel<BiometricPromptResult>()
    val promptResults = _resultChannel.receiveAsFlow()

    fun showBiometricPrompt(
        title: String,
        description: String
    ) {
        val manager = BiometricManager.from(activity)
        val authenticators = BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)

        when (manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                _resultChannel.trySend(BiometricPromptResult.HardwareUnavailable)
                return
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                _resultChannel.trySend(BiometricPromptResult.FeatureUnavailable)
                return
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                _resultChannel.trySend(BiometricPromptResult.AuthenticationNotSet)
                return
            }
            else -> Unit
        }

        val prompt = BiometricPrompt(
            activity,
            object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    _resultChannel.trySend(BiometricPromptResult.AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    _resultChannel.trySend(BiometricPromptResult.AuthenticationSucceeded)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    _resultChannel.trySend(BiometricPromptResult.AuthenticationFailed)
                }
            }
        )
        prompt.authenticate(promptInfo.build())

    }

    sealed interface BiometricPromptResult {
        data object HardwareUnavailable : BiometricPromptResult
        data object FeatureUnavailable : BiometricPromptResult
        data class AuthenticationError(val error: String) : BiometricPromptResult
        data object AuthenticationFailed : BiometricPromptResult
        data object AuthenticationSucceeded : BiometricPromptResult
        data object AuthenticationNotSet : BiometricPromptResult
    }

}