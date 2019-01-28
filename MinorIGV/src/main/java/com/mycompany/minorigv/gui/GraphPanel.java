package com.mycompany.minorigv.gui;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Tim
 */
public class GraphPanel extends IGVPanel implements PropertyChangeListener {

    private static final int BORDER_GAP = 12;
    private static final Color GRAPH_COLOR = Color.blue;
    private static final Stroke GRAPH_STROKE = new BasicStroke(1f);

    public GraphPanel(Context context) {
        super();
        this.setContext(context);
        this.setListeners();
        this.init();
    }

    @Override
    protected void paintComponent(Graphics g) {

        if(cont.getCurrentReadCoverage() == null){
            return;
        }

        int[] hoi = cont.getCurrentReadCoverage();

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int MAX_SCORE = 0;
        for(int a : hoi){
            if (a > MAX_SCORE) MAX_SCORE = a;
        }

        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);

        int steps = (int) Math.ceil((hoi.length / (double) (getWidth() - 2 *BORDER_GAP)));

        ArrayList<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < hoi.length; i+=steps) {

            int x1 = (int) DrawingTools.calculateLetterPosition(getWidth(),hoi.length,i);

            double valueNeg = new Double(MAX_SCORE - hoi[i]);
            double percHeight = valueNeg / (double) MAX_SCORE;
            int pixHeight = (getHeight()-2*BORDER_GAP);

            int y1 = (int) (percHeight * pixHeight);

            graphPoints.add(new Point(x1, y1));
        }

        // create x and y axes
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);

    }


    public void init() {
        this.setPreferredSize(new Dimension(200, 25));
        this.setMinimumSize(new Dimension(100, 25));

    }

    @Override
    public void setListeners() {
        cont.addPropertyChangeListener("range", this);
        cont.addPropertyChangeListener("chromosome", this);
        cont.addPropertyChangeListener("blastedReads",this);
    }
}