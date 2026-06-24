package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record NutritionAttributeModifier(
        ResourceLocation attribute,
        double amount,
        AttributeModifier.Operation operation
) {
    public static final Codec<NutritionAttributeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("attribute").forGetter(NutritionAttributeModifier::attribute),
            Codec.DOUBLE.fieldOf("amount").forGetter(NutritionAttributeModifier::amount),
            AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(NutritionAttributeModifier::operation)
    ).apply(instance, NutritionAttributeModifier::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutritionAttributeModifier> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, NutritionAttributeModifier::attribute,
            ByteBufCodecs.DOUBLE, NutritionAttributeModifier::amount,
            AttributeModifier.Operation.STREAM_CODEC, NutritionAttributeModifier::operation,
            NutritionAttributeModifier::new
    );

    public Holder<Attribute> resolveAttribute() {
        return BuiltInRegistries.ATTRIBUTE.getHolder(attribute).orElse(null);
    }
}