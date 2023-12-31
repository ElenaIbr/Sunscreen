package com.example.sunscreen.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Notification
import com.example.domain.models.UserModel
import com.example.domain.usecases.GetUserEntity
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    init {
        getUser()
    }

    fun sendEvent(profileEvent: ProfileEvent) {
        when (profileEvent) {
            is UpdateUserName -> {
                updateUserName(profileEvent.name)
            }
            is UpdateUserBirthDate -> {
                updateUserBirthDate(profileEvent.birthDate)
            }
            is UpdateUserSkinType -> {
                updateUserSkinType(profileEvent.skinType)
            }
            is UpdateUserSkinColor -> {
                updateUserSkinColor(profileEvent.skinColor)
            }
            is UpdateUser -> {
                updateUser()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.execute(Unit).collect { flow ->
                when (flow) {
                    is GetUserEntity.Loading -> {}
                    is GetUserEntity.Success -> {
                        _profileState.value = _profileState.value.copy(
                            user = flow.userModel,
                            updatedUserName = flow.userModel?.name,
                            updatedBirthDate = flow.userModel?.birthDate,
                            updatedSkinType = flow.userModel?.skinType,
                            updatedSkinColor = flow.userModel?.skinColor,
                            userDataWasChanged = false
                        )
                    }
                }
            }
        }
    }

    private fun updateUserName(name: String) {
        _profileState.value = _profileState.value.copy(
            updatedUserName = name
        )
        userDataWasChanged()
    }

    private fun updateUserBirthDate(birthDate: String) {
        _profileState.value = _profileState.value.copy(
            updatedBirthDate = birthDate
        )
        userDataWasChanged()
    }

    private fun updateUserSkinType(skinType: UserModel.SkinType) {
        _profileState.value = _profileState.value.copy(
            updatedSkinType = skinType
        )
        userDataWasChanged()
    }
    private fun updateUserSkinColor(skinColor: UserModel.SkinColor) {
        _profileState.value = _profileState.value.copy(
            updatedSkinColor = skinColor
        )
        userDataWasChanged()
    }
    private fun updateUser() {
        viewModelScope.launch {
            updateUserUseCase.execute(
                UserModel(
                    id = UUID.randomUUID(),
                    name = _profileState.value.updatedUserName ?: "",
                    birthDate = _profileState.value.updatedBirthDate ?: "",
                    skinType = _profileState.value.updatedSkinType ?: UserModel.SkinType.Unknown,
                    skinColor = _profileState.value.updatedSkinColor ?: UserModel.SkinColor.Unknown,
                    coordinates = null,
                    notifications = null
                )
            )
        }
    }
    private fun userDataWasChanged() {
        _profileState.value = _profileState.value.copy(
            userDataWasChanged = _profileState.value.user?.name != _profileState.value.updatedUserName ||
                    _profileState.value.user?.birthDate != _profileState.value.updatedBirthDate ||
                    _profileState.value.user?.skinType != _profileState.value.updatedSkinType ||
                    _profileState.value.user?.skinColor != _profileState.value.updatedSkinColor
        )
    }
}
