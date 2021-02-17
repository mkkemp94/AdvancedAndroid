package com.mkemp.advancedandroid.home;

import com.bluelinelabs.conductor.Controller;
import com.mkemp.advancedandroid.di.ControllerKey;
import com.mkemp.advancedandroid.trending.TrendingReposComponent;
import com.mkemp.advancedandroid.trending.TrendingReposController;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        TrendingReposComponent.class
})
public abstract class MainScreenBindingModule
{
    @Binds
    @IntoMap
    @ControllerKey(TrendingReposController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindTrendingReposInjector(TrendingReposComponent.Builder builder);
}
