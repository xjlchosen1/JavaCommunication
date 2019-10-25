import com.xue.SerialTool;

import java.io.*;
import java.util.TooManyListenersException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Main {
    static SerialPort sp=null;
    public static void main(String[] args) {
        try{

            sp=SerialTool.openPort("COM11", 9600);
            if(sp!=null)
            {

                System.out.println("请输入要发送的内容：");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String str;
                try {
                    str = br.readLine();
                    SerialTool.sendToPort(sp, str.getBytes());//向串口写入数据
                }catch (IOException io){

                }

                try {
                    //给串口添加事件监听
                    sp.addEventListener(new SerialPortEventListener() {

                        @Override
                        public void serialEvent(SerialPortEvent arg0) {
                            if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知

                                byte[] bytes = SerialTool.read(sp);//从串口读取数据
                                String data=new String(bytes);
                                System.out.println("收到的数据："+data);

                            }
                        }
                        /*
                        public void serialEvent(SerialPortEvent arg0) {
                            if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//数据通知
                                byte[] bytes = SerialTool.read(sp);//从串口读取数据
                                //System.out.println("收到的数据长度："+bytes.length);
                                String data=new String(bytes);
                                System.out.println("收到的数据："+data);
                                SerialTool.sendToPort(sp, data.getBytes());//向串口写入数据
                            }
                        }

                         */
                    });
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                }
                sp.notifyOnDataAvailable(true);//串口有数据监听
                sp.notifyOnBreakInterrupt(true);//中断事件监听
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }



        //SerialTool.closePort(sp);关闭端口通信

    }
}
