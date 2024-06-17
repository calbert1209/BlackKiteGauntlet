package com.calbert.blackkitegauntlet.presentation.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.calbert.blackkitegauntlet.presentation.BlackKiteApplication
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { MainViewModel(blackKiteApplication().container) }
    }
}

fun CreationExtras.blackKiteApplication(): BlackKiteApplication = (this[AndroidViewModelFactory.APPLICATION_KEY] as BlackKiteApplication)