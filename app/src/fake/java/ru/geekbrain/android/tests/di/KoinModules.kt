package ru.geekbrain.android.tests.di

import org.koin.dsl.module
import ru.geekbrain.android.tests.repository.RepositoryContract
import ru.geekbrain.android.tests.repository.fake.FakeGitHubRepository

val application = module {
    single<RepositoryContract> { FakeGitHubRepository() }

}