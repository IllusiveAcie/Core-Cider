package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record NutritionAttribute(
        Holder<Attribute> attribute,
        double amount,
        AttributeModifier.Operation type
) {
    public static final Codec<NutritionAttribute> CODEC = RecordCodecBuilder.create(i -> i.group(
            Attribute.CODEC.fieldOf("attribute").forGetter(NutritionAttribute::attribute),
            Codec.DOUBLE.fieldOf("amount").forGetter(NutritionAttribute::amount),
            AttributeModifier.Operation.CODEC.optionalFieldOf("type", AttributeModifier.Operation.ADD_VALUE).forGetter(NutritionAttribute::type)
    ).apply(i, NutritionAttribute::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutritionAttribute> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ATTRIBUTE), NutritionAttribute::attribute,
            ByteBufCodecs.DOUBLE, NutritionAttribute::amount,
            AttributeModifier.Operation.STREAM_CODEC, NutritionAttribute::type,
            NutritionAttribute::new
    );
}
