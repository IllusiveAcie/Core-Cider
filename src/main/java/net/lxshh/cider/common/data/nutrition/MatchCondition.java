package net.lxshh.cider.common.data.nutrition;

import com.mojang.serialization.Codec;
import net.dries007.tfc.common.component.food.INutritionData;
import net.minecraft.util.StringRepresentable;

import java.util.List;
import java.util.Locale;

public enum MatchCondition implements StringRepresentable {
    ALL,
    ANY;

    public static final Codec<MatchCondition> CODEC = StringRepresentable.fromEnum(MatchCondition::values);

    public boolean test(List<NutrientCondition> conditions, INutritionData nutritionData) {
        return switch (this) {
            case ALL -> conditions.stream().allMatch(c -> c.test(nutritionData));
            case ANY -> conditions.stream().anyMatch(c -> c.test(nutritionData));
        };
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

}

