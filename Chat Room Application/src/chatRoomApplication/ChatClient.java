package chatRoomApplication;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class ChatClient {
	
	BufferedReader in;
	PrintWriter out;
	JFrame frame = new JFrame("Chat App");
	JTextField textField = new JTextField(60);
	JTextArea messageArea = new JTextArea(10,60);
    
	public ChatClient() {
		textField.setEditable(false);
		messageArea.setEditable(false);
		frame.getContentPane().add(textField, "North");
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		frame.pack();
		
		textField.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				out.println(textField.getText());
				textField.setText("");
			}
		});
		
	}
	
	//getting server address
	private String getServerAddress() {
		return JOptionPane.showInputDialog(
				frame,
				"Enter the IP address of the server: ",
				"Welcome to Chat App Developed By Madhawa Kumarathunga"
		);
	}
	
	private String getName() {
		return JOptionPane.showInputDialog(
				frame,
				"Choose a screen name: ",
				"Screen name selection",
				JOptionPane.PLAIN_MESSAGE
		);
	}
	
	private void run() throws IOException{
		String serverAddress = getServerAddress();
		Socket socket = new Socket(serverAddress, 9001);
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out = new PrintWriter(socket.getOutputStream(), true);
		
		while(true) {
			String line = in.readLine();
			
			if(line.startsWith("SUBMITNAME")) {
				out.println(getName());
			} else if(line.startsWith("NAMEACCEPTED")) {
				textField.setEditable(true);
			} else if(line.startsWith("MESSAGE")) {
				messageArea.append(line.substring(8) + "\n");
			}
		}
	}
	
	public static void main(String args[]) throws Exception{
		ChatClient client = new ChatClient();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
		client.run();
	}
}





//package chatRoomApplication;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.net.Socket;
//import javax.swing.*;
//
//public class ChatClient {
//
//    BufferedReader in;
//    PrintWriter out;
//    JFrame frame = new JFrame("Chat App");
//    JTextField textField = new JTextField(60);
//    JTextArea messageArea = new JTextArea(10, 60);
//
//    public ChatClient() {
//        textField.setEditable(false);
//        messageArea.setEditable(false);
//        frame.getContentPane().add(textField, "North");
//        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
//        frame.pack();
//
//        textField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                out.println(textField.getText());
//                textField.setText("");
//            }
//        });
//    }
//
//    // Getting server address
//    private String getServerAddress() {
//        JPanel panel = new JPanel();
//        JLabel label = new JLabel("Enter the IP address of the server: ");
//        JTextField textField = new JTextField(20);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.add(label);
//        panel.add(Box.createVerticalStrut(10));  // a spacer
//        panel.add(textField);
//
//        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
//        JDialog dialog = optionPane.createDialog(frame, "Welcome to Chat App Developed By Madhawa Kumarathunga");
//        dialog.setPreferredSize(new java.awt.Dimension(400, 150)); // Set dimension size of the panel window
//        dialog.pack();
//        dialog.setVisible(true);
//
//        int result = (int) optionPane.getValue();
//        if (result == JOptionPane.OK_OPTION) {
//            return textField.getText();
//        } else {
//            return null;
//        }
//    }
//
//    private String getName() {
//        return JOptionPane.showInputDialog(
//                frame,
//                "Choose a screen name: ",
//                "Screen name selection",
//                JOptionPane.PLAIN_MESSAGE
//        );
//    }
//
//    private void run() throws IOException {
//        String serverAddress = getServerAddress();
//        if (serverAddress == null) {
//            System.exit(0);
//        }
//        Socket socket = new Socket(serverAddress, 9001);
//
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(), true);
//
//        while (true) {
//            String line = in.readLine();
//
//            if (line.startsWith("SUBMITNAME")) {
//                out.println(getName());
//            } else if (line.startsWith("NAMEACCEPTED")) {
//                textField.setEditable(true);
//            } else if (line.startsWith("MESSAGE")) {
//                messageArea.append(line.substring(8) + "\n");
//            }
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        ChatClient client = new ChatClient();
//        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        client.frame.setVisible(true);
//        client.run();
//    }
//}

