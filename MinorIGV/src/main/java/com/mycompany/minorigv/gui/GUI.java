package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.sequence.Strand;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;


/**
 * Class waarin de main staat waarmee de GUI geinitialiseerd wordt en verschillende objecten worden toegevoegt.
 * @author kahuub
 * Date: 19/11/18
 */
public class GUI {

	private JFrame frame;
	private ReferencePanel ReferencePaneel;
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
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		IGVMenuBar igvMenuBar = new IGVMenuBar();
		igvMenuBar.init();
		igvMenuBar.featureMenu();

		GridBagConstraints gbc_panel = new GridBagConstraints();
		//gbc_panel.insets = new Insets(0, 0, 0, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(igvMenuBar,gbc_panel );
		
		
		GenomePanel organismPanel = new GenomePanel();
		organismPanel.init();
		
		GridBagConstraints gbc_genome = new GridBagConstraints();
		gbc_genome.fill = GridBagConstraints.HORIZONTAL;
		gbc_genome.gridx = 0;
		gbc_genome.gridy = 1;
		organismPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(organismPanel,gbc_genome );
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 2;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		OrganismPanel organism = new OrganismPanel();
		scrollPane_1.setViewportView(organism);
		organism.init();
		
		
		RulerPanel liniaal = new RulerPanel();
		liniaal.init();
		organism.add(liniaal);

		FeaturePanel featuresForward = new FeaturePanel();
		featuresForward.init(true);
		organism.add(featuresForward);

		CodonPanel forwardPanel = new CodonPanel();

		forwardPanel.init(Strand.POSITIVE);

		organism.add(forwardPanel);

		ReferencePanel refpanel1 = new ReferencePanel();
		refpanel1.init();
		organism.add(refpanel1);

		CodonPanel reversePanel = new CodonPanel();

		reversePanel.init(Strand.NEGATIVE);

		organism.add(reversePanel);

		FeaturePanel featuresReverse = new FeaturePanel();
		featuresReverse.init(false);
		organism.add(featuresReverse);

		context = new Context();
		refpanel1.setContext(context);
		forwardPanel.setContext(context);
		reversePanel.setContext(context);
		liniaal.setContext(context);
		organismPanel.setContext(context);
		igvMenuBar.setContext(context);
		featuresForward.setContext(context);
		featuresReverse.setContext(context);

	}
}