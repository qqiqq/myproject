package ch01;

import util.Debug;
import util.Tools;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;


import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class FileDownloaderApp {
    public static void main(String[] args){
        Thread downloaderThread = null;
        for(String url : args){
            downloaderThread = new Thread(new FileDownloader(url));
            downloaderThread.start();
        }
    }

    static class FileDownloader implements Runnable{
        private  static String fileURL;

        public FileDownloader(String fileURL){
            this.fileURL = fileURL;
        }

        public void run(){
            Debug.info("Downloading form " + fileURL);
            String fileBaseName = fileURL.substring(fileURL.lastIndexOf('/') + 1);
            try {
                URL url = new URL(fileURL);
                String localFileName = System.getProperty("java.io.tmpdir" + fileBaseName);
                Debug.info("Saving to: " + localFileName);
                downloadFile(url, new FileOutputStream(localFileName), 1024);
            } catch (Exception e){
                e.printStackTrace();
            }
            Debug.info("Done downloading from " + fileURL);
        }

        private static void downloadFile(URL url, OutputStream outputStream, int bufSize)
                throws MalformedURLException, IOException {
            final HttpURLConnection httpConn = (HttpURLConnection) url
                    .openConnection();
            httpConn.setRequestMethod("GET");
            ReadableByteChannel inChannel = null;
            WritableByteChannel outChannel = null;
            try {
                // 获取HTTP响应码
                int responseCode = httpConn.getResponseCode();
                // HTTP响应非正常:响应码不为2开头
                if (2 != responseCode / 100) {
                    throw new IOException("Error: HTTP " + responseCode);
                }

                if (0 == httpConn.getContentLength()) {
                    Debug.info("Nothing to be downloaded " + fileURL);
                    return;
                }
                inChannel = Channels
                        .newChannel(new BufferedInputStream(httpConn.getInputStream()));
                outChannel = Channels
                        .newChannel(new BufferedOutputStream(outputStream));
                ByteBuffer buf = ByteBuffer.allocate(bufSize);
                while (-1 != inChannel.read(buf)) {
                    buf.flip();
                    outChannel.write(buf);
                    buf.clear();
                }
            } finally {
                // 关闭指定的Channel以及HttpURLConnection
                Tools.silentClose(inChannel, outChannel);
                httpConn.disconnect();
            }
        }
    }
}
