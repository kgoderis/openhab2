/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.io.homekit.v1.internal.accessories;

import java.util.concurrent.CompletableFuture;

import org.openhab.core.items.GenericItem;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.io.homekit.v1.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.v1.internal.HomekitTaggedItem;
import org.openhab.io.homekit.v1.internal.battery.BatteryStatus;

import  org.openhab.io.homekit.hap.HomekitCharacteristicChangeCallback;
import  org.openhab.io.homekit.hap.accessories.BatteryStatusAccessory;
import  org.openhab.io.homekit.hap.accessories.OccupancySensor;

/**
 *
 * @author Tim Harper - Initial contribution
 */
public class HomekitOccupancySensorImpl extends AbstractHomekitAccessoryImpl<GenericItem>
        implements OccupancySensor, BatteryStatusAccessory {
    private BatteryStatus batteryStatus;
    private BooleanItemReader occupancySensedReader;

    public HomekitOccupancySensorImpl(HomekitTaggedItem taggedItem, ItemRegistry itemRegistry,
            HomekitAccessoryUpdater updater, BatteryStatus batteryStatus) {
        super(taggedItem, itemRegistry, updater, GenericItem.class);
        this.occupancySensedReader = new BooleanItemReader(taggedItem.getItem(), OnOffType.ON, OpenClosedType.OPEN);
        this.batteryStatus = batteryStatus;
    }

    @Override
    public CompletableFuture<Boolean> getOccupancyDetected() {
        return CompletableFuture.completedFuture(occupancySensedReader.getValue());
    }

    @Override
    public void subscribeOccupancyDetected(HomekitCharacteristicChangeCallback callback) {
        getUpdater().subscribe(getItem(), callback);
    }

    @Override
    public void unsubscribeOccupancyDetected() {
        getUpdater().unsubscribe(getItem());
    }

    @Override
    public CompletableFuture<Boolean> getLowBatteryState() {
        return CompletableFuture.completedFuture(batteryStatus.isLow());
    }

    @Override
    public void subscribeLowBatteryState(HomekitCharacteristicChangeCallback callback) {
        batteryStatus.subscribe(getUpdater(), callback);
    }

    @Override
    public void unsubscribeLowBatteryState() {
        batteryStatus.unsubscribe(getUpdater());
    }
}