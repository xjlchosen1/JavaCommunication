import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Socket16{
    public static void main(String[] args) {
        Socket socket = null;
        try{
            System.out.println("connecting...");
            socket = new Socket("192.168.1.25", 8080);
            System.out.println("connection success");

            Scanner in = new Scanner(System.in);
            String str = in.nextLine();
            byte[] sendbytes =hexStringToByteArray(str);
            OutputStream os = socket.getOutputStream();
            os.write(sendbytes);
            os.flush();

            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            byte[] recvbyte = new byte[1];
            String ret = "";
            while (dis.read(recvbyte) != -1){
                ret += bytesToHexString(recvbyte) + " ";
                if (dis.available() == 0){
                    System.out.println(socket.getRemoteSocketAddress() + ":" + ret);
                    ret = "";
                }
            }
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (socket != null){
                try {
                    socket.close();
                }catch (Exception e){

                }
            }
        }
    }
    public static byte[] hexStringToByteArray(String hexString){
        hexString = hexString.replaceAll(" ","");

        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len/2];

        while (index < len){
            String sub = hexString.substring(index, index + 2);
            bytes[index/2] = (byte)Integer.parseInt(sub,16);

            index += 2;
        }

        return bytes;
    }

    public static String bytesToHexString(byte[] bytes){
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes){
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex = " ";
        }
        return result;
    }
}