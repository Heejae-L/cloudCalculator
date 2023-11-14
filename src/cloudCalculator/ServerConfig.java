package cloudCalculator;

import java.io.*;

public class ServerConfig { 
    private String serverIP; 
    private int port;

    public ServerConfig() {
        this.serverIP = "localhost"; // Default server IP.
        this.port = 4567; // Default port number
        readConfigFile();
    }

    private void readConfigFile() {
        File file = new File("server_info.dat");
        

        //파일이 있는지 확인
        if (file.exists()) {
        	//파일이 있다면 파일을 열어 서버 IP와 port 번호를 저장
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        if (parts[0].equalsIgnoreCase("ServerIP")) {
                            this.serverIP = parts[1];
                        } else if (parts[0].equalsIgnoreCase("Port")) {
                            this.port = Integer.parseInt(parts[1]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //서버 IP를 반환한다 
    public String getServerIP() {
        return serverIP;
    }
    //서버의 port를 반환한다
    public int getPort() {
        return port;
    }
}
