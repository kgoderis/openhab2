package org.openhab.io.homekit.library.accessory;

import org.eclipse.jdt.annotation.NonNull;
import org.openhab.io.homekit.HomekitCommunicationManager;
import org.openhab.io.homekit.api.AccessoryServer;
import org.openhab.io.homekit.internal.accessory.AbstractAccessory;
import org.openhab.io.homekit.library.service.HAPProtocolInformationService;

public class BridgeAccessory extends AbstractAccessory {

    public BridgeAccessory(HomekitCommunicationManager manager, AccessoryServer server, long instanceId, boolean extend)
            throws Exception {
        super(manager, server, instanceId, extend);
    }

    @Override
    public void addServices() throws Exception {
        super.addServices();
        addService(new HAPProtocolInformationService(manager, this, this.getInstanceId(), true, getLabel()));
    }

    @Override
    public @NonNull String getLabel() {
        return this.getServer().getId();
    }

    @Override
    public @NonNull String getManufacturer() {
        return "openHAB";
    }

}
