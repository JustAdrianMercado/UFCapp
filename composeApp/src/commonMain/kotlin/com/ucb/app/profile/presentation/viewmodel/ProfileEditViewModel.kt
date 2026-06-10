package com.ucb.app.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.profile.domain.model.ProfileModel
import com.ucb.app.profile.domain.usecase.EditProfileUseCase
import com.ucb.app.profile.domain.usecase.GetProfileUseCase
import com.ucb.app.profile.presentation.state.ProfileEditEffect
import com.ucb.app.profile.presentation.state.ProfileEditEvent
import com.ucb.app.profile.presentation.state.ProfileEditUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val editProfileUseCase: EditProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEditEffect>()
    val effect: SharedFlow<ProfileEditEffect> = _effect.asSharedFlow()

    init {
        onEvent(ProfileEditEvent.LoadProfile)
    }

    fun onEvent(event: ProfileEditEvent) {
        when (event) {
            ProfileEditEvent.LoadProfile -> loadProfile()
            ProfileEditEvent.OnBackClick -> emitEffect(ProfileEditEffect.NavigateBack)
            ProfileEditEvent.OnSaveClick -> saveProfile()
            ProfileEditEvent.OnChangePasswordClick -> emitEffect(ProfileEditEffect.NavigateToChangePassword)
            is ProfileEditEvent.OnFirstNameChanged -> updateState { copy(firstName = event.value) }
            is ProfileEditEvent.OnLastNameChanged -> updateState { copy(lastName = event.value) }
            is ProfileEditEvent.OnUsernameChanged -> updateState { copy(username = event.value) }
            is ProfileEditEvent.OnEmailChanged -> updateState { copy(email = event.value) }
            is ProfileEditEvent.OnPhoneNumberChanged -> updateState { copy(phoneNumber = event.value) }
            is ProfileEditEvent.OnBirthdayChanged -> updateState { copy(birthday = event.value) }
            is ProfileEditEvent.OnGenderChanged -> updateState { copy(gender = event.value) }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val profile = getProfileUseCase("1")
                _uiState.value = ProfileEditUiState(
                    isLoading = false,
                    profileId = profile.id,
                    firstName = profile.firstName.ifBlank { profile.name.trim().split(" ", limit = 2).getOrNull(0).orEmpty() },
                    lastName = profile.lastName.ifBlank { profile.name.trim().split(" ", limit = 2).getOrNull(1).orEmpty() },
                    username = profile.username.ifBlank { profile.description },
                    email = profile.email,
                    phoneNumber = profile.cellphone,
                    birthday = profile.birthday,
                    gender = profile.gender,
                    avatarUrl = profile.pathUrl
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Could not load profile"
                )
            }
        }
    }

    private fun saveProfile() {
        val currentState = _uiState.value
        if (!currentState.canSave) {
            emitEffect(ProfileEditEffect.ShowMessage("First name, last name and email are required"))
            return
        }
        if (!currentState.email.isValidEmail()) {
            emitEffect(ProfileEditEffect.ShowMessage("Enter a valid email address"))
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isSaving = true, error = null)

            try {
                editProfileUseCase.invoke(
                    ProfileModel(
                        id = currentState.profileId,
                        firstName = currentState.firstName.trim(),
                        lastName = currentState.lastName.trim(),
                        username = currentState.username.trim(),
                        name = "${currentState.firstName.trim()} ${currentState.lastName.trim()}".trim(),
                        email = currentState.email.trim(),
                        cellphone = currentState.phoneNumber.trim(),
                        description = currentState.username.trim(),
                        pathUrl = currentState.avatarUrl,
                        birthday = currentState.birthday.trim(),
                        gender = currentState.gender.trim()
                    )
                )
                _uiState.value = _uiState.value.copy(isSaving = false)
                _effect.emit(ProfileEditEffect.ShowMessage("Profile updated"))
                _effect.emit(ProfileEditEffect.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Could not update profile"
                )
            }
        }
    }

    private fun updateState(reducer: ProfileEditUiState.() -> ProfileEditUiState) {
        _uiState.value = _uiState.value.reducer()
    }

    private fun emitEffect(effect: ProfileEditEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun String.isValidEmail(): Boolean {
        return trim().matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }
}
