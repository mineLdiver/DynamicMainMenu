package net.mine_diver.dynamicmainmenu.mixin;

import net.mine_diver.dynamicmainmenu.DynamicMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.Living;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow public Living viewEntity;

    @Inject(method = "openScreen(Lnet/minecraft/client/gui/screen/ScreenBase;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;lockCursor()V", shift = At.Shift.BEFORE))
    private void stopMusic(ScreenBase screen, CallbackInfo ci) {
        if (DynamicMainMenu.musicId != null) {
            AccessorSoundHelper.getSoundSystem().stop(DynamicMainMenu.musicId);
            DynamicMainMenu.musicId = null;
        }
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ScreenBase;tick()V", shift = At.Shift.AFTER))
    private void tick(CallbackInfo ci) {
        if (DynamicMainMenu.musicId != null && viewEntity != null) {
            viewEntity.setPositionAndAngles(viewEntity.x, 128, viewEntity.z, 180, 30);
            viewEntity.velocityZ = -0.05;
            ((AccessorEntityBase) viewEntity).setFallDistance(0);
        }
    }
}
