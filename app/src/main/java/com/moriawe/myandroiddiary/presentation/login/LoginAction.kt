package com.moriawe.myandroiddiary.presentation.login

// The actions the user can do on the login screen

sealed interface LoginAction {
    data object Login : LoginAction
}