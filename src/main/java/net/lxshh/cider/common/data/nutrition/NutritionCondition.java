package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

import java.util.List;
import java.util.Locale;

public record NutritionCondition(
        List<String> nutrients,
        MatchCondition match,
        int above,
        int below
) {
    public static final Codec<NutritionCondition> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.listOf(1, 5).fieldOf("nutrients").forGetter(NutritionCondition::nutrients),
            MatchCondition.CODEC.optionalFieldOf("match", MatchCondition.AVERAGE).forGetter(NutritionCondition::match),
            Codec.INT.optionalFieldOf("above", 1).forGetter(NutritionCondition::above),
            Codec.INT.optionalFieldOf("below", 10).forGetter(NutritionCondition::below)
    ).apply(i, NutritionCondition::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutritionCondition> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list(5)), NutritionCondition::nutrients,
            ByteBufCodecs.fromCodec(MatchCondition.CODEC), NutritionCondition::match,
            ByteBufCodecs.INT, NutritionCondition::above,
            ByteBufCodecs.INT, NutritionCondition::below,
            NutritionCondition::new
    );


    public enum MatchCondition implements StringRepresentable {
        ANY,
        AVERAGE,
        ALL;

        public static final Codec<MatchCondition> CODEC = StringRepresentable.fromEnum(MatchCondition::values);

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
