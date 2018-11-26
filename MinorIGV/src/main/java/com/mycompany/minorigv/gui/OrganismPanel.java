package com.mycompany.minorigv.gui;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Container class voor alle Panelen die behoren bij een gekozen organism,
 * ReferencePanel, RulerPanel, FeaturePanel, GenomePanel, CodonPanel.
 * @author kahuub
 * Date 20/11/18
 */
public class OrganismPanel extends JPanel {

	public void init() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Color.GREEN);
		
	}

}