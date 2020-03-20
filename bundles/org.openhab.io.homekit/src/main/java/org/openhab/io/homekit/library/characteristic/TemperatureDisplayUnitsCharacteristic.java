package org.openhab.io.homekit.library.characteristic;

import org.openhab.io.homekit.HomekitCommunicationManager;
import org.openhab.io.homekit.api.ManagedService;
import org.openhab.io.homekit.internal.characteristic.ByteCharacteristic;

public class TemperatureDisplayUnitsCharacteristic extends ByteCharacteristic {

    public TemperatureDisplayUnitsCharacteristic(HomekitCommunicationManager manager, ManagedService service,
            long instanceId) {
        super(manager, service, instanceId, false, true, true, "Units of temperature used for presentation purposes",
                (byte) 0, (byte) 1);
    }

    public static String getType() {
        return "00000036-0000-1000-8000-0026BB765291";
    }

    @Override
    public String getInstanceType() {
        return getType();
    }

}