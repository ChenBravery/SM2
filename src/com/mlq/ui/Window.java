package com.mlq.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.mlq.sm.SM2Utils;

public class Window extends JFrame{
	StringBuffer str=new StringBuffer();
	// ��ʾ���
	JTextArea textArea; 
	// �ļ�URL������
	JTextField urlField=new JTextField();
	public Window() {
		super("SM2����"); 

		/*// �½���ʾHTML����壬�����������ɱ༭
		textPane = new JEditorPane(); */
		//textPane.setEditable(false);
		
		// ����״̬����ǩ�������������ڵײ�


		// ��ʼ���˵��͹�����
		this.initMenu();
		this.initToolbar();
		textArea=new JTextArea();
		textArea.setTabSize(4);
		textArea.setFont(new Font("�꿬��", Font.BOLD, 16));
		textArea.setLineWrap(true);// �����Զ����й���
		textArea.setWrapStyleWord(true);// ������в����ֹ���
		textArea.setBackground(Color.white);
		
		
		// ��HTML��ʾ�����������ڣ�������ʾ
		this.add(new JScrollPane(textArea),BorderLayout.CENTER);
	}
	private void initToolbar() {
		// ������ַ���ı���
		urlField = new JTextField();
		JToolBar toolbar = new JToolBar();

		// ��ַ��ǩ
		toolbar.add(new JLabel("         ��ַ��"));
		toolbar.add(urlField);
		// �����������������ڵı���
		this.getContentPane().add(toolbar, BorderLayout.NORTH);
	}
	private void initMenu() {
		// �ļ��˵��������������˵���򿪡��˳�
		JMenu fileMenu = new JMenu("�ļ�");
		JMenuItem openMenuItem = new JMenuItem("��");

		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		        jfc.showDialog(new JLabel(), "ѡ��");  
		        File file=jfc.getSelectedFile();  
		        if(file.isDirectory()){  
		        	urlField.setText("�ļ���:"+file.getAbsolutePath());  
		        }else if(file.isFile()){  
		        	urlField.setText("�ļ�:"+file.getAbsolutePath());  
		        }  
		        //�������ݵ�����ͨ��
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(file);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//���������������ѭ����ȡ�ļ������ݡ�
				int length = 0; //����ÿ�ζ�ȡ�����ֽڸ�����
				byte[] buf = new byte[1024]; //�洢��ȡ��������    �������� �ĳ���һ����1024�ı�������Ϊ�������Ĵ���λ��  �����ϻ�������Խ��Ч��Խ��
				try {
					while((length = fileInputStream.read(buf))!=-1){ // read���������ȡ�����ļ���ĩβ����ô�᷵��-1��ʾ��
						//SM2Utils.plainText=(new String(buf,0,length));
						try {
							str.append(new String(buf,0,length));	
							textArea.append(new String(buf,0,length));
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem exitMenuItem = new JMenuItem("�˳�");

		// �����˳���ʱ�˳�Ӧ�ó���
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});

		fileMenu.add(openMenuItem);
		fileMenu.add(exitMenuItem);

		//�����˵�����һ���˵������
		JMenu helpMenu = new JMenu("����");
		JMenu doMenu = new JMenu("����");
		
		JMenuItem pItem = new JMenuItem("����");
		pItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				SM2Utils.plainText=str.toString();
				
				try {
					textArea.append(SM2Utils.encrypte());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		JMenuItem cItem = new JMenuItem("����");
		cItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int  sb_length =str.length();// ȡ���ַ����ĳ���
				str.delete(0,sb_length);    //ɾ���ַ�����0~sb_length-1��������
				String str1="";
				try {
					str1 = SM2Utils.decrypte();
					str.append(str1);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					textArea.append(str1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		doMenu.add(pItem);
		doMenu.add(cItem);
		JMenuItem aboutMenuItem = new JMenuItem("����");
		helpMenu.add(aboutMenuItem);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(doMenu);
		menuBar.add(helpMenu);
		
		
		// ���˵�����ӵ�������
		this.setJMenuBar(menuBar);

	}
	public void exit() {
		// �����Ի�������ȷ�ϣ����ȷ���˳������˳�Ӧ�ó���
		if ((JOptionPane.showConfirmDialog(this, "��ȷ���˳�SM2��������", "�˳�",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)){
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		// �������������������������ڶ����ر�ʱ���˳�Ӧ�ó���

		Window window = new Window(); 
		window.setSize(800, 600);
		// ��ʾ����
		window.setVisible(true); 

	}

}
