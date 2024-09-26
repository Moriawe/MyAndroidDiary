import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.myandroiddiary.domain.biometric.BiometricPromptManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    private val _biometricResult = MutableLiveData<BiometricPromptManager.BiometricPromptResult?>()
    val biometricResult: LiveData<BiometricPromptManager.BiometricPromptResult?> = _biometricResult

    private var timeoutJob: Job? = null

    fun setAuthenticated(authenticated: Boolean) {
        _isAuthenticated.value = authenticated
        if (authenticated) {
            startTimeout()
        } else {
            timeoutJob?.cancel()
        }
    }

    private fun startTimeout() {
        timeoutJob?.cancel()
        timeoutJob = viewModelScope.launch {
            delay(5 * 60 * 1000) // 5 minutes
            _isAuthenticated.value = false
        }
    }

    fun handleBiometricResult(result: BiometricPromptManager.BiometricPromptResult) {
        when (result) {
            is BiometricPromptManager.BiometricPromptResult.AuthenticationSucceeded -> {
                setAuthenticated(true)
            }
            else -> {
                // Handle other cases
            }
        }
    }
}