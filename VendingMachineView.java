import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;


//MinimalSwingUITemplate taken from - http://www.javapractices.com/topic/TopicAction.do?Id=231

public final class VendingMachineView extends JPanel implements ActionListener{
    private static VendingMachineController controller;


    public static void main(String... aArgs){
        VendingMachineView app = new VendingMachineView();
        controller = new VendingMachineController();
        populateMachine();

        app.buildAndDisplayGui();
    }

    private static void populateMachine(){

    }



  // PRIVATE
  private void buildAndDisplayGui(){
    JFrame frame = new JFrame("Vending Machine");
    frame.setPreferredSize(new Dimension(500, 600));

    JPanel buttons = new JPanel();
    JPanel vendingView = new JPanel();
    frame.getContentPane().add(buttons);
    frame.getContentPane().add(vendingView);

    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

    addCoinButtons(buttons);
    addCoinReturnAndSlot(buttons);
    addKeypad(buttons);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void addCoinButtons(JPanel panel){

    JButton onep = new JButton("1p");
    onep.setActionCommand("1");
    onep.addActionListener(this);
    panel.add(onep);

    JButton twop = new JButton("2p");
    twop.setActionCommand("2");
    twop.addActionListener(this);
    panel.add(twop);

    JButton fivep = new JButton("5p");
    fivep.setActionCommand("5");
    fivep.addActionListener(this);
    panel.add(fivep);

    JButton tenp = new JButton("10p");
    tenp.setActionCommand("10");
    tenp.addActionListener(this);
    panel.add(tenp);

    JButton twentyp = new JButton("20p");
    twentyp.setActionCommand("20");
    twentyp.addActionListener(this);
    panel.add(twentyp);


    JButton fiftyp = new JButton("50p");
    fiftyp.setActionCommand("50");
    fiftyp.addActionListener(this);
    panel.add(fiftyp);

    JButton onepound = new JButton("\u00A31");//\u00A3 is the pound symbol.
    onepound.setActionCommand("100");
    onepound.addActionListener(this);
    panel.add(onepound);

    JButton twopound = new JButton("\u00A32");//\u00A3 is the pound symbol.
    twopound.setActionCommand("200");
    twopound.addActionListener(this);
    panel.add(twopound);
}



public void actionPerformed(ActionEvent e) {
    String coin = e.getActionCommand();
    int val = Integer.parseInt(coin);

    JOptionPane.showMessageDialog(null, "My Goodness, this is:"+ coin);

    controller.coinInserted(val);
}


  private void addCoinReturnAndSlot(JPanel panel){
    //we use a panel here in case we want to add more things
    //onto the panel later, such as a picture of a coin slot.

    JButton coinReturn = new JButton("COIN RETURN");
    coinReturn.setActionCommand("1");
    coinReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int[] change = controller.coinReturnPressed();
                System.out.println("Returning these coins");
            }
        });
    panel.add(coinReturn);

    frame.getContentPane().add(panel);
}








  // private void buildContent(JFrame aFrame){
  //   JPanel panel = new JPanel();
  //   panel.add(new JLabel("Hello"));
  //   JButton ok = new JButton("OK");
  //   ok.addActionListener(new ShowDialog(aFrame));
  //   panel.add(ok);
  //   aFrame.getContentPane().add(panel);
  // }

  // private static final class ShowDialog implements ActionListener {
  //   /** Defining the dialog's owner JFrame is highly recommended. */
  //   ShowDialog(JFrame aFrame){
  //     fFrame = aFrame;
  //   }
  //   @Override public void actionPerformed(ActionEvent aEvent) {
  //     JOptionPane.showMessageDialog(fFrame, "This is a dialog");
  //   }
  //   private JFrame fFrame;
  // }
}
