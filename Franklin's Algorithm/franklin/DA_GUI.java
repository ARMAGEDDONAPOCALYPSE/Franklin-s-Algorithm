package franklin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JToggleButton;

public class DA_GUI {

	private JFrame frmTheFranklinLeader;
	private final int HEIGHT = 600;
	private final int WIDTH = 1440;
	private JTextField np;
	private JTextField irange;
	private JTextArea textArea;

	private int pos = 0;
	private JLabel img_label;
	private JButton btnNewButton_1;
	private JPanel img_panel;
	private JToggleButton tglbtnNewToggleButton;
	private JToggleButton tglbtnShow;

	private static final String FILE_PATH = "C:\\Users\\KAI\\Desktop\\JAVA\\Franklin¡¯s Algorithm\\src\\image\\";

	/**
	 * Create the application.
	 */
	public DA_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	public String[] getImages() {
		File file = new File(FILE_PATH);
		String[] imagesList = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return !dir.isHidden();
			}
		});
		return imagesList;
	}

	public void showImage(int index) {
		String[] imagesList = getImages();

		String imageName = imagesList[index];

		ImageIcon icon = new ImageIcon(FILE_PATH + imageName);

		Image image = icon.getImage().getScaledInstance(img_label.getWidth(), img_label.getHeight(),
				Image.SCALE_SMOOTH);

		img_label.setIcon(new ImageIcon(image));

	}

	private void initialize() {

		frmTheFranklinLeader = new JFrame();
		frmTheFranklinLeader.setFont(new Font("Source Sans Pro", Font.PLAIN, 28));
		frmTheFranklinLeader.setTitle("The Franklin Leader Election Algorithm");
		frmTheFranklinLeader.setBounds(100, 100, this.WIDTH, this.HEIGHT);
		frmTheFranklinLeader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmTheFranklinLeader.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton = new JButton("Start Election");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (np.getText().isEmpty() || irange.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmTheFranklinLeader, "Process and Identity are NULL, Set 5 and 100 as default", "Auto Setup",
							0, null);
					np.setText("5");
					irange.setText("100");
				}

				fle_init();
				btnNewButton.setText("Re-Elect");
				btnNewButton_1.setEnabled(true);
			}
		});
		btnNewButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 28));
		panel.add(btnNewButton, BorderLayout.WEST);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);

		tglbtnNewToggleButton = new JToggleButton("Probabilistic OFF");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnNewToggleButton.isSelected())
					tglbtnNewToggleButton.setText("Probabilistic ON");
				else
					tglbtnNewToggleButton.setText("Probabilistic OFF");
			}
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		panel_2.add(btnClear);
		panel_2.add(tglbtnNewToggleButton);

		JLabel lblNewLabel = new JLabel("Number of Processes");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_2.add(lblNewLabel, BorderLayout.NORTH);

		np = new JTextField();

		np.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_2.add(np);
		np.setColumns(3);

		JLabel lblNewLabel_1 = new JLabel("Identity Number Range");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_2.add(lblNewLabel_1);

		irange = new JTextField();

		irange.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_2.add(irange);
		irange.setColumns(3);
		
		tglbtnShow = new JToggleButton("Hide");
		tglbtnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnShow.isSelected())
					tglbtnShow.setText("Show");
				else
					tglbtnShow.setText("Hide");
			}
		});
		panel_2.add(tglbtnShow);

		btnNewButton_1 = new JButton("More Information");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				img_panel.setVisible(true);
			}
		});
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setFont(new Font("Source Sans Pro", Font.PLAIN, 28));
		panel.add(btnNewButton_1, BorderLayout.EAST);

		textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane sp = new JScrollPane(textArea);
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		textArea.setEditable(false);
		frmTheFranklinLeader.getContentPane().add(sp, BorderLayout.CENTER);

		img_panel = new JPanel();
		frmTheFranklinLeader.getContentPane().add(img_panel, BorderLayout.EAST);
		img_panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		img_panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		img_panel.setVisible(false);

		JButton img_pre = new JButton("Previous");
		img_pre.setFont(new Font("Source Sans Pro", Font.PLAIN, 28));
		img_pre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pos--;
				if (pos < 0) {
					pos = 0;
				}
				showImage(pos);

			}
		});
		panel_1.add(img_pre, BorderLayout.WEST);

		JButton img_next = new JButton("Next");
		img_next.setFont(new Font("Source Sans Pro", Font.PLAIN, 28));
		img_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pos++;
				if (pos >= getImages().length) {
					pos = 0;
				}
				showImage(pos);
			}
		});
		panel_1.add(img_next, BorderLayout.EAST);

		JButton img_start = new JButton("Start");
		img_start.setFont(new Font("Source Sans Pro", Font.PLAIN, 28));
		img_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				showImage(0);

			}
		});
		panel_1.add(img_start, BorderLayout.CENTER);

		img_label = new JLabel();
		img_label.setPreferredSize(new Dimension(600, 0));

		img_panel.add(img_label, BorderLayout.CENTER);

		// np.setText(JOptionPane.showInputDialog(frmTheFranklinLeader, "Please input
		// number of processes."));
		// irange.setText((JOptionPane.showInputDialog(frmTheFranklinLeader, "Please
		// input identity number range")));

	}

	private void fle_init() {
		int n_p = Integer.parseInt((np.getText()));
		int i_r = Integer.parseInt((irange.getText()));

		// validate process number
		if (n_p < 2) {
			JOptionPane.showMessageDialog(frmTheFranklinLeader, "Number of Processes must be greater than 2",
					"Invalidate Process Number", i_r, null);
		}

		// validate identity number
		if (i_r < 2) {
			JOptionPane.showMessageDialog(frmTheFranklinLeader, "Please enter a validate identity range",
					"Invalidate validate range", i_r, null);
		}

		Franklin_Algorithm fle = new Franklin_Algorithm(n_p, i_r);
		try {
			fle.simulate(textArea, tglbtnNewToggleButton, tglbtnShow);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DA_GUI window = new DA_GUI();
					window.frmTheFranklinLeader.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
