package epm.xnox.notes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import epm.xnox.notes.data.repository.DatabaseRepositoryImpl
import epm.xnox.notes.data.sources.database.dao.NoteDao
import epm.xnox.notes.domain.repository.DatabaseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: NoteDao): DatabaseRepository =
        DatabaseRepositoryImpl(dao)
}