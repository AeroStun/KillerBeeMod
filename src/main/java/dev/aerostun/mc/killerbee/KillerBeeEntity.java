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

import dev.aerostun.mc.killerbee.bridge.IBeeEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class KillerBeeEntity extends BeeEntity {
    public KillerBeeEntity(EntityType<? extends KillerBeeEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createKillerBeeAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MAX_HEALTH, 16.0D)
                .add(EntityAttributes.FLYING_SPEED, 0.6D)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.FOLLOW_RANGE, 112.0D);
    }

    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return false;
        } else if (other.getClass() != KillerBeeEntity.class && other.getClass() != BeeEntity.class) {
            return false;
        } else {
            return this.isInLove() && other.isInLove();
        }
    }

    public BeeEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        if (passiveEntity.getClass() == KillerBeeEntity.class || serverWorld.random.nextBoolean())
            return KillerBeeMod.KILLER_BEE.create(serverWorld, SpawnReason.BREEDING);
        return EntityType.BEE.create(serverWorld, SpawnReason.BREEDING);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        DamageSource damageSource = this.getDamageSources().sting(this);
        boolean bl = target.damage(world, damageSource, (int) this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE));
        if (bl) {
            EnchantmentHelper.onTargetDamaged(world, target, damageSource);
            if (target instanceof LivingEntity livingEntity) {
                livingEntity.setStingerCount(livingEntity.getStingerCount() + 1);
                int i = 0;
                int amp = 0;
                if (world.getDifficulty() == Difficulty.EASY)
                    i = 2;
                else if (world.getDifficulty() == Difficulty.NORMAL) {
                    i = 12;
                    amp = 1;
                } else if (world.getDifficulty() == Difficulty.HARD) {
                    i = 20;
                    amp = 2;
                }

                if (i > 0)
                    livingEntity
                            .addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, amp));
            }

            ((IBeeEntity) (BeeEntity) this).$setHasStung(true);
            this.stopAnger();
            this.playSound(SoundEvents.ENTITY_BEE_STING, 1.0F, 1.0F);
        }

        return bl;
    }
}
