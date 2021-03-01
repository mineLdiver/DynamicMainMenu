package net.mine_diver.dynamicmainmenu.mixin;

import net.mine_diver.dynamicmainmenu.DynamicMainMenu;
import net.minecraft.class_537;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.MainMenu;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MainMenu.class)
public class MixinMainMenu extends ScreenBase {

    @Shadow private float ticksOpened;

    @Inject(method = "init()V", at = @At("TAIL"))
    private void startMusic(CallbackInfo ci) {
        if (minecraft.level == null) {
            minecraft.interactionManager = new class_537(minecraft);
            String name = DynamicMainMenu.MODID + "/MainMenu";
            LevelProperties properties = minecraft.getLevelStorage().getLevelData(name);
            if (properties != null && (properties.getSizeOnDisk() / 1024L * 100L / 1024L) / 100 > 25) {
                LevelStorage levelStorage = minecraft.getLevelStorage();
                levelStorage.method_1003();
                levelStorage.method_1006(name);
            }
            minecraft.createOrLoadWorld(name, name, new Random().nextLong());
        }
        if (DynamicMainMenu.musicId == null) {
            AccessorSoundHelper.getSoundSystem().stop("BgMusic");
            DynamicMainMenu.captureSoundId = true;
            minecraft.soundHelper.playSound(DynamicMainMenu.modular.toString(), 1, 1);
        }
    }

    @ModifyConstant(method = "render(IIF)V", constant = @Constant(intValue = 5263440))
    private int modCon(int color) {
        long delta = System.currentTimeMillis() - DynamicMainMenu.musicStartTimestamp;
        if (delta >= 25000 && delta < 30000) {
            int red = (color & 0xFF0000) >> 16;
            int green = (color & 0x00FF00) >> 8;
            int blue = color & 0x0000FF;
            red += (delta - 25000) / (5000F / (256 - red));
            green += (delta - 25000) / (5000F / (256 - green));
            blue += (delta - 25000) / (5000F / (256 - blue));
            return red << 16 | green << 8 | blue;
        } else if (delta >= 30000)
            return 255 << 16 | 255 << 8 | 255;
        else
            return color;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void test(CallbackInfo ci) {
        if (ticksOpened < 1) {
            DynamicMainMenu.musicStartTimestamp = System.currentTimeMillis();
        }
    }
}
