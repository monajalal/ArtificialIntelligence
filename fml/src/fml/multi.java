package fml;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class multi extends JFrame {
  public static void main(String[] args) {
    new multi();
  }

  JTextField fdLhs = new JTextField(10), fdRhs = new JTextField(10);
		  //address = new JTextField(20);

  JRadioButton dt = new JRadioButton("Decision Tree"), naive = new JRadioButton("Naive Bayes"),
      tan = new JRadioButton("TAN"), height = new JRadioButton("Tree Height"),
      precision = new JRadioButton("Precision");

  JCheckBox yelp = new JCheckBox("Kaggle Yelp"),
		    tomato = new JCheckBox("Kaggle Rotten Tomato"),
            baidu = new JCheckBox("Kaggle Baidu");

  JButton okButton = new JButton("OK"), closeButton = new JButton("Close");

  public multi() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel1 = new JPanel();
    panel1.setLayout(new GridBagLayout());
    addItem(panel1, new JLabel("FD LHS:"), 0, 0, 1, 1, GridBagConstraints.EAST);
    addItem(panel1, new JLabel("FD RHS:"), 0, 1, 1, 1, GridBagConstraints.EAST);
   // addItem(panel1, new JLabel("Address:"), 0, 2, 1, 1, GridBagConstraints.EAST);

    addItem(panel1, fdLhs, 1, 0, 2, 1, GridBagConstraints.WEST);
    addItem(panel1, fdRhs, 1, 1, 1, 1, GridBagConstraints.WEST);
 //   addItem(panel1, address, 1, 2, 2, 1, GridBagConstraints.WEST);

    Box fmlBox = Box.createVerticalBox();
    ButtonGroup sizeGroup = new ButtonGroup();
    sizeGroup.add(dt);
    sizeGroup.add(naive);
    sizeGroup.add(tan);
    fmlBox.add(dt);
    fmlBox.add(naive);
    fmlBox.add(tan);
    fmlBox.setBorder(BorderFactory.createTitledBorder("FML algorithms"));
    addItem(panel1, fmlBox, 0, 3, 1, 1, GridBagConstraints.NORTH);

    Box styleBox = Box.createVerticalBox();

    ButtonGroup styleGroup = new ButtonGroup();
    styleGroup.add(height);
    styleGroup.add(precision);
    styleBox.add(height);
    styleBox.add(precision);
    styleBox.setBorder(BorderFactory.

    createTitledBorder("Stopping Condition"));
    addItem(panel1, styleBox, 1, 3, 1, 1, GridBagConstraints.NORTH);

    Box topBox = Box.createVerticalBox();
    ButtonGroup topGroup = new ButtonGroup();
    topGroup.add(yelp);
    topGroup.add(tomato);
    topGroup.add(baidu);
    topBox.add(yelp);
    topBox.add(tomato);
    topBox.add(baidu);
    topBox.setBorder(BorderFactory.createTitledBorder("Datasets"));
    addItem(panel1, topBox, 2, 3, 1, 1, GridBagConstraints.NORTH);

    Box buttonBox = Box.createHorizontalBox();
    buttonBox.add(okButton);
    buttonBox.add(Box.createHorizontalStrut(20));
    buttonBox.add(closeButton);
    addItem(panel1, buttonBox, 2, 4, 1, 1, GridBagConstraints.NORTH);

    this.add(panel1);
    this.pack();
    this.setVisible(true);
  }

  private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = x;
    gc.gridy = y;
    gc.gridwidth = width;
    gc.gridheight = height;
    gc.weightx = 100.0;
    gc.weighty = 100.0;
    gc.insets = new Insets(5, 5, 5, 5);
    gc.anchor = align;
    gc.fill = GridBagConstraints.NONE;
    p.add(c, gc);
  }
}
