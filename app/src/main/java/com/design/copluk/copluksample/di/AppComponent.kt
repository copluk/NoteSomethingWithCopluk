package com.design.copluk.copluksample.di

import com.design.copluk.copluksample.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    BuildersModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)
}