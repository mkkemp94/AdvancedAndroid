package com.mkemp.advancedandroid.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.mkemp.advancedandroid.R;
import com.mkemp.advancedandroid.di.Injector;
import com.mkemp.advancedandroid.di.ScreenInjector;

import java.util.UUID;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity
{
    private static final String INSTANCE_ID_KEY = "instance_id";
    
    @Inject
    ScreenInjector screenInjector;
    
    // Uniques key for each activity even across configuration changes
    private String instanceId;
    private Router router;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        if ( savedInstanceState != null )
        {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        }
        else
        {
            instanceId = UUID.randomUUID().toString();
        }
        
        Injector.inject(this);
        setContentView(layoutRes());
        
        ViewGroup screenContainer = findViewById(R.id.screen_container);
        if ( screenContainer == null )
        {
            throw new NullPointerException("Activity must have a view with id: screen_container");
        }
        
        router = Conductor.attachRouter(this, screenContainer, savedInstanceState);
        monitorBackStack();
        
        super.onCreate(savedInstanceState);
    }
    
    @LayoutRes
    protected abstract int layoutRes();
    
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
        if ( isFinishing() )
        {
            Injector.clearComponent(this);
        }
    }
    
    public ScreenInjector getScreenInjector()
    {
        return screenInjector;
    }
    
    
    private void monitorBackStack()
    {
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener()
        {
            @Override
            public void onChangeStarted(
                    @Nullable Controller to,
                    @Nullable Controller from,
                    boolean isPush,
                    @NonNull ViewGroup container,
                    @NonNull ControllerChangeHandler handler)
            {
            
            }
            
            @Override
            public void onChangeCompleted(
                    @Nullable Controller to,
                    @Nullable Controller from,
                    boolean isPush,
                    @NonNull ViewGroup container,
                    @NonNull ControllerChangeHandler handler)
            {
                if ( ! isPush && from != null )
                {
                    Injector.clearComponent(from);
                }
            }
        });
    }
}
