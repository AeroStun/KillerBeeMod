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

package dev.aerostun.mc.killerbee.mixin;

import dev.aerostun.mc.killerbee.KillerBeeEntity;
import dev.aerostun.mc.killerbee.KillerBeeMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.tree.BeehiveTreeDecorator;
import net.minecraft.world.gen.tree.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeehiveTreeDecorator.class)
public abstract class BeehiveTreeDecoratorMixin extends TreeDecorator {

    @Redirect(
            method = "generate(Lnet/minecraft/world/StructureWorldAccess;Ljava/util/Random;Ljava/util/List;Ljava/util/List;Ljava/util/Set;Lnet/minecraft/util/math/BlockBox;)V",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)Lnet/minecraft/entity/passive/BeeEntity;"
            )
    )
    private BeeEntity selectBeeType(EntityType<? extends BeeEntity> entityType, World world) {
        return world.random.nextInt(4) == 0 ? new KillerBeeEntity(KillerBeeMod.KILLER_BEE, world) : new BeeEntity(entityType, world);
    }
}
