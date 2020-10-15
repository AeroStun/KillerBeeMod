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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KillerBeeEntityRenderer extends MobEntityRenderer<KillerBeeEntity, BeeEntityModel<KillerBeeEntity>> {
    private static final Identifier ANGRY_TEXTURE = new Identifier("killerbee", "textures/entity/killer_bee/bee_angry.png");
    private static final Identifier ANGRY_NECTAR_TEXTURE = new Identifier("killerbee", "textures/entity/killer_bee/bee_angry_nectar.png");
    private static final Identifier PASSIVE_TEXTURE = new Identifier("killerbee", "textures/entity/killer_bee/bee.png");
    private static final Identifier NECTAR_TEXTURE = new Identifier("killerbee", "textures/entity/killer_bee/bee_nectar.png");

    public KillerBeeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BeeEntityModel<>(), 0.4F);
    }

    @Override
    public Identifier getTexture(KillerBeeEntity beeEntity) {
        if (beeEntity.hasAngerTime()) {
            return beeEntity.hasNectar() ? ANGRY_NECTAR_TEXTURE : ANGRY_TEXTURE;
        } else {
            return beeEntity.hasNectar() ? NECTAR_TEXTURE : PASSIVE_TEXTURE;
        }
    }
}