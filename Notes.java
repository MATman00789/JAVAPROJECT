import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
// import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notes extends JFrame implements ActionListener {

    private JTextArea noteArea;
    private JButton saveButton;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openMenuItem, deleteMenuItem;

    public Notes() {
        super("Notes App");


        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open Note");
        deleteMenuItem = new JMenuItem("Delete Note");
        openMenuItem.addActionListener(this);
        deleteMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);
        fileMenu.add(deleteMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        noteArea = new JTextArea();
        noteArea.setSize(500, 3000);
        noteArea.setLineWrap(true);

        saveButton = new JButton("Save Note");
        saveButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(noteArea);
        panel.add(saveButton);
        add(panel);

        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveNote();
        } else if (e.getSource() == openMenuItem) {
            openNote();
        } else if (e.getSource() == deleteMenuItem) {
            deleteNote();
        }
    }

    private void saveNote() {
        String noteText = noteArea.getText();
        if (noteText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a note to save!");
            return;
        }

        long timestamp = System.currentTimeMillis();
        String filename = "note_" + timestamp + ".txt";

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(noteText);
            writer.close();
            JOptionPane.showMessageDialog(this, "Note saved successfully!");
            noteArea.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving note: " + ex.getMessage());
        }
    }

    
    private void openNote() {
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "assignment";

        JFileChooser fileChooser = new JFileChooser(desktopPath); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(selectedFile);
                StringBuilder noteText = new StringBuilder();
                int ch;
                while ((ch = reader.read()) != -1) {
                    noteText.append((char) ch);
                }
                reader.close();
                noteArea.setText(noteText.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening note: " + ex.getMessage());
            }
        }
    }

    private void deleteNote() {
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "assignment";
    
        JFileChooser fileChooser = new JFileChooser(desktopPath);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(this);
    
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + selectedFile.getName() + "?");
    
            if (confirm == JOptionPane.YES_OPTION) {
                if (selectedFile.delete()) {
                    JOptionPane.showMessageDialog(this, "Note deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting note!");
                }
            }
        }
    }
    
}
