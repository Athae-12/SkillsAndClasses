package com.athae.skillsandclasses;

import com.athae.skillsandclasses.events.skillsandclassesEvents;

public class DatapackBoolean {
    public static boolean DISABLE_DATABASE_DATAPACK_FEATURE = false;

    public static boolean runDevTools() {
        skillsandclassesEvents.OnCheckIsDevToolsRunning event = skillsandclassesEvents.CHECK_IF_DEV_TOOLS_SHOULD_RUN.callEvents(new skillsandclassesEvents.OnCheckIsDevToolsRunning());
        return event.run;
    }
}
