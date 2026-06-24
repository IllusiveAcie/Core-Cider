package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public record NutritionMobEffect(
        ResourceLocation effect,
        int amplifier
) {
    public static final Codec<NutritionMobEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("effect").forGetter(NutritionMobEffect::effect),
            Codec.INT.optionalFieldOf("amplifier", 0).forGetter(NutritionMobEffect::amplifier)
    ).apply(instance, NutritionMobEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutritionMobEffect> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, NutritionMobEffect::effect,
            ByteBufCodecs.INT, NutritionMobEffect::amplifier,
            NutritionMobEffect::new
    );

    public Holder<MobEffect> resolveEffect() {
        return BuiltInRegistries.MOB_EFFECT.getHolder(effect).orElse(null);
    }
}
