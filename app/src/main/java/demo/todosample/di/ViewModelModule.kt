package demo.bookssample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import demo.todosample.ui.MainViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindNewBooksViewModel(newReleasedViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TodosViewModelFactory): ViewModelProvider.Factory
}