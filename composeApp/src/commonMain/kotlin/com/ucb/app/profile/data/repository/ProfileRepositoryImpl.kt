package com.ucb.app.profile.data.repository

import com.ucb.app.profile.domain.model.ProfileModel
import com.ucb.app.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl : ProfileRepository {

    private var fakeProfile = ProfileModel(
        id = "1",
        name = "Sabrina Aryan",
        email = "SabrinaAry208@gmail.com",
        cellphone = "+234 904 6470",
        description = "UFC fan",
        pathUrl = "https://via.placeholder.com/300"
    )

    override suspend fun update(profile: ProfileModel) {
        fakeProfile = profile
    }

    override suspend fun create(profile: ProfileModel) {
        fakeProfile = profile
    }

    override suspend fun findById(id: String): ProfileModel {
        return fakeProfile
    }
}