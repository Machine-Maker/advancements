package me.machinemaker.datapacks.advancements.triggers;

import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface Trigger<I extends Trigger.Instance> extends Keyed {

    @ApiStatus.NonExtendable
    interface Instance {

    }
}
