package ru.geekbrain.android.tests.di

import ru.geekbrain.android.tests.repository.RepositoryContract
import ru.geekbrain.android.tests.repository.real.GitHubRepository
import org.koin.dsl.module

val application = module {

    single<RepositoryContract> { GitHubRepository(get()) }
}