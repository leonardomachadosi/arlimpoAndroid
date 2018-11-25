package br.ufma.lsdi.arlimpo;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.support.multidex.MultiDex;

import java.util.concurrent.TimeUnit;


/**
 * Created by Leeo on 26/10/15.
 */
public class ArLimpoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
