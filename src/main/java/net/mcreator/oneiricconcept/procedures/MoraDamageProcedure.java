package net.mcreator.oneiricconcept.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.oneiricconcept.init.OneiricconceptModGameRules;
import net.mcreator.oneiricconcept.init.OneiricconceptModEntities;
import net.mcreator.oneiricconcept.entity.MoraCoinEntity;

public class MoraDamageProcedure {
	public static void execute(LevelAccessor world, Entity entity, double dmg, double spd) {
		if (entity == null)
			return;
		ItemStack magazineitem = ItemStack.EMPTY;
		magazineitem = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);
		if (magazineitem.getMaxDamage() - magazineitem.getDamageValue() <= 1) {
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(((BuiltInRegistries.ITEM.getKey(magazineitem.getItem()).toString())).toLowerCase(java.util.Locale.ENGLISH)))).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
		if (world instanceof ServerLevel _level) {
			magazineitem.hurtAndBreak(1, _level, null, _stkprov -> {
			});
		}
		{
			Entity _shootFrom = entity;
			Level projectileLevel = _shootFrom.level();
			if (!projectileLevel.isClientSide()) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
						AbstractArrow entityToSpawn = new MoraCoinEntity(OneiricconceptModEntities.MORA_COIN.get(), level) {
							@Override
							public byte getPierceLevel() {
								return piercing;
							}

							@Override
							protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
								if (knockback > 0) {
									double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
									Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
									if (vec3.lengthSqr() > 0.0) {
										livingEntity.push(vec3.x, 0.1, vec3.z);
									}
								}
							}
						};
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setSilent(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) (dmg * (world.getLevelData().getGameRules().getInt(OneiricconceptModGameRules.OC_DAMAGEMULTIPLIER))), 1, (byte) 0);
				_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
				_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) spd, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
	}
}
