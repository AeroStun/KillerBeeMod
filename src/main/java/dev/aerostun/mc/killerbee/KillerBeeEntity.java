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

import dev.aerostun.mc.killerbee.bridge.IBeeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
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
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6000000238418579D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 112.0D);
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
        if(passiveEntity.getClass() == KillerBeeEntity.class || serverWorld.random.nextBoolean())
            return KillerBeeMod.KILLER_BEE.create(serverWorld);
        return EntityType.BEE.create(serverWorld);
    }

    @Override
    public boolean tryAttack(Entity target) {
        final boolean bl = target.damage(DamageSource.sting(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.dealDamage(this, target);
            if (target instanceof LivingEntity) {
                ((LivingEntity)target).setStingerCount(((LivingEntity)target).getStingerCount() + 1);
                int i = 0;
                int amp = 0;
                if (this.world.getDifficulty() == Difficulty.EASY)
                    i = 2;
                else if (this.world.getDifficulty() == Difficulty.NORMAL){
                    i = 12;
                    amp = 1;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 20;
                    amp = 2;
                }

                if (i > 0)
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 30, amp));
            }

            ((IBeeEntity)(BeeEntity)this).$setHasStung(true);
            this.stopAnger();
            this.playSound(SoundEvents.ENTITY_BEE_STING, 1.0F, 1.0F);
        }

        return bl;
    }
}
