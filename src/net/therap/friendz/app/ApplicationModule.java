package net.therap.friendz.app;

import com.google.inject.AbstractModule;
import net.therap.friendz.util.DatabaseManager;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        requestStaticInjection(DatabaseManager.class);
    }
}
