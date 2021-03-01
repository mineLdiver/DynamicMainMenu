package net.mine_diver.dynamicmainmenu.mixin;

import net.minecraft.client.sound.SoundHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import paulscode.sound.SoundSystem;

@Mixin(SoundHelper.class)
public interface AccessorSoundHelper {

    @Accessor
    static SoundSystem getSoundSystem() {
        throw new AssertionError("Mixin!");
    }
}
