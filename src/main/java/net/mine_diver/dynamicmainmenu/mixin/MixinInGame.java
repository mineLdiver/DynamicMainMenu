package net.mine_diver.dynamicmainmenu.mixin;

import net.mine_diver.dynamicmainmenu.DynamicMainMenu;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.util.ScreenScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGame.class)
public abstract class MixinInGame {

    @Inject(method = "renderHud(FZII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/InGame;renderVingette(FII)V", shift = At.Shift.BY, by = 2), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void cancelHud(float f, boolean flag, int i, int j, CallbackInfo ci, ScreenScaler var5, int var6, int var7) {
        if (DynamicMainMenu.musicId != null)
            ci.cancel();
    }
}
