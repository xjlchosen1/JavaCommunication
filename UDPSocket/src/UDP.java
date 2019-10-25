import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(7009);
        byte[] buf = new byte[1024];

        DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
        String data = "#DR*1,2,3,4,5,6,7&b0$";
        DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getLocalHost() , 7009);

        socket.send(sendPacket);

        socket.receive(recvPacket);

        System.out.println("接收到的数据："+ new String(buf,0,recvPacket.getLength())); // getLength() 获取数据包存储了几个字节。

        socket.close();


    }
}
