package com.mkemp.advancedandroid.base;

import android.app.Application;

import com.mkemp.advancedandroid.di.ActivityInjector;

import javax.inject.Inject;

public class MyApplication extends Application
{
    @Inject
    ActivityInjector activityInjector;
    
    private ApplicationComponent applicationComponent;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }
    
    public ActivityInjector getActivityInjector()
    {
        return activityInjector;
    }
}
