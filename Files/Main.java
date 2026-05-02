import Gui.MainGUI;
import Service.ComplaintManager;
import Service.UserManager; // Ensure this matches your folder name (gui or Gui)
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        
        UserManager U1 = new UserManager();
        ComplaintManager C1 = new ComplaintManager();

        
        try {
            System.out.println("Connecting to database...");
            U1.loadUsersFromDB();
            C1.loadComplaintsFromDB();
            U1.registerAdmin("Admin", "admin123");
            
            System.out.println("System Ready.");
        } catch (Exception e) {
            System.err.println("Startup Warning: " + e.getMessage());
        }

        // 3. Launch the GUI
        // We use invokeLater to ensure the window runs smoothly on the Linux desktop
        SwingUtilities.invokeLater(() -> {
            MainGUI window = new MainGUI(U1, C1);
            window.setVisible(true);
        });
    }
}