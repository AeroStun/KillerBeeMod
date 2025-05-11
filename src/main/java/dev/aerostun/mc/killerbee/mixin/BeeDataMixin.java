/*
 *     Copyright 2020-2025 AeroStun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.aerostun.mc.killerbee.mixin;

import dev.aerostun.mc.killerbee.KillerBeeMod;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BeehiveBlockEntity.BeeData;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BeeData.class)
public abstract class BeeDataMixin {

    /**
     * @author AeroStun
     * @reason Gives brand new bees in a hive a chance to be killer bees
     */
    @Overwrite
    public static BeehiveBlockEntity.BeeData create(int ticksInHive) {
        // We don't have access to the world here to get a random generator,
        // however ticksInHive is randomly generated so piggy-back on its entropy
        var bee_id = ticksInHive % 4 == 0 ? KillerBeeMod.KILLER_BEE_ID
                : Registries.ENTITY_TYPE.getId(EntityType.BEE);
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("id", bee_id.toString());
        return new BeehiveBlockEntity.BeeData(NbtComponent.of(nbtCompound), ticksInHive, 600);
    }
}
