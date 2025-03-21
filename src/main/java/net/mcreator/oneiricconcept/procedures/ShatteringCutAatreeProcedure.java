package net.mcreator.oneiricconcept.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.oneiricconcept.init.OneiricconceptModBlocks;

import java.util.Optional;
import java.util.Comparator;

public class ShatteringCutAatreeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double sx = 0;
		double sz = 0;
		double ent = 0;
		BlockState blok = Blocks.AIR.defaultBlockState();
		MaraRangeClearProcedure.execute(world, x, y, z, 67);
		sx = x;
		sz = z;
		blok = (world.getBlockState(BlockPos.containing(x, y, z)));
		if (!(blok.getBlock() == Blocks.END_GATEWAY)) {
			if (blok.getBlock() == OneiricconceptModBlocks.AMBROSIAL_ARBOR_LOG.get() || blok.getBlock() == OneiricconceptModBlocks.AMBROSIAL_ARBOR_LEAVE.get()) {
				blok = Blocks.AIR.defaultBlockState();
			}
			ClearArborProcedure.execute(world, x, y, z, blok);
		}
		for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 3); index0++) {
			if (world instanceof ServerLevel _level && _level.getServer() != null) {
				Optional<CommandFunction<CommandSourceStack>> _fopt = _level.getServer().getFunctions().get(ResourceLocation.parse("oneiricconcept:arbor_clear"));
				if (_fopt.isPresent())
					_level.getServer().getFunctions().execute(_fopt.get(), new CommandSourceStack(CommandSource.NULL, new Vec3(sx, y, sz), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null));
			}
			sx = x + Mth.nextInt(RandomSource.create(), -7, 7);
			sz = z + Mth.nextInt(RandomSource.create(), -7, 7);
		}
		{
			final Vec3 _center = new Vec3(x, 250, z);
			for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("oneiricconcept:divinearrow")))) {
					ent = ent + 1;
				}
			}
		}
		{
			final Vec3 _center = new Vec3(x, (y + 10), z);
			for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
				entityiterator.push((Mth.nextDouble(RandomSource.create(), -3, 3)), 3, (Mth.nextDouble(RandomSource.create(), -3, 3)));
			}
		}
		if (ent < 60) {
			DivineArrowProcedure.execute(world, x, y, z);
		}
	}
}
