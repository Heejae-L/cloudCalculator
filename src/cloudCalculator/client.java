package cloudCalculator; 

import java.io.*;
import java.net.*;

public class client {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader server_in = null;
		BufferedReader my_in = null;
		PrintWriter out = null;
		InetAddress ia = null;
		
		//server_info.dat파일을 읽고 서버 정보(IP, port)를 반환하는 클래스 ServerConfig
		ServerConfig config = new ServerConfig();
        String serverIP = config.getServerIP();
        int port = config.getPort();
        
		try {
			//위에서 받은 서버 정보를 이용해 연결을 생성한다
			ia = InetAddress.getByName(serverIP);
			socket = new Socket(ia, port);
			
			//입출력을 위한 스트림을 생성한다
			server_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			my_in = new BufferedReader(new InputStreamReader(System.in));
			
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			System.out.println(socket.toString());

			while(true) {
				//계산을 한 번 더 진행할 것인지 확인 
				System.out.println("계산을 실행하겠습니까?Yes/No ");
				String repeat = my_in.readLine().toLowerCase();
				
				if(repeat.equals("y")||repeat.equals("yes")) {
					System.out.print("계산할 내용을 입력하세요(예: add 5 3) : ");
					String data = my_in.readLine(); // 키보드로부터 입력받음
					out.println(data);
					out.flush();
					
					String str2 = server_in.readLine(); // 서버로부터 되돌아오는 데이터를 읽어들임
					//에러 메시지가 반환됐는지, 그 내용이 무엇인지 확인
					if(str2.length()>5) {
						if((str2.substring(0,5).toLowerCase()).equals("error")) {
							if(str2.substring(5,6).equals("1"))System.out.println(str2+" : divided by zero");
							else if(str2.substring(5,6).equals("2"))System.out.println(str2+" : Invalid input");
							else System.out.println(str2 + " : unknown error");
						}
						//에러가 아니라면 결과값을 출력 
						else {System.out.println("서버로부터 받은 결과 : " + str2+"\n");}
					}
					//에러가 없다면 결과값을 출력 
					else {System.out.println("서버로부터 받은 결과 : " + str2+"\n");}
					
				}
				//반복하지 않으면 서버에 종료 메시지를 전달한다
				else {
					out.println("end");
					out.flush();
					break;
				}
			}
			socket.close();
			System.out.println("종료");
		}catch(IOException e) {
			
		}
	}
}
