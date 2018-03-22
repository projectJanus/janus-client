import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.MovementMessage.setDirection;

public class ClientHandler extends SimpleChannelInboundHandler<setDirection> {
    /**
     * Created by A. Bashilov on 20/03/18
     * <p>
     * Class for read channel, decode and pars DatagramPacker to SocketAddress and protobuf "setDirection"
     * <p>
     * {@link #channelRead0(ChannelHandlerContext, setDirection)} - override method for read channel;
     * <p>
     * {@link #exceptionCaught(ChannelHandlerContext, Throwable)} - override method for close channel because of Exeption;
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, setDirection msg) throws Exception {
        //Печатаем в консоль дынне для проверки
        System.out.println("Клиент получил сообщение DirectionX: " + msg.getDirectionX() + " DirectionY: " + msg.getDirectionY());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}