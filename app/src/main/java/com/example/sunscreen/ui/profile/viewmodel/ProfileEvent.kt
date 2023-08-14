package com.example.sunscreen.ui.profile.viewmodel

import com.example.domain.models.UserModel

interface ProfileEvent

class UpdateUserName(val name: String): ProfileEvent

class UpdateUserBirthDate(val birthDate: String): ProfileEvent

class UpdateUserSkinType(val skinType: UserModel.SkinType): ProfileEvent

class UpdateUserSkinColor(val skinColor: UserModel.SkinColor): ProfileEvent

class UpdateUser(): ProfileEvent
