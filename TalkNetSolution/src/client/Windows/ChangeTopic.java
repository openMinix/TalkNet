package client.Windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import client.Entities.*;

public class ChangeTopic extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	
	ConferenceManager cm;
	final ConferenceFrame cf;
	
	ChangeTopic ct;
	
	public ChangeTopic(ConferenceManager cm, final ConferenceFrame cf) {
		
		this.cm = cm;
		this.cf = cf;
		ct = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 140);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter new topic:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 12, 249, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(79, 39, 117, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(79, 70, 117, 25);
		contentPane.add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
						
				ct.setTopic(ct.getTopic());
				
				cf.setTopic(ct.getTopic());
				
				ct.dispose();
			}
        	
        });
		
		setVisible(true);
	}
	
	public String getTopic(){
		return textField.getText();
	}
	
	public void setTopic(String topic) {
		cm.setTopic(topic);
	}

}
