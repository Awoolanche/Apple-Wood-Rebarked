package net.awoolanche.applewoodrebarked.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AppleChestBoatEntity extends ChestBoat {

    private static final EntityDataAccessor<Integer> WOOD_TYPE = SynchedEntityData.defineId(AppleChestBoatEntity.class, EntityDataSerializers.INT);
    public AppleChestBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public AppleChestBoatEntity(Level level, double x, double y, double z) {
        this(ModEntities.APPLE_CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WOOD_TYPE, AppleBoatEntity.Type.APPLE.ordinal());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setWoodType(AppleBoatEntity.Type.byName(pCompound.getString("Type")));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("Type", this.getWoodType().getName());
    }

    public AppleBoatEntity.Type getWoodType() {
        return AppleBoatEntity.Type.byId(this.entityData.get(WOOD_TYPE));
    }

    public void setWoodType(AppleBoatEntity.Type type) {
        this.entityData.set(WOOD_TYPE, type.ordinal());
    }
    public AppleBoatEntity.Type getModVariant() {
        return AppleBoatEntity.Type.byId(this.entityData.get(WOOD_TYPE));
    }
    @Override
    public @NotNull Item getDropItem() {
        return this.getWoodType().getChestItem().get();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this,entity);
    }
}