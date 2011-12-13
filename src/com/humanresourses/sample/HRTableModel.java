package com.humanresourses.sample;

import javax.swing.table.AbstractTableModel;

public class HRTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -457007535025637612L;
	private Department _localDep;
	private int _size;
	
	public HRTableModel(Department department){
		this._localDep = department;
		this._size = department.size();
	}

	public Class<?> getColumnClass(int ColumnIndex){
		switch (ColumnIndex){
		case 0:
			return String.class;
		case 1:
			return Position.class;
		case 2:
			return Integer.class;
		}
		return Object.class;
	}
	public int getColumnCount(){
		return 3;
	}
	public String getColumnName(int ColumnIndex){
		switch (ColumnIndex){
		case 0:
			return "Full name";
		case 1:
			return "Position";
		case 2:
			return "Salary";
		}
		return "";
	}
	public int getRowCount(){
		return _size;
	}
	public Object getValueAt(int rowIndex, int columnIndex){
		Employee em = _localDep.getEmployeeAtIndex(rowIndex);
		switch (columnIndex){
		case 0:
			return em.getFIO();
		case 1:
			return em.getPosition();
		case 2:
			return em.getSalary();
		}
		return "";
	}
	public boolean isCellEditable (int rowIndex, int columnIndex){
		return true;
	}
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		Employee em = new Employee();
		switch(columnIndex){
		case 0: 
			em.setName((String)value);
		case 1:
			em.setPosition((Position)value);
		case 2:
			em.setSalary((Integer)value);
		}
		_localDep.setEmployeeAt(rowIndex, em);
		fireTableCellUpdated(rowIndex, columnIndex);
	}		
	public void remove(int index){
		_localDep.remove(index);
		fireTableRowsDeleted(index, index);
	}
}
