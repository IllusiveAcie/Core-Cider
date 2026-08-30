package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.util.data.DataManager;
import net.lxshh.cider.util.CiderHelpers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record Nutrition(
        List<NutritionCondition> conditions,
        List<NutritionEffect> effects,
        List<NutritionAttribute> attributes
) {

    public static final Codec<Nutrition> CODEC = RecordCodecBuilder.create(i -> i.group(
            NutritionCondition.CODEC.listOf().fieldOf("conditions").forGetter(Nutrition::conditions),
            NutritionEffect.CODEC.listOf().fieldOf("effects").forGetter(Nutrition::effects),
            NutritionAttribute.CODEC.listOf().fieldOf("attributes").forGetter(Nutrition::attributes)
    ).apply(i, Nutrition::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Nutrition> STREAM_CODEC = StreamCodec.composite(
            NutritionCondition.STREAM_CODEC.apply(ByteBufCodecs.list()), Nutrition::conditions,
            NutritionEffect.STREAM_CODEC.apply(ByteBufCodecs.list()), Nutrition::effects,
            NutritionAttribute.STREAM_CODEC.apply(ByteBufCodecs.list()), Nutrition::attributes,
            Nutrition::new
    );

    public static final DataManager<Nutrition> MANAGER = new DataManager<>(CiderHelpers.identifier("nutrition_effects"), CODEC, STREAM_CODEC);

}
