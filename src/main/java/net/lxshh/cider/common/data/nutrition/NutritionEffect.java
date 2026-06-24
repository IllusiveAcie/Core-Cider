package net.lxshh.cider.common.data.nutrition;

import net.dries007.tfc.common.component.food.INutritionData;
import net.dries007.tfc.common.player.IPlayerInfo;
import net.dries007.tfc.util.data.DataManager;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.lxshh.cider.Cider;

import java.util.*;

/**
 * Heavily inspired by the mod 'Diet by TheIllusiveC4' data driven nutrition effect,
 * A large of the system was built on the example's provided on the Diet Wiki.
 */
public record NutritionEffect(
        List<NutrientCondition> nutrients,
        MatchCondition condition,
        List<NutritionMobEffect> mobEffects,
        List<NutritionAttributeModifier> attributes
) {

    public static final Codec<NutritionEffect> CODEC = RecordCodecBuilder.create(i -> i.group(
            NutrientCondition.CODEC.listOf(0, 5).fieldOf("nutrients").forGetter(NutritionEffect::nutrients),
            MatchCondition.CODEC.optionalFieldOf("match", MatchCondition.ANY).forGetter(NutritionEffect::condition),
            NutritionMobEffect.CODEC.listOf().optionalFieldOf("mob_effects", List.of()).forGetter(NutritionEffect::mobEffects),
            NutritionAttributeModifier.CODEC.listOf().optionalFieldOf("attributes", List.of()).forGetter(NutritionEffect::attributes)
    ).apply(i, NutritionEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NutritionEffect> STREAM_CODEC = StreamCodec.composite(
            NutrientCondition.STREAM_CODEC.apply(ByteBufCodecs.list(5)), NutritionEffect::nutrients,
            ByteBufCodecs.fromCodec(MatchCondition.CODEC), NutritionEffect::condition,
            NutritionMobEffect.STREAM_CODEC.apply(ByteBufCodecs.list()), NutritionEffect::mobEffects,
            NutritionAttributeModifier.STREAM_CODEC.apply(ByteBufCodecs.list()), NutritionEffect::attributes,
            NutritionEffect::new
    );

    public static final DataManager<NutritionEffect> MANAGER = new DataManager<>(Cider.loc("nutrition_effects"), CODEC, STREAM_CODEC);

    public boolean test(INutritionData nutritionData) {
        return condition.test(nutrients, nutritionData);
    }

    public static void nutritionEffectTick(Player player) {
        if (player.level().isClientSide()) return;

        if (player.isCreative() || player.isSpectator()) return;

        INutritionData nutrition = IPlayerInfo.get(player).nutrition();
        Collection<NutritionEffect> rules = MANAGER.getValues();

        for (NutritionEffect rule : rules) {
            ResourceLocation ruleId = NutritionEffect.MANAGER.getId(rule);
            if (ruleId == null) continue;

            ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(ruleId.getNamespace(), "nutrition_effect/" + ruleId.getPath());

            if (rule.test(nutrition)) {
                // Apply effects to the player
                for (NutritionMobEffect se : rule.mobEffects()) {
                    Holder<MobEffect> effectHolder = se.resolveEffect();

                    player.addEffect(new MobEffectInstance(effectHolder, 40, se.amplifier(), true, false, false));
                }
                //Apply attribute modifiers to the player
                for (NutritionAttributeModifier mod : rule.attributes()) {
                    Holder<Attribute> attrHolder = mod.resolveAttribute();

                    AttributeInstance instance = player.getAttribute(attrHolder);
                    if (instance == null) continue;

                    if (instance.getModifier(modifierId) == null) {
                        instance.addPermanentModifier(new AttributeModifier(modifierId, mod.amount(), mod.operation()));
                    }
                }
            } else {
                // Remove attribute modifiers from the player
                for (NutritionAttributeModifier mod : rule.attributes()) {
                    Holder<Attribute> attrHolder = mod.resolveAttribute();

                    AttributeInstance instance = player.getAttribute(attrHolder);
                    if (instance != null) {
                        instance.removeModifier(modifierId);
                    }
                }
            }
        }
    }
}
