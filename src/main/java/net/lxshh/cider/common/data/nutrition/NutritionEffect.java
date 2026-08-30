package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;

public record NutritionEffect(
    Holder<MobEffect> effect,
    int amplifier
) {
    public static final Codec<NutritionEffect> CODEC = RecordCodecBuilder.create(i -> i.group(
            MobEffect.CODEC.fieldOf("effect").forGetter(NutritionEffect::effect),
            Codec.INT.optionalFieldOf("amplifier", 1).forGetter(NutritionEffect::amplifier)
    ).apply(i, NutritionEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutritionEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), NutritionEffect::effect,
            ByteBufCodecs.INT, NutritionEffect::amplifier,
            NutritionEffect::new
    );
}
