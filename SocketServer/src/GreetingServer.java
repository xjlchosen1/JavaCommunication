import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
                /*
                //DataInputStream in = new DataInputStream(server.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));

                //DataOutputStream out = new DataOutputStream(server.getOutputStream());
                //PrintWriter out = new PrintWriter(server.getOutputStream());
                OutputStream outToClient = server.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outToClient);
                PrintWriter out = new PrintWriter(osw);

                System.out.println(in.readLine());
                out.println("This is a reply! 这是一条回复！");
                server.close();

                 */
                BufferedReader br=new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter ps=new PrintWriter(server.getOutputStream(),true);
                String b=br.readLine();
                System.out.println("客户端发送的消息：");
                System.out.println(b);
                System.out.println("服务器准备返回信息！");
                //System.out.println("返回信息时：我是服务器！");
                try {
                    sleep(10000);
                }catch (InterruptedException i){

                }

                ps.println("你好，我是服务器!");
                //ps.print("我是服务器! \n");
                //ps.flush();
                System.out.println("----------------------");

            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String [] args)
    {
        int port = 10005;
        try
        {
            Thread t = new GreetingServer(port);
            t.run();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}