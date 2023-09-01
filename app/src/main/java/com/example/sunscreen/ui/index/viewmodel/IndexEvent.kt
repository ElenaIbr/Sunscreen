package com.example.sunscreen.ui.index.viewmodel

interface IndexEvent
class ObserveInternetConnectivity: IndexEvent
class SetCoordinates(val latitude: Double, val longitude: Double): IndexEvent
class UpdateLocation: IndexEvent
class SetSolarActivity(val indexValue: Double): IndexEvent
