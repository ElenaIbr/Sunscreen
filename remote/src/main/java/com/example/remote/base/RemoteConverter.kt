package com.example.remote.base

interface RemoteConverter<in RemoteModel, out DomainModel> {
    fun convertFromRemote(remoteModel: RemoteModel): DomainModel
}
