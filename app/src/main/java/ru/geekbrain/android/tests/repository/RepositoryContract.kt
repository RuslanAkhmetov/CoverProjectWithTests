package ru.geekbrain.android.tests.repository

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}