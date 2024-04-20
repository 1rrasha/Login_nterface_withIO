import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Login_Interface extends JFrame implements ActionListener {
    JLabel studentNameLabel, studentNumberLabel;
    JTextField studentNameField, studentNumberField;
    JButton enterButton, printButton;
    static StudentChart studentChart = new StudentChart();
    static final String FILE_NAME = "studentsrecords.txt";

    public Login_Interface() {
        studentNameLabel = new JLabel("Student Name:");
        studentNumberLabel = new JLabel("Student Number:");
        studentNameField = new JTextField(20); // Set length to 20 characters
        studentNumberField = new JTextField(20);
        enterButton = new JButton("Enter");
        printButton = new JButton("Print Student");

        // Setting up layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        add(studentNameLabel, gbc);

        gbc.gridy++;
        add(studentNameField, gbc);

        gbc.gridy++;
        add(studentNumberLabel, gbc);

        gbc.gridy++;
        add(studentNumberField, gbc);

        // Creating a panel for buttons and adding it to the layout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(enterButton);
        buttonPanel.add(printButton);
        gbc.gridy++;
        gbc.gridwidth = 2; // Set the buttons next to each other
        add(buttonPanel, gbc);

        enterButton.addActionListener(this);
        printButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterButton) {
            String studentName = studentNameField.getText();
            String studentNumber = studentNumberField.getText();
            if (!studentName.isEmpty() && !studentNumber.isEmpty()) {
                Student student = new Student(studentName, studentNumber);
                studentChart.addStudent(student);
                saveStudentRecord(student); // Save student record to file
                JOptionPane.showMessageDialog(this, "Student Added:\n" + student);
                // to clear fields after adding student
                studentNameField.setText("");
                studentNumberField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both fields :).");
            }
        } else if (e.getSource() == printButton) {
            String studentName = readStudentRecord(); // Read student record from file
            if (studentName != null) {
                studentNameField.setText(studentName); // Display student name in the text field
            } else {
                JOptionPane.showMessageDialog(this, "No student records available:(.");
            }
        }
    }

    // Method to save student record to a file
    private void saveStudentRecord(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(student.getName() + "," + student.getId() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving student record:(.");
        }
    }

    // Method to read student record from a file
    private String readStudentRecord() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            List<String> records = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            if (!records.isEmpty()) {
                return records.getLast().split(",")[0]; // Return the last student name
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Cannot reading student records:(.");
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login_Interface::new);
    }
}
