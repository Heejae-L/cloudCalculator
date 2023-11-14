package cloudCalculator;

import java.io.*;
import java.net.*;

public class User implements Runnable {
    private Socket socket; 
    private int userID;
    
    //클라이언트마다 객체를 생성 
    public User(Socket socket, int ID) {
        this.socket = socket;
        this.userID = ID;
    }
    //클라이언트에게 받은 메시지를 이용해 계산과 답변을 수행
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            String str;
            while((str = in.readLine()) != null) {
				System.out.println("Client#"+ userID +"로부터 온 메시지 : " + str);
				String[] arr = str.split(" ");
				//포맷에 맞지 않은 입력일 경우 error2 반환
				if(arr.length>3) {
					out.println("error2");
				}
				else {
					int mode=-1,n1,n2;
					double result = 0;
					try {
						//모드 나누기 
						if((arr[0].toLowerCase()).equals("add"))mode=0;
						else if((arr[0].toLowerCase()).equals("sub"))mode=1;
						else if((arr[0].toLowerCase()).equals("mul"))mode=2;
						else if((arr[0].toLowerCase()).equals("div"))mode=3;
						else if((arr[0].toLowerCase()).equals("end")) {
							System.out.println("Client#"+userID+"가 연결을 종료했습니다.");
							break;
						}
						else mode=-1;
						
						//입력이 정상적이라면 연산을 수행한다
						if(mode>-1) {
							n1 = Integer.parseInt(arr[1]);
							n2 = Integer.parseInt(arr[2]);
							//연산이 가능한지 확인(0으로 나누지 않는지) 
							if(Calculator.canCalculate(n1,n2,mode)) {
								result=Calculator.calculate(n1,n2,mode);
								//서버쪽에서 값을 프린트
								System.out.println(">>"+result);//result check
								out.println(String.valueOf(result));//결과값을 클라이언트에게 전송
							}
							//0으로 나누려 할 경우 error1 반환
							else {
								out.println("error1");
							}
						//포맷에 맞지 않은 입력일 경우 error2 반환
						}else {
							out.println("error2");
						}
					
					}catch(NumberFormatException e) {
						out.println("error0");
					}
				}
				out.flush();
			}
            //클라이언트와 연결이 끊어졌을 경우
            System.out.println("Client#"+userID+"와의 연결 종료");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
