/**
 *
 */
package org.openhab.io.homekit.internal.characteristic;

import javax.json.JsonNumber;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.State;
import org.openhab.io.homekit.HomekitCommunicationManager;
import org.openhab.io.homekit.api.ManagedService;

/**
 * @author Karel Goderis - Initial Contribution
 *
 */
public abstract class BooleanCharacteristic extends AbstractManagedCharacteristic<Boolean> {

    public BooleanCharacteristic(HomekitCommunicationManager manager, ManagedService service, long instanceId,
            boolean isWritable, boolean isReadable, boolean hasEvents, String description) {
        super(manager, service, instanceId, "bool", isWritable, isReadable, hasEvents, description);
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    protected Boolean getDefault() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean convert(JsonValue value) {
        if (value.getValueType().equals(ValueType.NUMBER)) {
            return ((JsonNumber) value).intValue() > 0;
        }
        return value.equals(JsonValue.TRUE);
    }

    @Override
    protected Boolean convert(State state) {
        OnOffType convertedState = state.as(OnOffType.class);
        if (convertedState == null) {
            return null;
        }

        return convertedState.equals(OnOffType.ON);
    }

    @Override
    protected State convert(Boolean value) {
        return value ? OnOffType.ON : OnOffType.OFF;
    }
}