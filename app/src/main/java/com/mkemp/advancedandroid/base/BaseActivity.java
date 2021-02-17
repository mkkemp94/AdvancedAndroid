package com.mkemp.advancedandroid.base;

import android.app.Activity;
import android.os.Bundle;

import com.mkemp.advancedandroid.di.Injector;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity
{
    private static final  String INSTANCE_ID_KEY = "instance_id";
   
    // Uniques key for each activity even across configuration changes
    private String instanceId;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        }
        else
        {
            instanceId = UUID.randomUUID().toString();
        }
        
        Injector.inject(this);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_ID_KEY, instanceId);
    }
    
    public String getInstanceId()
    {
        return instanceId;
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (isFinishing())
        {
            Injector.clearComponent(this);
        }
    }
}
