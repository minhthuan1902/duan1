package Clock;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.awt.Font;

public class Clock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField newclock;
	private JLabel clock;
	private JButton openNewClock;
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clock frame = new Clock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Clock() {
		setTitle("Clock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 171);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		newclock = new JTextField();
		newclock.setBounds(162, 52, 96, 30);
		contentPane.add(newclock);
		newclock.setColumns(10);
		
		clock = new JLabel(" ");
		clock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clock.setBounds(162, 11, 96, 30);
		contentPane.add(clock);
		
		Thread clockThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					updateTime();
				}
			}
		});
		clockThread.start();
		
		openNewClock = new JButton("Open");
		openNewClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNewClock();
			}
		});
		openNewClock.setBounds(270, 59, 89, 23);
		contentPane.add(openNewClock);
	}
	
	private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        clock.setText(dateFormat.format(new Date()));
    }
	
	private void openNewClock() {
        String input = newclock.getText();
        try {
            int timezone = Integer.parseInt(input);
            JFrame newClockFrame = createNewClockFrame(timezone);
            newClockFrame.setVisible(true);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    private JFrame createNewClockFrame(int timezone) {
        JFrame newFrame = new JFrame("New Clock");
        newFrame.setBounds(100, 100, 300, 100);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel newClockLabel = new JLabel();
        newFrame.getContentPane().add(newClockLabel);
        newClockLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Date date = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

                sdf.setTimeZone(TimeZone.getTimeZone("GMT" + (timezone < 0 ? "" : "+") + timezone));
 
                String formattedTime = sdf.format(date);

                newClockLabel.setText(formattedTime);
            }
        });
        timer.start();

        return newFrame;
    }
}
