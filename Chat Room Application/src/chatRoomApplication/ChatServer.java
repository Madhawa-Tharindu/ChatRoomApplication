package chatRoomApplication;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
		
		//specify the port number
		private static final int PORT = 9001;
		
		// use HashSet to get unique names
		private static HashSet<String> names = new HashSet<String>();
		
		private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
		
		public static void main(String args[]) throws Exception{
			
			System.out.println("The chat server is running");
			ServerSocket listner = new ServerSocket(PORT);
			
			try {
				while(true) {
					Socket socket = listner.accept();
					Thread handlerThread = new Thread(new Handler(socket));
					
				}
			} finally {
				listner.close();
			}
			
		}
		
		// implement the handler class for using thread
        private static class Handler implements Runnable{
        	
        	private String name;
        	private Socket socket;
        	private BufferedReader in;
        	private PrintWriter out;
        	
        	public Handler(Socket socket) {
        		this.socket = socket;
        	}
        	
        	public void run() {
        		
        		//
        		try {
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	        		out = new PrintWriter(socket.getOutputStream(), true);
	        		
	        		while(true) {
	        			out.println("SUBMITNAME");
	        			name = in.readLine();
	        			
	        			if (name == null) {
	        				return;
	        			}
	        			if(!names.contains(names)) {
	        				names.add(name);
	        				break;
	        			}
	        		}
	        		
	        		out.println("NAMEACCEPTED");
	        		writers.add(out);
	        		
	        		while(true) {
	        			String input = in.readLine();
	        			if(input == null) {
	        				return;
	        			}
	        			for (PrintWriter writer: writers) {
	        				writer.println("MESSAGE "+ name + ": " + input);
	        			}
	        		}
	        		
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(e);
				} finally {
					if(names != null) {
						names.remove(name);
					}
					if (out != null) {
						writers.remove(out);
					}
					try {
						socket.close();
					}catch (IOException e) {
						System.out.println(e);
					}
				}
        		
        	}
        }
}
