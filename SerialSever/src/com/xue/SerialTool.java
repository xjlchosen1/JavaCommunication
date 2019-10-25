package com.xue;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialTool
{
    private static SerialTool st=null;
    static{
        if(st==null)
            st=new SerialTool();
    }
    private SerialTool(){}
    public static SerialTool getSerialTool() {
        if (st == null) {
            st = new SerialTool();
        }
        return st;
    }
    /*
    public static final ArrayList<String> findPort()
    {
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<>();
        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

     */
    public static final SerialPort openPort(String portName, int baudrate) throws Exception {

        try {
            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            //判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                try {
                    //设置一下串口的波特率等参数
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                }
                System.out.println("Open " + portName + " sucessfully !");
                return serialPort;

            }
            else {
                //不是串口
                return null;
            }
        } catch (NoSuchPortException e1) {
            e1.printStackTrace();
        }

        return null;

    }

    /*
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            System.out.println("Close " + serialPort + " sucessfully !");
            serialPort = null;
        }
    }

     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {

        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static byte[] read(SerialPort serialPort)  {

        InputStream in = null;
        byte[] bytes = null;
        try {
            in = serialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长度

            while (bufflenth != 0) {
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
                //bufflenth--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

}
