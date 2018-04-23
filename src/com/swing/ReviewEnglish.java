package com.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ReviewEnglish {

	public static int lineNum=40;
	public static int index;
	public static void main(String[] args) {
		JFrame frame = new JFrame("English");
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		placeComponents(panel);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(100, 100, 100, 700);
		panel.setPreferredSize(new Dimension(scrollPane.getWidth() - 50, scrollPane.getHeight()*2));
		frame.add(scrollPane);
		panel.revalidate(); 
		frame.setVisible(true);
	}

	private static void placeComponents(final JPanel panel) {

		panel.setLayout(null);
		JLabel userLabelWord = new JLabel("µ•¥ :");
		userLabelWord.setBounds(10, 20, 80, 25);
		panel.add(userLabelWord);

		JLabel userLabelTranslation = new JLabel("∑≠“Î:");
		userLabelTranslation.setBounds(210, 20, 80, 25);
		panel.add(userLabelTranslation);

		JLabel userLabelPronunciation = new JLabel("∂¡“Ù:");
		userLabelPronunciation.setBounds(410, 20, 80, 25);
		panel.add(userLabelPronunciation);

		for (int i = 0; i < lineNum; i++) {
			JTextField userTextWord = new JTextField(200);
			userTextWord.setBounds(10, 40 + 30 * i, 165, 25);
			panel.add(userTextWord);
			
			JTextField userTextTranslation = new JTextField(200);
			userTextTranslation.setBounds(210, 40 + 30 * i, 165, 25);
			panel.add(userTextTranslation);

			JTextField userTextPronunciation = new JTextField(200);
			userTextPronunciation.setBounds(410, 40 + 30 * i, 165, 25);
			panel.add(userTextPronunciation);
			
		}

		JButton loginButton = new JButton("OK");
		loginButton.setBounds(710, 30, 80, 25);
		panel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickSave(e,panel);
			}
		});
	}
	
	public static void clickSave(ActionEvent e,final JPanel panel){
		index=3;
		final StringBuffer buffer=new StringBuffer("");
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(index>=lineNum*3+3){
					index=3;
				}
				for(int i=0;i<5;i++){
					for(int j=0;j<3;j++){
						JTextField textField=(JTextField) panel.getComponent(index+j+i*3);
						if(j<2){
							buffer.append(textField.getText()+" : ");
						}else{
							buffer.append(textField.getText()+"\n");
						}
					}
				}
				JOptionPane.showMessageDialog(panel, buffer.toString(), "English", JOptionPane.WARNING_MESSAGE);
				buffer.delete(0, buffer.length());
				index+=15;
			}
		}, 0, 30*60*1000);
	}
}
