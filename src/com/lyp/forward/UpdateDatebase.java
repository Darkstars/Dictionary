package com.lyp.forward;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateDatebase  extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btn_ack;
	JButton btn_cancel;
	JTextField ename , cname;
	JLabel label1,label2;
	Container container;
	String title_info;
    public UpdateDatebase(String str){    	
      	title_info = str;
      	container = this.getContentPane();
     	if(str.equals(ConstantData.AddWord)){
     		initAdd();		
    	}else if(str.equals(ConstantData.DeleteWord)){
    		initDelete();	
    	}else if(str.equals(ConstantData.ModifyWord)){
    		initModify();
    	}
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn_ack){
			 Connection conn = null;
			 boolean flag = true;
			try {
				conn = DatabaseConn.GetConnection();
				container = this.getContentPane();
				Statement stm = conn.createStatement();
				String einfo, cinfo;
				if(conn!=null){
					if(title_info.equals(ConstantData.AddWord))
					{
						String ename_text = ename.getText().trim();
						String cname_text = cname.getText().trim();
						if(ename_text.equals("")||cname_text.equals("")){
							JOptionPane.showMessageDialog(this, "������������Ϣ��", "��ʾ",JOptionPane.WARNING_MESSAGE);
						}else if(!ename_text.equals("")&&!cname_text.equals("")){
							
							ResultSet rs = stm.executeQuery(ConstantData.SQLGetAllInfo);
							while(rs.next()){
								einfo = rs.getString(ConstantData.WordInfo);
								cinfo = rs.getString(ConstantData.ChineseInfo);
								if(einfo.equals(ename_text)){
									flag = false;
									JOptionPane.showMessageDialog(this, "�ôʻ��Ѿ����ڣ�","��ʾ",JOptionPane.WARNING_MESSAGE);
									break;
								}
							}
							if(flag==true){
								String sql = "INSERT INTO ��1 VALUES ("+"'"+ ename_text +"'"+","+"'"+cname_text+"'"+")";
								stm.executeUpdate(sql);
								JOptionPane.showMessageDialog(this,"��ӳɹ���","��ϲ",JOptionPane.WARNING_MESSAGE);
								this.dispose();
							}
						}
						
					}else if(title_info.equals(ConstantData.ModifyWord)){
						if(ename.getText().equals("")||cname.getText().equals("")){
							JOptionPane.showMessageDialog(this, "������������Ϣ��", "��ʾ",JOptionPane.WARNING_MESSAGE);
						}else if(!ename.getText().trim().equals("")&&!cname.getText().trim().equals("")){
							String ename_text = ename.getText().trim();
							String cname_text = cname.getText().trim();
							if(ename_text.equals("")||cname_text.equals("")){
								JOptionPane.showMessageDialog(this, "������������Ϣ��", "��ʾ",JOptionPane.WARNING_MESSAGE);
							}else if(!ename_text.equals("")&&!cname_text.equals("")){
								
								ResultSet rs = stm.executeQuery(ConstantData.SQLGetAllInfo);
								while(rs.next()){
									einfo = rs.getString(ConstantData.WordInfo);
									cinfo = rs.getString(ConstantData.ChineseInfo);
									if(einfo.equals(ename_text)){
										flag = false;
										break;
									}
								}
								if(flag==false){
									String sql = "UPDATE ��1 SET ����= "+"'"+cname_text +"'"+ " WHERE ����="+"'"+ename_text+"'";
									stm.executeUpdate(sql);
									JOptionPane.showMessageDialog(this,"�޸ĳɹ���","��ϲ",JOptionPane.WARNING_MESSAGE);
									this.dispose();
								}else{
									JOptionPane.showMessageDialog(this,"�ôʻ㲻����","����",JOptionPane.WARNING_MESSAGE);
								}
								}
						}
					}else if(title_info.equals(ConstantData.DeleteWord)){
						if(ename.getText().equals("")){
							JOptionPane.showMessageDialog(this, "������������Ϣ��", "��ʾ",JOptionPane.WARNING_MESSAGE);
						}else if(!ename.getText().trim().equals("")){
							String ename_text = ename.getText().trim();
							if(ename_text.equals("")){
								JOptionPane.showMessageDialog(this, "������������Ϣ��", "��ʾ",JOptionPane.WARNING_MESSAGE);
							}else if(!ename_text.equals("")){
								
								ResultSet rs = stm.executeQuery(ConstantData.SQLGetAllInfo);
								while(rs.next()){
									einfo = rs.getString(ConstantData.WordInfo);
									cinfo = rs.getString(ConstantData.ChineseInfo);
									if(einfo.equals(ename_text)){
										flag = false;
										break;
									}
								}
								if(flag==false){
									String sql = "DELETE FROM ��1 WHERE ����="+"'"+ename_text+"'";
									stm.executeUpdate(sql);
									JOptionPane.showMessageDialog(this,"ɾ���ɹ���","��ϲ",JOptionPane.WARNING_MESSAGE);
									this.dispose();
								}else{
									JOptionPane.showMessageDialog(this,"�ôʻ㲻����","����",JOptionPane.WARNING_MESSAGE);
								}
								}
						
						}
					}
				}else{
					 JOptionPane.showMessageDialog(this, "���ݿ�����ʧ��,�����³��ԣ�", "����",JOptionPane.WARNING_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		}else if(e.getSource()==btn_cancel){
			this.dispose();  //�رմ���
		}
	}
	void initAdd(){
		this.setTitle("��Ӵʻ�");
		this.setBounds(380, 340, 350, 200);
		JPanel pane = new JPanel();
		label1 = new JLabel(ConstantData.AddWordEnglish);
		label2 = new JLabel(ConstantData.AddWordChinese);
		ename = new JTextField(20);
		cname = new JTextField(20);
		btn_ack = new JButton("ȷ��");
		btn_cancel = new JButton("ȡ��");
		JPanel pane2 = new JPanel();
		pane.add(label1);
		pane.add(ename);
		pane.add(label2);
		pane.add(cname);
		pane2.add(btn_ack);
		pane2.add(btn_cancel);
		pane.add(pane2);
		container.add(pane);
		btn_ack.addActionListener(this);
		btn_cancel.addActionListener(this);  
	}
	void initDelete(){
		this.setTitle("ɾ���ʻ�"); 	
		this.setBounds(380, 340, 300, 150);

		JPanel pane = new JPanel();
		label1 = new JLabel(ConstantData.DeleteWordEnglish);
		ename = new JTextField(20);
		btn_ack = new JButton("ȷ��");
		btn_cancel = new JButton("ȡ��");
		JPanel pane2 = new JPanel();
		pane.add(label1);
		pane.add(ename);
		pane2.add(btn_ack);
		pane2.add(btn_cancel);
		pane.add(pane2);
		container.add(pane);
		btn_ack.addActionListener(this);
		btn_cancel.addActionListener(this);  
		
	}
	void initModify(){
		this.setTitle("�޸Ĵʻ�");
		this.setBounds(380, 340, 350, 200);
		container.setComponentOrientation(getComponentOrientation().LEFT_TO_RIGHT);
		JPanel pane = new JPanel();
		label1 = new JLabel(ConstantData.ModifyWordEnglish);
		label2 = new JLabel(ConstantData.ModifyWordChinese);
		ename = new JTextField(20);
		cname = new JTextField(20);
		btn_ack = new JButton("ȷ��");
		btn_cancel = new JButton("ȡ��");
		JPanel pane2 = new JPanel();
		pane.add(label1);
		pane.add(ename);
		pane.add(label2);
		pane.add(cname);
		pane2.add(btn_ack);
		pane2.add(btn_cancel);
		pane.add(pane2);
		container.add(pane);
		btn_ack.addActionListener(this);
		btn_cancel.addActionListener(this);	
	}
}
