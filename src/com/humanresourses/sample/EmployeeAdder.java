package com.humanresourses.sample;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class EmployeeAdder extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final Employee em = new Employee();
	private Boolean made = false;
	private JTextField _EmployeeNameTF = new JTextField(10);
	private JTextField _EmployeeSurnameTF = new JTextField(10);
	private JTextField _EmployeeFathernameTF = new JTextField(10);
	private JTextField _EmployeeSalaryTF = new JTextField(6);
	private JComboBox<Object> _EmployeePositionCB = new JComboBox<>();
	
	public EmployeeAdder(){
		setModal(true);
		JButton addBtn = new JButton("Add employee");
		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		
		//=== customization TextFields
		_EmployeeNameTF = makeTextFieldDefaultView(_EmployeeNameTF, "Name",
				"Please enter employee's name here", Color.gray);
		_EmployeeNameTF.addFocusListener(new MyFocusAdapter(_EmployeeNameTF));
		
		_EmployeeSurnameTF = makeTextFieldDefaultView(_EmployeeSurnameTF, "Surname",
				"Please enter employee's surname here", Color.gray);
		_EmployeeSurnameTF.addFocusListener(new MyFocusAdapter(_EmployeeSurnameTF));
		
		_EmployeeFathernameTF = makeTextFieldDefaultView(_EmployeeFathernameTF, "Fathername",
				"Please enter employee's Fathername here", Color.gray);
		_EmployeeFathernameTF.addFocusListener(new MyFocusAdapter(_EmployeeFathernameTF));
		
		_EmployeeSalaryTF = makeTextFieldDefaultView(_EmployeeSalaryTF, "0",
				"Please enter employee's salary here", Color.gray);
		_EmployeeSalaryTF.addFocusListener(new MyFocusAdapter(_EmployeeSalaryTF));
		
		_EmployeePositionCB.setModel(new DefaultComboBoxModel<Object>(Position.values()));
		
		//=== Customizing of button
		addBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				em.setName(_EmployeeNameTF.getText());
				em.setSurname(_EmployeeSurnameTF.getText());
				em.setFatherName(_EmployeeFathernameTF.getText());			
				em.setPosition((Position) _EmployeePositionCB.getSelectedItem());
				em.setSalary(Integer.parseInt(_EmployeeSalaryTF.getText()));
				made = true;
				EmployeeAdder.this.setVisible(false);
			}
		});
		
		//=== Add the components to the content pane
		content.add(_EmployeeNameTF);
		content.add(_EmployeeSurnameTF);
		content.add(_EmployeeFathernameTF);
		content.add(_EmployeePositionCB);
		content.add(_EmployeeSalaryTF);
		content.add(addBtn);		
		//=== Set window's attributes
		setContentPane(content);
		pack();
		addBtn.requestFocusInWindow();  // focus on button to avoid clearing textedit info on initialising
		setTitle("Add employee");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // center window		
	} //=== End of constructor
	
	public Employee getEmployee(){
		return em;
	}
	
	public Boolean isMade(){
		return made;
	}
	
	private JTextField makeTextFieldDefaultView(JTextField _jtf, String text, String tip, Color foreground){
		_jtf.setToolTipText(tip);
		_jtf.setText(text);
		_jtf.setForeground(foreground);
		return _jtf;
	}
	
	static class MyFocusAdapter extends FocusAdapter{
		private JTextField _jtf;
		private String s;
		
		public MyFocusAdapter(JTextField _jtf) {
			super();
			this._jtf = _jtf;
			s = _jtf.getText();
		}

		public void focusGained(FocusEvent arg0){
			if (_jtf.getClientProperty("init") != Boolean.TRUE){
				_jtf.setText("");
				_jtf.putClientProperty("init", Boolean.FALSE);
			}
			_jtf.setForeground(Color.black);
		}
		public void focusLost(FocusEvent arg0){
			String s2 = _jtf.getText();
			if (s2.equals(s2));
				_jtf.setText(s);
		}
	}
	
}
