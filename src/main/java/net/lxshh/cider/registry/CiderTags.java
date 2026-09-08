package net.lxshh.cider.registry;

import net.lxshh.cider.util.CiderHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CiderTags {
    public static class Items {

        public static TagKey<Item> create(String path) {
            return TagKey.create(Registries.ITEM, CiderHelpers.identifier(path));
        }
        public static TagKey<Item> common(String path) {
            return TagKey.create(Registries.ITEM, CiderHelpers.identifierCommon(path));
        }
    }

    public static class Blocks {

        public static TagKey<Block> create(String path) {
            return TagKey.create(Registries.BLOCK, CiderHelpers.identifier(path));
        }
        public static TagKey<Block> common(String path) {
            return TagKey.create(Registries.BLOCK, CiderHelpers.identifierCommon(path));
        }
    }

    public static class Entities {

        public static TagKey<EntityType<?>> create(String path) {
            return TagKey.create(Registries.ENTITY_TYPE, CiderHelpers.identifier(path));
        }
        public static TagKey<EntityType<?>> common(String path) {
            return TagKey.create(Registries.ENTITY_TYPE, CiderHelpers.identifierCommon(path));
        }
    }
}
