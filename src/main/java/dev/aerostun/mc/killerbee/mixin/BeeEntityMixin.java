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

import dev.aerostun.mc.killerbee.KillerBeeMod;
import dev.aerostun.mc.killerbee.bridge.IBeeEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity implements IBeeEntity {
    protected BeeEntityMixin(EntityType<? extends BeeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(
            method = "initGoals()V",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/entity/passive/AnimalEntity;D)Lnet/minecraft/entity/ai/goal/AnimalMateGoal;"
            )
    )
    AnimalMateGoal makeGenericBeeMatingGoal(AnimalEntity animal, double chance) {
        return new AnimalMateGoal(animal, chance, BeeEntity.class);
    }

    /**
    * @author AeroStun
    * @reason Handles cross-breeding with killer-bees
    */
    @Overwrite
    public BeeEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        if(passiveEntity.getClass() == BeeEntity.class || serverWorld.random.nextBoolean())
            return EntityType.BEE.create(serverWorld);
        return KillerBeeMod.KILLER_BEE.create(serverWorld);
    }

    @Shadow
    private void setHasStung(boolean hasStung) { /* dummy body */ }

    public void $setHasStung(boolean hasStung) {
        this.setHasStung(hasStung);
    }
}
