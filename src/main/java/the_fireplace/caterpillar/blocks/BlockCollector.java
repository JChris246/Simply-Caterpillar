package the_fireplace.caterpillar.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import the_fireplace.caterpillar.Caterpillar;
import the_fireplace.caterpillar.containers.CaterpillarData;

import java.util.ArrayList;
import java.util.List;

public class BlockCollector extends BlockDrillBase
{
	public BlockCollector(){
		super();
		this.movementTicks = 50;
	}

	@Override
	protected void fired(World worldIn, BlockPos pos, IBlockState state, String catID, int[] movingXZ, int Count)
	{
		List<Entity> ETList = new ArrayList<>();

		for (int i = 0; i < worldIn.loadedEntityList.size(); i++) {
			Entity ETObject = worldIn.loadedEntityList.get(i);
			if (ETObject instanceof EntityItem)
			{
				if (ETObject.getPosition().distanceSq(pos.getX(), pos.getY(), pos.getZ()) < 7*7)
				{
					ETList.add(ETObject);
				}
			}
		}

		ETList.stream().filter(ETObject -> ETObject instanceof EntityItem).forEach(ETObject -> {
			CaterpillarData myCat = Caterpillar.instance.getContainerCaterpillar(pos, state);
			if (myCat.addToOutInventory(((EntityItem) ETObject).getEntityItem())) {
				worldIn.removeEntity(ETObject);
			}
		});
	}
}