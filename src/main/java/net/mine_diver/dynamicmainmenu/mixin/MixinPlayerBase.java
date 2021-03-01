package net.mine_diver.dynamicmainmenu.mixin;

import net.mine_diver.dynamicmainmenu.DynamicMainMenu;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerBase.class)
public class MixinPlayerBase extends Living {

    public MixinPlayerBase(Level arg) {
        super(arg);
    }

    @Inject(method = "handleFallDamage(F)V", at = @At("HEAD"), cancellable = true)
    private void handleFallDamage(float height, CallbackInfo ci) {
        if (DynamicMainMenu.musicId != null)
            ci.cancel();
    }

    @Inject(method = "travel(FF)V", at = @At("HEAD"), cancellable = true)
    private void travel(float f, float f1, CallbackInfo ci) {
        if (DynamicMainMenu.musicId != null) {
            if (this.method_1334()) {
                this.movementInputToVelocity(f, f1, 0.02F);
                this.move(this.velocityX, this.velocityY, this.velocityZ);
                this.velocityX *= 0.8F;
                this.velocityY *= 0.8F;
                this.velocityZ *= 0.8F;
            } else if (this.method_1335()) {
                this.movementInputToVelocity(f, f1, 0.02F);
                this.move(this.velocityX, this.velocityY, this.velocityZ);
                this.velocityX *= 0.5D;
                this.velocityY *= 0.5D;
                this.velocityZ *= 0.5D;
            } else {
                float var3 = 0.91F;
                if (this.onGround) {
                    var3 = 0.54600006F;
                    int var4 = this.level.getTileId(MathHelper.floor(this.x), MathHelper.floor(this.boundingBox.minY) - 1, MathHelper.floor(this.z));
                    if (var4 > 0) {
                        var3 = BlockBase.BY_ID[var4].slipperiness * 0.91F;
                    }
                }

                float var10 = 0.16277136F / (var3 * var3 * var3);
                this.movementInputToVelocity(f, f1, this.onGround ? 0.1F * var10 : 0.02F);
                var3 = 0.91F;
                if (this.onGround) {
                    var3 = 0.54600006F;
                    int var5 = this.level.getTileId(MathHelper.floor(this.x), MathHelper.floor(this.boundingBox.minY) - 1, MathHelper.floor(this.z));
                    if (var5 > 0) {
                        var3 = BlockBase.BY_ID[var5].slipperiness * 0.91F;
                    }
                }

                this.move(this.velocityX, this.velocityY, this.velocityZ);
                this.velocityX *= var3;
                this.velocityY *= var3;
                this.velocityZ *= var3;
            }

            this.field_1048 = this.limbDistance;
            double var9 = this.x - this.prevX;
            double var11 = this.z - this.prevZ;
            float var7 = MathHelper.sqrt(var9 * var9 + var11 * var11) * 4.0F;
            if (var7 > 1.0F) {
                var7 = 1.0F;
            }

            this.limbDistance += (var7 - this.limbDistance) * 0.4F;
            this.field_1050 += this.limbDistance;
            ci.cancel();
        }
    }

    @Override
    public boolean method_932() {
        if (DynamicMainMenu.musicId == null)
            return super.method_932();
        else
            return false;
    }
}
