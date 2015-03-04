package com.lyp.forward;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DicFrame {
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		initUIWindow window = new initUIWindow();
		window.validate();
	}
}
class initUIWindow extends JFrame implements ActionListener{
   
	private static final long serialVersionUID = 1L;
	JFrame topFrame;
	JDesktopPane desktop;
	FileDialog filedialog_save; // 声明文件对话框
	JTextField m_input;
	JTextArea  m_show;
	JButton btn_query,  btn_delete;
	JLabel  label;
	JMenuBar m_bar;   //声明工具栏
	JMenu    m_file, m_edit, m_help  ;
	JMenuItem m_etoc, m_ctoe, m_back, m_quit,
	          m_add, m_modify, m_delete, m_about;
	public initUIWindow(){
		this.setTitle("英汉小字典");
		this.setBackground(Color.BLUE);
		this.setBounds(250, 250, 600, 400);
		this.setVisible(true);

		getContentPane().add(new JScrollPane());
		m_bar = new JMenuBar();
		setJMenuBar(m_bar);
		m_file = new  JMenu("文件");
		m_edit = new  JMenu("编辑");
		m_help = new  JMenu("帮助");
	
		m_bar.add(m_file);
		m_bar.add(m_edit);
		m_bar.add(m_help);
		
		//二级菜单初始化
		m_etoc = new  JMenuItem("英译汉");
		m_ctoe = new JMenuItem("汉译英");
		m_back = new JMenuItem("备份");
		 
        m_add  = new JMenuItem("添加词汇");
        m_modify = new JMenuItem("修改词汇");
        m_delete = new JMenuItem("删除词汇"); 
        
        m_about = new JMenuItem("关于");
    	m_quit  = new JMenuItem("退出");
        //二级菜单添加到一级菜单
        m_file.add(m_etoc);
        m_file.add(m_ctoe);
        m_file.add(m_back);
        
        m_edit.add(m_add);
        m_edit.add(m_modify);
        m_edit.add(m_delete);
        
        m_help.add(m_about);
        m_help.add(m_quit);
        
        m_file.addActionListener(this);
        m_edit.addActionListener(this); 
        m_help.addActionListener(this); 
        m_etoc.addActionListener(this);
        m_ctoe.addActionListener(this);
        m_back.addActionListener(this);
        m_quit.addActionListener(this);
        m_add.addActionListener(this);
        m_modify.addActionListener(this);
        m_delete.addActionListener(this);
        m_about.addActionListener(this);
        
        label =new JLabel(ConstantData.EnglishToChinese);
        m_input = new JTextField(18);
        m_input.setBackground(Color.WHITE);
        m_input.setFont(new Font("",15,15));
    	m_show = new JTextArea(8,15); 
    	m_show.setFont(new Font("",20,20));
    	m_show.setEditable(false);

    	btn_query = new JButton("查询");
    	btn_delete  = new JButton("清除");
    	btn_query.addActionListener(this);
    	btn_delete.addActionListener(this);
    	
    	JPanel panel = new JPanel();
    	panel.add(label);
    	panel.add(m_input);
    	panel.add(btn_query);
    	panel.add(btn_delete);
    	
    	this.add(panel,"North");
    	this.add(new JScrollPane(m_show),"Center");
    	 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == m_etoc){//如果点击二级菜单英译汉
			label.setText(ConstantData.EnglishToChinese);
		}else if (e.getSource() == m_ctoe){//如果点击二级菜单汉译英
			label.setText(ConstantData.ChineseToEnglish);
		}else if (e.getSource() == m_back){//如果点击二级菜单备份
			String firstPath = System.getProperty("user.dir");
			String url = firstPath+"\\bin\\com\\lyp\\forward\\english.mdb";
			File fromfile = new File(url);
			
			FileInputStream fis = null;
			filedialog_save = new FileDialog(this, "保存文件对话框", FileDialog.SAVE);
			filedialog_save.setVisible(true);
			try {
				fis = new FileInputStream(fromfile);
				int bytesRead; // 定义变量来存储输入流中读取出来的文件
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				File tofile = new File(filedialog_save.getDirectory(),
						filedialog_save.getFile());
				FileOutputStream fos = new FileOutputStream(tofile);
				while ((bytesRead = fis.read(buf)) != -1) {
					fos.write(buf, 0, bytesRead);
				}
				fos.flush();
				fos.close();
				fis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if (e.getSource() == m_add){ //如果点击二级菜单添加词汇
			UpdateDatebase jf = new UpdateDatebase(ConstantData.AddWord);	
			jf.setVisible(true);
			
		}else if (e.getSource() == m_modify){//如果点击二级菜单修改词汇
			UpdateDatebase jf = new UpdateDatebase(ConstantData.ModifyWord);
			jf.setVisible(true);
			
		}else if (e.getSource() == m_delete){//如果点击二级菜单删除词汇
			UpdateDatebase jf = new UpdateDatebase(ConstantData.DeleteWord);
			jf.setVisible(true);
			
		}else if (e.getSource() == m_about){//如果点击二级菜单关于

			JOptionPane.showMessageDialog(this, ConstantData.SoftWareAbout, "版权",
					JOptionPane.INFORMATION_MESSAGE);
			
			
		}else if (e.getSource() == m_quit){//如果点击二级菜单退出
			System.exit(0);
		}else if(e.getSource() == btn_delete){
			m_show.setText("");
			m_input.setText("");
		}else if(e.getSource() == btn_query){
			String query_info = m_input.getText().trim();
			m_show.setText("");
			Connection conn = null;
			try {
				 conn = DatabaseConn.GetConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(conn!=null){
				Statement stm;
				String cinfo,einfo;
				try {
					stm = conn.createStatement();
					if(label.getText().equals(ConstantData.EnglishToChinese)){
						ResultSet rs =  stm.executeQuery(ConstantData.SQLGetAllInfo);
						while(rs.next()){
							einfo = rs.getString("单词");
							cinfo = rs.getString("解释");
							if(query_info.equals(einfo)){
								m_show.setText(cinfo);
							}
						}
						rs.close();
					}else if(label.getText().equals(ConstantData.ChineseToEnglish)){
						m_show.setText("");
						ResultSet rs =  stm.executeQuery("SELECT * FROM 表1 WHERE 解释 LIKE '%"
								+ m_input.getText().trim() + "%'");
						while(rs.next()){
							einfo = rs.getString("单词");
							cinfo = rs.getString("解释");
							m_show.append(einfo+"  "+cinfo+"\n");
						}
						rs.close();
					}
					stm.close();
					conn.close(); // 关闭数据库
					if (m_show.getText().equals("")) {
						JOptionPane.showMessageDialog(this, "查无此单词！", "警告",
								JOptionPane.WARNING_MESSAGE);
						m_show.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}			
		}
	}	
}