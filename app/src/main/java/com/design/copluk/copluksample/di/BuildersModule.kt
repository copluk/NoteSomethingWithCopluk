package com.design.copluk.copluksample.di

import com.design.copluk.copluksample.controller.GoogleMapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): GoogleMapActivity
}