package net.mine_diver.dynamicmainmenu.mixin;

import net.minecraft.entity.EntityBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityBase.class)
public interface AccessorEntityBase {

    @Accessor
    void setFallDistance(float fallDistance);
}
