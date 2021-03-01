package net.mine_diver.dynamicmainmenu.mixin;

import net.mine_diver.dynamicmainmenu.DynamicMainMenu;
import net.minecraft.util.ProgressListenerImpl;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ProgressListenerImpl.class)
public class MixinProgressListenerImpl {

    @ModifyConstant(method = "progressStagePercentage(I)V", constant = @Constant(intValue = 4210752))
    private int modCon(int constant) {
        int col = 48 + (int) (MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 50000L) / 50000.0F * (float)Math.PI * 2.0F)) * 32);
        return col << 16 | col << 8 | col;
    }

    @ModifyArg(method = "progressStagePercentage(I)V", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;vertex(DDDDD)V"))
    private double modArg(double arg) {
        return DynamicMainMenu.flowingBackground ? arg + (System.currentTimeMillis() % 5000) / 5000D : arg;
    }
}
