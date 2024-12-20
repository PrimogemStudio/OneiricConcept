
package net.mcreator.oneiricconcept.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.oneiricconcept.procedures.OrexpProcedure;

public class VayudaTurquoiseOreBlock extends Block {
	public VayudaTurquoiseOreBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).sound(SoundType.WOOL).strength(4f, 10f).requiresCorrectToolForDrops().speedFactor(0.7f).jumpFactor(2f).hasPostProcess((bs, br, bp) -> true)
				.emissiveRendering((bs, br, bp) -> true));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
		OrexpProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		return retval;
	}
}
