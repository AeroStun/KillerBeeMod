/*
 *     Copyright 2020 AeroStun
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

package dev.aerostun.mc.killerbee;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class KillerBeeMod implements ModInitializer {
    public static final EntityType<KillerBeeEntity> KILLER_BEE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("killerbee", "killer_bee"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KillerBeeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7F, 0.6F))
                    .build());

    private static final Item BEE_SPAWN_EGG = Registry.register(
            Registry.ITEM,
            new Identifier("killerbee", "killer_bee_spawn_egg"),
            new SpawnEggItem(KILLER_BEE, 0xEDC343, 0xB31C15, new FabricItemSettings().group(ItemGroup.MISC)));

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(KILLER_BEE, KillerBeeEntity.createKillerBeeAttributes());
    }
}
