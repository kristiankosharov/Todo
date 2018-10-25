/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.bookssample.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import demo.todosample.db.TodoDao
import demo.todosample.db.TodoDb
import javax.inject.Singleton



@Module(includes = [ViewModelModule::class])
class AppModule {
//    @Singleton
//    @Provides
//    fun provideRetrofit(): Retrofit {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//        return Retrofit.Builder()
//                .baseUrl("https://api.itbook.store/1.0/")
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
//                .build()
//    }

//    @Singleton
//    @Provides
//    fun provideBooksService(retrofit: Retrofit): BooksService {
//        return retrofit.create(BooksService::class.java)
//    }

    @Singleton
    @Provides
    fun provideDb(app: Application): TodoDb {
        return Room
                .databaseBuilder(app, TodoDb::class.java, "TodosDb.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideTodosDao(db: TodoDb): TodoDao {
        return db.todoDao()
    }
//
//    @Singleton
//    @Provides
//    fun provideRepoDao(db: GithubDb): RepoDao {
//        return db.repoDao()
//    }
}
