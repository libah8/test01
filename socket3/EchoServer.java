package socket3;

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
			System.out.println("클라이언트의 접속을 대기중");
			while(true){
				Socket socket = server.accept();
				new EchoThread(socket).start();
				}
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
	InputStream in; 
	OutputStream out;
	BufferedReader br; 
	PrintWriter pw;
	Map<InetAddress, String> map = new HashMap<>();
	
	public EchoThread() {	}

	public EchoThread(Socket socket) {
		super();
		this.socket = socket;
		
	}

	@Override
	public void run(){
		
		InetAddress address = socket.getInetAddress();
		System.out.println(address.getHostAddress()+" 로부터 접속했습니다.");

		try {
				in = socket.getInputStream();
				out = socket.getOutputStream();
				br = new BufferedReader(new InputStreamReader(in));
				pw = new PrintWriter(new OutputStreamWriter(out));
			
			//DataInputStream di = new DataInputStream(in); primitive타입으로 .
			
			String message = null;
			while((message = br.readLine())!= null){
				//System.out.println("클라이언트 --> 서버로 : "+message);
				pw.println(message);
				pw.flush();
				map.put(socket.getInetAddress(), pw.toString());
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


