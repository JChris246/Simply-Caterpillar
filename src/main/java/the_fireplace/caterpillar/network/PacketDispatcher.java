package the_fireplace.caterpillar.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import the_fireplace.caterpillar.Caterpillar;
import the_fireplace.caterpillar.network.packets.clientbound.PacketIncrementClientInventory;
import the_fireplace.caterpillar.network.packets.clientbound.PacketParticles;
import the_fireplace.caterpillar.network.packets.serverbound.PacketIncrementInventory;

/**
 * @author coolAlias
 * @author The_Fireplace
 */
public class PacketDispatcher {
    private static byte packetId = 0;

    private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Caterpillar.MODID);

    public static final void registerPackets() {
        PacketDispatcher.registerMessage(PacketParticles.Handler.class, PacketParticles.class, Side.CLIENT);
        //PacketDispatcher.registerMessage(PacketRetrieveCatData.Handler.class, PacketRetrieveCatData.class, Side.CLIENT);
        //PacketDispatcher.registerMessage(PacketSendCatData.Handler.class, PacketSendCatData.class, Side.SERVER);
        PacketDispatcher.registerMessage(PacketIncrementInventory.Handler.class, PacketIncrementInventory.class, Side.SERVER);
        PacketDispatcher.registerMessage(PacketIncrementClientInventory.Handler.class, PacketIncrementClientInventory.class, Side.CLIENT);
    }

    private static final void registerMessage(Class handlerClass, Class messageClass, Side side) {
        PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
    }

    //Wrapper methods
    public static final void sendTo(IMessage message, EntityPlayerMP player) {
        PacketDispatcher.dispatcher.sendTo(message, player);
    }

    public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        PacketDispatcher.dispatcher.sendToAllAround(message, point);
    }

    public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
        PacketDispatcher.dispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
        PacketDispatcher.dispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(player.world.provider.getDimension(), player.posX, player.posY, player.posZ, range));
    }

    public static final void sendToDimension(IMessage message, int dimensionId) {
        PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
    }

    public static final void sendToServer(IMessage message) {
        PacketDispatcher.dispatcher.sendToServer(message);
    }
}