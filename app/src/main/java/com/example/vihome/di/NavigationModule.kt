package com.example.vihome.di

import com.example.core.navigationComponent.BaseNavigator
import com.example.features.FeatureNavigation
import com.example.vihome.navigation.AppNavigation
import com.example.vihome.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun provideBaseNavigation(navigation: AppNavigatorImpl): BaseNavigator

    @Binds
    abstract fun provideAppNavigation(navigation: AppNavigatorImpl): AppNavigation

    @Binds
    abstract fun provideFeatureNavigation(navigation: AppNavigatorImpl): FeatureNavigation
}