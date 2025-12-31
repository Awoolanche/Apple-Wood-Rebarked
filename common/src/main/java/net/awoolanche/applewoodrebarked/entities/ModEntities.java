package net.awoolanche.applewoodrebarked.entities;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.satisfy.vinery.platform.PlatformHelper;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(AppleWoodRebarked.MOD_ID, Registries.ENTITY_TYPE);

    public static final Supplier<EntityType<AppleBoatEntity>> APPLE_BOAT = PlatformHelper.registerBoatType("apple_boat", AppleBoatEntity::new, MobCategory.MISC, 1.375F, 0.5625F, 10);
    public static final Supplier<EntityType<AppleChestBoatEntity>> APPLE_CHEST_BOAT = PlatformHelper.registerBoatType("apple_chest_boat", AppleChestBoatEntity::new, MobCategory.MISC, 1.375F, 0.5625F, 10);
    public static final RegistrySupplier<EntityType<SlingshotProjectileEntity>> SLINGSHOT_PROJECTILE = ENTITY_TYPES.register("slingshot_projectile", () -> EntityType.Builder.<SlingshotProjectileEntity>of(SlingshotProjectileEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("slingshot_projectile"));

    public static <T extends EntityType<?>> RegistrySupplier<T> registerEntity(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(AppleWoodRebarked.identifier(path), type);
    }

    public static void init() {
        ENTITY_TYPES.register();
    }
}
