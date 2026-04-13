package com.ucb.app.github.data.datasource

import com.ucb.app.github.data.dto.UserDto
import com.ucb.app.github.data.service.GitHubApiService

interface GithubRemoteDataSource {
    suspend fun getUser(nickname: String): UserDto
}