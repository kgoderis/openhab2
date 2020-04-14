package org.openhab.io.homekit.api;

import javax.json.JsonObject;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jetty.io.Connection;
import org.openhab.core.common.registry.Identifiable;
import org.openhab.io.homekit.internal.notification.NotificationUID;

//TODO Manage Connection from client.api and from server.api

@NonNullByDefault
public interface Notification extends Identifiable<NotificationUID> {

    void publish();

    void publish(JsonObject notification);

    Connection getConnection();

    ManagedCharacteristic<?> getCharacteristic();

}
