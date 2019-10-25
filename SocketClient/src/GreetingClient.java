import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class GreetingClient{
    public static void main(String[] args) {
        String servername = "192.168.10.200";
        int port = 10005;
        try{
            System.out.println("连接到："+servername+"，端口号："+port);
            Socket client = new Socket(servername,port);
            System.out.println("远程主机地址："+client.getRemoteSocketAddress());

            PrintWriter ps = new PrintWriter(client.getOutputStream(),true);
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();

            ps.println(message);

            System.out.println("服务器返回消息：");

            String apply = null;
            while(!((apply = br.readLine()) == null)){
                System.out.println(apply);
            }


        }catch (IOException e){
            e.printStackTrace();
        }

    }
}