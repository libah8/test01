package socket4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class EchoServer {
	public static void main(String args[]){
		ServerSocket server = null;
		try {
			server = new ServerSocket(9001);
			System.out.println("Ŭ���̾�Ʈ�� ������ �����");
				Socket socket = server.accept();
				
				new EchoThread(socket).start();
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}finally{
					try {
						if(server != null) server.close();
					} catch (IOException e) {
						e.printStackTrace();
				}
		}
	}
}

class EchoThread extends Thread{
	Socket socket;
	Map<String, OutputStream> map = new HashMap();
	InputStream in; 
	OutputStream out;
	BufferedReader br; 
	PrintWriter pw;
	
		
	public EchoThread() {	}

	public EchoThread(Socket socket) {
		super();
		this.socket = socket;
		
	}

	@Override
	public void run(){
		
		InetAddress address = socket.getInetAddress();
		System.out.println(address.getHostAddress()+" �κ��� �����߽��ϴ�.");

		try {
				in = socket.getInputStream();
				out = socket.getOutputStream();
				br = new BufferedReader(new InputStreamReader(in));
				pw = new PrintWriter(new OutputStreamWriter(out));
			
			//DataInputStream di = new DataInputStream(in); primitiveŸ������ .
			
			String message = null;
			while((message = br.readLine())!= null){
				//System.out.println("Ŭ���̾�Ʈ --> ������ : "+message);
				pw.println(message);
				pw.flush();
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			br.close();
			pw.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


