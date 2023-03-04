import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtPhone;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtId;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection connection;
    PreparedStatement preparedStatement;



    public void Connect() {

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

            System.out.println("Success");

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
        }

        void TableLoad(){
        try{
        preparedStatement = connection.prepareStatement("select * from employee");
            ResultSet resultSet = preparedStatement.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch(SQLException e){
        e.printStackTrace();}
        }


    public Employee() {
        Connect();
        TableLoad();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, salary, phone;

                name = txtName.getText();
                salary = txtSalary.getText();
                phone = txtPhone.getText();

                try{
                    preparedStatement = connection.prepareStatement("insert into employee(name, salary, phone) values (?, ?, ?)");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, salary);
                    preparedStatement.setString(3, phone);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record added");
                    TableLoad();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtPhone.setText("");
                    txtName.requestFocus();
                }catch(SQLException e1){
                    e1.printStackTrace();
                }


            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                String id = txtId.getText();

                preparedStatement = connection.prepareStatement("select name, salary, phone from employee where id = ?");
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){

                    String name = resultSet.getString(1);
                    String salary = resultSet.getString(2);
                    String phone = resultSet.getString(3);

                    txtName.setText(name);
                    txtSalary.setText(salary);
                    txtPhone.setText(phone);
                }
                else{
                    txtName.setText("");
                    txtSalary.setText("");
                    txtPhone.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid employee id");
                }


            }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, salary, phone, id;

                name = txtName.getText();
                salary = txtSalary.getText();
                phone = txtPhone.getText();
                id = txtId.getText();

                try{
                    preparedStatement = connection.prepareStatement("update employee set name = ?, salary = ?, phone = ? where id = ?");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, salary);
                    preparedStatement.setString(3, phone);
                    preparedStatement.setString(4, id);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated");
                    TableLoad();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtPhone.setText("");
                    txtId.setText("");
                    txtName.requestFocus();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;

                id = txtId.getText();

                try {
                    preparedStatement= connection.prepareStatement("delete from employee where id = ?");
                    preparedStatement.setString(1, id);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record deleted");
                    TableLoad();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtPhone.setText("");
                    txtName.requestFocus();


                }catch (Exception e2){
                    e2.printStackTrace();
                }}
        });
    }
}
