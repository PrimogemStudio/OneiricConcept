package net.mcreator.oneiricconcept.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class ExplosionIgnitesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double rang) {
		boolean found = false;
		double sx = 0;
		double sy = 0;
		double sz = 0;
		double xyz = 0;
		double range = 0;
		range = rang;
		xyz = Math.round(0 - (range - 1) / 2);
		sx = xyz;
		for (int index0 = 0; index0 < (int) range; index0++) {
			sy = xyz;
			for (int index1 = 0; index1 < (int) range; index1++) {
				sz = xyz;
				for (int index2 = 0; index2 < (int) range; index2++) {
					if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == Blocks.AIR) {
						if (RandomProcedure.execute(world, 0.5)) {
							{
								BlockPos _bp = BlockPos.containing(x + sx, y + sy, z + sz);
								BlockState _bs = Blocks.FIRE.defaultBlockState();
								BlockState _bso = world.getBlockState(_bp);
								for (Property<?> _propertyOld : _bso.getProperties()) {
									Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
									if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
										try {
											_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
										} catch (Exception e) {
										}
								}
								world.setBlock(_bp, _bs, 3);
							}
						}
					}
					sz = sz + 1;
				}
				sy = sy + 1;
			}
			sx = sx + 1;
		}
	}
}
