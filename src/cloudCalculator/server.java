package cloudCalculator;

import java.io.*;
import java.net.*;

public class server{
	public static void main(String[] args)throws IOException{
		Socket socket;
		ServerSocket server_socket = null;
		int count=1;
		
		try {
			server_socket = new ServerSocket(4567);//서버를 연다
		}catch(IOException e) {System.out.println("해당 포트는 사용중입니다.");
		}
		try {
			System.out.println("서버가 열렸습니다.");
			
			while (true) {
                socket = server_socket.accept();//accept를 이용해 클라이언트의 연결을 받는다
                System.out.println("새로운 클라이언트가 연결되었습니다.#"+ count);
                // 클라이언트마다 스레드를 생성한다
                Thread userThread = new Thread(new User(socket, count));
                userThread.start();
                count++;//클라이언트의 수를 저장한다
            }
			
		}catch(IOException e) {
			  e.printStackTrace();
		}
	}
}
