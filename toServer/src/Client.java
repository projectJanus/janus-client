import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by A. Bashilov on 20/03/18
 * <p>
 * Class contain UDP client parameters and method:
 * {@link #start()} for open connection with server
 * and parameters of channel:
 * PORT = {@link #PORT}
 * HOST = {@link #HOST}
 */
public class Client {
    static final int PORT = 9999;
    static final String HOST = "192.168.0.102";
    public static Channel channel; //Сделал статическим для доступности, так как через канал отправляется данные методом writeAndFlush

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); //Создаем хэлпер для настройки подключения
            b.group(group)
                    .channel(NioDatagramChannel.class) //Назначаем тип канала
                    .handler(new ClientInitializer()); //Вешаем на канал хендлеры
            channel = b.connect(HOST, PORT).sync().channel(); //Коннектимся на адрес сервера
            channel.closeFuture().sync(); //Слушаем сервер
        } finally {
            group.shutdownGracefully();
        }
    }
}
