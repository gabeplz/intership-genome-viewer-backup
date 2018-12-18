package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.sequence.Strand;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.util.Observable;
import java.util.Observer;


/**
 * Class waarin de Main staat waarmee de GUI geinitialiseerd wordt en verschillende objecten worden toegevoegt.
 * @author kahuub
 * Date: 19/11/18
 */
public class GUI {

	private JFrame frame;
	private ReferencePanel referencePaneel;
	private JScrollPane scrollPane;
	private Context context;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 760, 480);
		frame.setMinimumSize(new Dimension(760,480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		IGVMenuBar igvMenuBar = new IGVMenuBar();
		igvMenuBar.init();

		GridBagConstraints gbcPanel = new GridBagConstraints();
		//gbcPanel.insets = new Insets(0, 0, 0, 0);
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		frame.getContentPane().add(igvMenuBar,gbcPanel );
		
		
		GenomePanel organismPanel = new GenomePanel();
		organismPanel.init();
		
		GridBagConstraints gbcGenome = new GridBagConstraints();
		gbcGenome.fill = GridBagConstraints.HORIZONTAL;
		gbcGenome.gridx = 0;
		gbcGenome.gridy = 1;
		organismPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(organismPanel,gbcGenome );
		
		
		JScrollPane scrollPane1 = new JScrollPane();
		GridBagConstraints gbcScrollPane1 = new GridBagConstraints();
		gbcScrollPane1.weighty = 1.0;
		gbcScrollPane1.fill = GridBagConstraints.BOTH;
		gbcScrollPane1.gridx = 0;
		gbcScrollPane1.gridy = 2;
		frame.getContentPane().add(scrollPane1, gbcScrollPane1);
		
		OrganismPanel organism = new OrganismPanel();
		scrollPane1.setViewportView(organism);
		organism.init();
		
		
		RulerPanel ruler = new RulerPanel();
		ruler.init();
		organism.add(ruler);

		FeaturePanel featuresForward = new FeaturePanel();
		featuresForward.init(true);
		organism.add(featuresForward);

		CodonPanel forwardPanel = new CodonPanel();

		forwardPanel.init(Strand.POSITIVE);

		organism.add(forwardPanel);

		ReferencePanel refPanel1 = new ReferencePanel();
		refPanel1.init();
		organism.add(refPanel1);

		CodonPanel reversePanel = new CodonPanel();

		reversePanel.init(Strand.NEGATIVE);

		organism.add(reversePanel);

		FeaturePanel featuresReverse = new FeaturePanel();
		featuresReverse.init(false);
		organism.add(featuresReverse);

		context = new Context();
		refPanel1.setContext(context);
		forwardPanel.setContext(context);
		reversePanel.setContext(context);
		ruler.setContext(context);
		organismPanel.setContext(context);
		igvMenuBar.setContext(context);
		featuresForward.setContext(context);
		featuresReverse.setContext(context);

	}
}