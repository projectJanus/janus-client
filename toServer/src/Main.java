public class Main {
    public static Client client;

    public static void main(String[] args) {
        //В отдельном потоке запускаем Class откуда будем отправлять на сервер объекты Protobuf
        new Thread(new Runnable() {
            @Override
            public void run() {
                Processor processor = new Processor();
                processor.run();
            }
        }).start();

        //Создаем подключение с сервером
        client = new Client();
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

