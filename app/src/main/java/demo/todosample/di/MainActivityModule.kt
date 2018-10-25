package demo.bookssample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import demo.todosample.ui.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}