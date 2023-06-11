package com.example.sunscreen.ui.profile.viewmodel

import com.example.domain.models.UserModel

data class ProfileState (
    val user: UserModel? = null,
    val updatedUserName: String? = null,
    val updatedBirthDate: String? = null,
    val updatedSkinType: UserModel.SkinType? = null,
    val updatedSkinColor: UserModel.SkinColor? = null,
    val userDataWasChanged: Boolean = false
)
