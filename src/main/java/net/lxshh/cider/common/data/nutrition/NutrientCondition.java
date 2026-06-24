package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.component.food.INutritionData;
import net.dries007.tfc.common.component.food.Nutrient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;

public record NutrientCondition(
        Nutrient nutrient,
        Optional<Float> above,
        Optional<Float> below
) {
    public static final Codec<Nutrient> NUTRIENT_CODEC = StringRepresentable.fromEnum(Nutrient::values);

    public static final Codec<NutrientCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            NUTRIENT_CODEC.fieldOf("nutrient").forGetter(NutrientCondition::nutrient),
            Codec.FLOAT.optionalFieldOf("above").forGetter(NutrientCondition::above),
            Codec.FLOAT.optionalFieldOf("below").forGetter(NutrientCondition::below)
    ).apply(instance, NutrientCondition::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutrientCondition> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(NUTRIENT_CODEC), NutrientCondition::nutrient,
            ByteBufCodecs.optional(ByteBufCodecs.FLOAT), NutrientCondition::above,
            ByteBufCodecs.optional(ByteBufCodecs.FLOAT), NutrientCondition::below,
            NutrientCondition::new
    );

    public boolean test(INutritionData nutritionData) {
        float value = nutritionData.getNutrient(nutrient);
        if (above.isPresent() && value < above.get()) {
            return false;
        }
        return below.isEmpty() || !(value > below.get());
    }

}
