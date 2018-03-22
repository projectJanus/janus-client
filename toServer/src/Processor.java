import protobuf.MovementMessage.setDirection;

import java.util.Scanner;

public class Processor implements Runnable {
    Scanner in = new Scanner(System.in);
    int x, y;

    @Override
    public void run() {
        while (true) {
            //Вводим данные для отправки на сервер
            x = in.nextInt();
            y = in.nextInt();

            //Создаем объект Protobuf для отравки на сервер
            setDirection message = setDirection.newBuilder()
                    .setDirectionX(x)
                    .setDirectionY(y).build();

            //Отправляем объект Protobuf на сервер
            Main.client.channel.writeAndFlush(message);
        }
    }
}
