package net.mcreator.oneiricconcept.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.oneiricconcept.init.OneiricconceptModMobEffects;
import net.mcreator.oneiricconcept.init.OneiricconceptModGameRules;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;
import java.util.Calendar;
import java.util.ArrayList;

@EventBusSubscriber
public class HitTargetoffsetProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, damagesource, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity sourceentity, double amount) {
		if (damagesource == null || sourceentity == null)
			return;
		List<Object> Entitylist = new ArrayList<>();
		double efflevel = 0;
		double efftime = 0;
		efflevel = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(OneiricconceptModMobEffects.TARGETOFFSET) ? _livEnt.getEffect(OneiricconceptModMobEffects.TARGETOFFSET).getAmplifier() : 0;
		efftime = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(OneiricconceptModMobEffects.TARGETOFFSET) ? _livEnt.getEffect(OneiricconceptModMobEffects.TARGETOFFSET).getDuration() : 0;
		if (sourceentity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(OneiricconceptModMobEffects.TARGETOFFSET) && RandomProcedure.execute(world, (efflevel + 1) * 0.01)) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
					if (entityiterator instanceof Mob && !(entityiterator == sourceentity && (entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == sourceentity)) {
						Entitylist.add(entityiterator);
					}
				}
			}
			if (!Entitylist.isEmpty()) {
				(Entitylist.get(Mth.nextInt(RandomSource.create(), 0, (int) (Entitylist.size() - 1))) instanceof Entity _e ? _e : null).hurt(damagesource, (float) amount);
			}
			if (sourceentity instanceof Player) {
				if (sourceentity instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("oneiricconcept:fragile_crisp_cone"));
					if (_adv != null) {
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
			if (0 < efftime) {
				if (sourceentity instanceof LivingEntity _entity)
					_entity.removeEffect(OneiricconceptModMobEffects.TARGETOFFSET);
				TargetcoolingProcedure.execute(world, sourceentity, efflevel, efftime);
			}
			if (world.getLevelData().getGameRules().getBoolean(OneiricconceptModGameRules.OCDEBUG)) {
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("\u00A7" + ("1234569abcde".substring((int) (world.dayTime() % 12))).substring(0, 0) + Calendar.getInstance().getTime().toString())), false);
			}
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
		}
	}
}
