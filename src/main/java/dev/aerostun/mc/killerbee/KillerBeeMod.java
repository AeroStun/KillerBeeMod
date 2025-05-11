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

package dev.aerostun.mc.killerbee;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class KillerBeeMod implements ModInitializer {
        public static final Identifier KILLER_BEE_ID = Identifier.of("killerbee", "killer_bee");
        public static final EntityType<KillerBeeEntity> KILLER_BEE = Registry.register(
                        Registries.ENTITY_TYPE,
                        KILLER_BEE_ID,
                        EntityType.Builder.create(KillerBeeEntity::new, SpawnGroup.CREATURE)
                                        .dimensions(0.7F, 0.6F)
                                        .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, KILLER_BEE_ID)));

        public static final Identifier KILLER_BEE_SPAWN_EGG_ID = Identifier.of("killerbee", "killer_bee_spawn_egg");
        public static final Item KILLER_BEE_SPAWN_EGG = Registry.register(Registries.ITEM, KILLER_BEE_SPAWN_EGG_ID,
                        new SpawnEggItem(KILLER_BEE,
                                        new Item.Settings().registryKey(
                                                        RegistryKey.of(RegistryKeys.ITEM, KILLER_BEE_SPAWN_EGG_ID))));

        @Override
        public void onInitialize() {
                FabricDefaultAttributeRegistry.register(KILLER_BEE, KillerBeeEntity.createKillerBeeAttributes());

                ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
                                .register(itemGroup -> itemGroup.add(KILLER_BEE_SPAWN_EGG));
        }
}
