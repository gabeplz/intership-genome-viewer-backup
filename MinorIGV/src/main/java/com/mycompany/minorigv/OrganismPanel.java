package com.mycompany.minorigv;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class OrganismPanel extends JPanel {

	public void init() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Color.GREEN);
		
	}

}
