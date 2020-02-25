package ch02;

public class AtomicityExample {
    private HostInfo hostInfo;

    public void updateHostInfo(String ip,int port){
        hostInfo.setIP(ip);
        hostInfo.setPort(port);
    }

    public void connectToHost(){
        String ip = hostInfo.getIP();
        int port = hostInfo.getPort();
        connectToHost(ip,port);
    }
    private void connectToHost(String ip,int prot){
    }

    public static class HostInfo{
        private String ip;
        private int port;
        public HostInfo(String ip,int port){
        }
        public String getIP(){
            return ip;
        }
        public int getPort(){
            return port;
        }
        public void setIP(String ip){
            this.ip = ip;
        }
        public void setPort(int port){
            this.port = port;
        }
    }
}
