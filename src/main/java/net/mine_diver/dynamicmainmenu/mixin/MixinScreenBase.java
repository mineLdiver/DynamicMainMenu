package net.mine_diver.dynamicmainmenu.mixin;

import net.mine_diver.dynamicmainmenu.DynamicMainMenu;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.Tessellator;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenBase.class)
public abstract class MixinScreenBase extends DrawableHelper {

    @Shadow public abstract void renderDirtBackground(int alpha);

    @Shadow public int width;

    @Shadow public int height;

    @Redirect(method = "renderDirtBackground(I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;colour(I)V"))
    private void redMet(Tessellator tessellator, int i) {
        if (DynamicMainMenu.glowingBackground) {
            int col = 48 + (int) (MathHelper.abs(MathHelper.sin((float) (System.currentTimeMillis() % 50000L) / 50000.0F * (float) Math.PI * 2.0F)) * 32);
            i = col << 16 | col << 8 | col;
        }
        if (DynamicMainMenu.coloredTransition)
            tessellator.colour(i);
        else {
            long delta = System.currentTimeMillis() - DynamicMainMenu.musicStartTimestamp;
            tessellator.colour(i, (int) (delta < 25000 ? 255 : 256 - (delta - 25000) / (5000F / 256)));
        }
    }

    @ModifyArg(method = "renderDirtBackground(I)V", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;vertex(DDDDD)V"))
    private double modArg(double arg) {
        return DynamicMainMenu.flowingBackground ? arg + (System.currentTimeMillis() % 5000) / 5000D : arg;
    }

    @Inject(method = "isPauseScreen()Z", at = @At("RETURN"), cancellable = true)
    private void isPauseScreen(CallbackInfoReturnable<Boolean> cir) {
        if (DynamicMainMenu.musicId != null)
            cir.setReturnValue(false);
    }

    @Inject(method = "renderBackground(I)V", at = @At("HEAD"), cancellable = true)
    private void renderBackground(int alpha, CallbackInfo ci) {
        if (DynamicMainMenu.musicId != null) {
            long delta = System.currentTimeMillis() - DynamicMainMenu.musicStartTimestamp;
            if (delta <= 30000)
                renderDirtBackground(alpha);
            if (DynamicMainMenu.coloredTransition) {
                if (delta >= 25000 && delta <= 30000) {
                    int transparent = (int) ((delta - 25000) / (5000F / 256));
                    fill(0, 0, width, height, transparent << 24 | DynamicMainMenu.transitionColorRed << 16 | DynamicMainMenu.transitionColorGreen << 8 | DynamicMainMenu.transitionColorBlue);
                } else if (delta > 30000 && delta <= 31000) {
                    int transparent = (int) (256 - (delta - 30000) / (1000F / 256));
                    fill(0, 0, width, height, transparent << 24 | DynamicMainMenu.transitionColorRed << 16 | DynamicMainMenu.transitionColorGreen << 8 | DynamicMainMenu.transitionColorBlue);
                }
            }
            ci.cancel();
        }
    }
}
