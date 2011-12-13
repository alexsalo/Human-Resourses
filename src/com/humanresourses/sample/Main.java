package com.humanresourses.sample;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;		
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

public final class Main {
	private JFrame _mainFrame;	
	private String srpath = "out.dat";
	private HRTableModel _tableModel;
	private JTable _employeesTable;
	private Department _localDep, _resDep;
	private JTextField _filterTextField;
	private JTextField _statusTextField;
	private JMenuBar _menuBar;
	private DeleteEmployeeAction _deleteAction;
	TableRowSorter<HRTableModel> _sorter;
	JPanel _filterPanel;
	
	public Main() throws ClassNotFoundException, IOException {
		initialize();
	}
	
	private void refresh(){
		_tableModel = new HRTableModel(_localDep);
		_sorter = new TableRowSorter<HRTableModel>(_tableModel);
		_employeesTable.setRowSorter(_sorter);
		_employeesTable.setModel(_tableModel);
		_employeesTable.getColumnModel().getColumn(0).setPreferredWidth(200);
	}
	
	private void tableInit(){
		_employeesTable = new JTable();
		refresh();
		_employeesTable.setFillsViewportHeight(true);
		JComboBox<Position> _cb = new JComboBox<>(new DefaultComboBoxModel<>(Position.values()));
		_employeesTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(_cb));
		_employeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_employeesTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {					
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int viewRow = _employeesTable.getSelectedRow();
						if (viewRow<0){
							_statusTextField.setText("");
						} else {
							int modelRow = _employeesTable.convertRowIndexToModel(viewRow);
							_statusTextField.setText(String.format("Selected row in view: %d. " +
									"Selected Row in model: %d.",viewRow,modelRow));
						}
					}
				});
	}
	
	private void newFilter(){
		RowFilter<HRTableModel, Object> _rowFilter = null;
		try{
			_rowFilter = RowFilter.regexFilter(_filterTextField.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e){
			return;
		}
		_sorter.setRowFilter(_rowFilter);
	}
	
	private void makeFilterPanelEdit(){
		_filterPanel = new JPanel(new SpringLayout());
		JLabel _filterTextLabel = new JLabel("Filter Text:", SwingConstants.TRAILING);
		_filterPanel.add(_filterTextLabel);
		_filterTextField = new JTextField();
		_filterTextField.getDocument().addDocumentListener(new DocumentListener() {			
			public void removeUpdate(DocumentEvent arg0) {	newFilter();}			
			public void insertUpdate(DocumentEvent arg0) {	newFilter();}
			public void changedUpdate(DocumentEvent arg0) {	newFilter();}
		});
		_filterTextLabel.setLabelFor(_filterTextLabel);
		_filterPanel.add(_filterTextField);
		JLabel _statusLabel = new JLabel("Status:", SwingConstants.TRAILING);
		_filterPanel.add(_statusLabel);
		_statusTextField = new JTextField();
		_statusLabel.setLabelFor(_statusTextField);
		_filterPanel.add(_statusTextField);
		SpringUtilities.makeCompactGrid(_filterPanel, 2, 2, 6, 6, 6, 6);
	}
	
	@SuppressWarnings("serial")
	class DeleteEmployeeAction extends AbstractAction {
		  public void actionPerformed(ActionEvent actionEvent) {
			  _localDep.remove(_employeesTable.getSelectedRow());
			  refresh();
			  writeDepartmentToFile(srpath);
		  }
	}
	
	private void makeJMenuBar(){
		_menuBar = new JMenuBar();
	    JMenu _menu = new JMenu("Edit");
	    JMenu _menuFile = new JMenu("File");
	    _menuBar.add(_menuFile);
	    _menuBar.add(_menu);
	    
	    JMenuItem _deleteMenuItem = new JMenuItem("Delete employee");
	    _deleteMenuItem.setToolTipText("Delete employee");
	    KeyStroke ctrlD = KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK);
	    _deleteMenuItem.setAccelerator(ctrlD);
	    _deleteAction = new DeleteEmployeeAction();
	    _deleteMenuItem.addActionListener(_deleteAction);
	    
	    JMenuItem _openDepMenuItem = new JMenuItem("Open Department");
	    _openDepMenuItem.setToolTipText("Open Department");
	    KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK);
	    _openDepMenuItem.setAccelerator(ctrlO);
	    
	    _menu.add(_deleteMenuItem);
	    _menuFile.add(_openDepMenuItem);
	}
	
	private void initialize() throws ClassNotFoundException, IOException{
		_mainFrame = new JFrame();
			
		JButton _addEmployeeBtn = new JButton("Add employee");
		JButton _deleteEmployeeBtn = new JButton("Delete employee");
		JButton _restoreLastDepBtn = new JButton("Restore");
		_restoreLastDepBtn.setToolTipText("Restore last loaded department");
			
		_localDep = new Department("Local");;		
		_localDep = readDepartmentFromFile(srpath);
		_resDep = readDepartmentFromFile(srpath);
		makeJMenuBar();
		tableInit();
		makeFilterPanelEdit();
		
		_addEmployeeBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {	
				EmployeeAdder eaWindow = new EmployeeAdder();
				eaWindow.setVisible(true);
				if (eaWindow.isMade()){
				    Employee _emToAdd = eaWindow.getEmployee();	
					System.out.println(_emToAdd.getBriefCase());
					_localDep.addEmployee(_emToAdd);
					refresh();		
					writeDepartmentToFile(srpath);
				}
			}
		});
		_deleteEmployeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_deleteAction.actionPerformed(e);
			}
		});
		_restoreLastDepBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_localDep = _resDep;
				writeDepartmentToFile(srpath);
				_resDep = readDepartmentFromFile(srpath);
				refresh();
			}
		});
		JPanel _managePanel = new JPanel();    
		_managePanel.setLayout(new FlowLayout());
		_managePanel.add(_restoreLastDepBtn);
		_managePanel.add(_addEmployeeBtn);
		_managePanel.add(_deleteEmployeeBtn);
		JScrollPane _jsp = new JScrollPane(_employeesTable);		
		_mainFrame.setBounds(100, 100, 450, 300);
		_mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_mainFrame.setTitle("Human Resourses");
		_mainFrame.setLayout(new BorderLayout());
		_mainFrame.setJMenuBar(_menuBar);
		_mainFrame.getContentPane().add(_jsp, BorderLayout.NORTH);
		_mainFrame.getContentPane().add(_filterPanel, BorderLayout.CENTER);
		_mainFrame.getContentPane().add(_managePanel,BorderLayout.SOUTH);
		_mainFrame.pack();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window._mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void writeDepartmentToFile(String path){
		ObjectOutputStream oos;
		try {
			FileOutputStream fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(_localDep);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private Department readDepartmentFromFile(String path){
		Department dep = new Department(null);
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(fis);
			dep = (Department) oin.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		return dep;
	}
	
}
