import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static class ClientHandler implements Runnable {

        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {

                //封装输入流（接收客户端的流）
                BufferedInputStream bis = new BufferedInputStream(
                        socket.getInputStream());
                DataInputStream dis = new DataInputStream(bis);
                byte[] bytes = new byte[1]; // 一次读取一个byte
                String ret = "";
                while (dis.read(bytes) != -1) {
                    ret += bytesToHexString(bytes) + " ";
                    if (dis.available() == 0) { //一个请求
                        System.out.println(socket.getRemoteSocketAddress() + ":" + ret);
                        ret = "";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("client is over");
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public static void main(String[] args) {

        ServerSocket server = null;
        try {
            server = new ServerSocket(8080);
            while (true) {
                System.out.println("listening...");

                Socket socket = server.accept();
                System.out.println("连接客户端地址：" + socket.getRemoteSocketAddress());
                System.out.println("connected...");
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }

}
