package com.atsistemas.magnolia.objeto.actions;

import info.magnolia.ui.dialog.action.SaveDialogAction;
import info.magnolia.ui.dialog.action.SaveDialogActionDefinition;

public class SaveOverrideActionDefinition extends SaveDialogActionDefinition {

    public SaveOverrideActionDefinition() {
        setImplementationClass(SaveOverrideAction.class);
    }

}
