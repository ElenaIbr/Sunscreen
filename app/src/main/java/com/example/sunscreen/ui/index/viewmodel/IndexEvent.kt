package com.example.sunscreen.ui.index.viewmodel

interface IndexEvent
class FetchForecast: IndexEvent
class FetchUvValue: IndexEvent
class SetCoordinates(val latitude: Double, val longitude: Double): IndexEvent
class UpdateLocation: IndexEvent
