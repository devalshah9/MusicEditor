package cs3500.music.view;

import javax.swing.*;

import cs3500.music.model.IViewModel;

/**
 * Created by akati on 11/13/2016.
 */
public class InteractionsPanel extends JPanel {

  private IViewModel model;
  JTextField input;
  JButton send;

  public InteractionsPanel(IViewModel model) {
    super();
    this.model = model;
  }



}
