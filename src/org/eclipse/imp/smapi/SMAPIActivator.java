package org.eclipse.imp.smapi;

import org.eclipse.imp.runtime.PluginBase;

public class SMAPIActivator extends PluginBase {
    public static final String kPluginID= "org.eclipse.imp.smapi";

    // This language name is bogus; it's only used as a qualifying prefix in the preference store.
    public static final String kLanguageName= "smapi";

    /**
     * The unique instance of this plugin class
     */
    protected static SMAPIActivator sPlugin;

    public static SMAPIActivator getInstance() {
        if (sPlugin == null)
            new SMAPIActivator();
        return sPlugin;
    }

    public SMAPIActivator() {
        super();
        sPlugin= this;
    }

    @Override
    public String getID() {
        return kPluginID;
    }

    @Override
    public String getLanguageID() {
        return kLanguageName;
    }
}
