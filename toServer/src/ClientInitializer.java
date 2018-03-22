import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.DatagramPacketDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import protobuf.MovementMessage.setDirection;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by A. Bashilov on 20/03/18
 * <p>
 * Class contain pipline for channel when pipeline contain handlers for:
 * 1) Logging
 * 2) Decode in message
 * 3) Encode out message
 * 4) Read decode brotobuf objects
 */
public class ClientInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {

        //Создаем пайплайн для работы с каналом (каждый попадающий пакет в пайплайн проходит по порядку через все хэндлеры)
        ChannelPipeline pipeline = ch.pipeline();

        //Добавляем логгер для отладки
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        //Добавляем декодер Protobuf для дессириализации, предварительно раскодировав DatagramPacker
        pipeline.addLast(new DatagramPacketDecoder(new ProtobufDecoder(setDirection.getDefaultInstance())));

        //Добавляем энкодер для серриализации отправляемых объектов Protobuf
        pipeline.addLast(new ProtobufEncoder());

        //Создаем хэндлер для распакованных объектов Protobuf
        pipeline.addLast(new ClientHandler());
    }

}