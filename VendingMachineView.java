import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Font;

import java.awt.GridLayout;

//MinimalSwingUITemplate taken from - http://www.javapractices.com/topic/TopicAction.do?Id=231

public final class VendingMachineView extends JPanel implements ActionListener, VendingMachineViewInterface{
    private static VendingMachineController controller;


    public static void main(String... aArgs){
        VendingMachineView app = new VendingMachineView();
        controller = new VendingMachineController(app);
        controller.populateMachine();

        app.buildAndDisplayGui();
    }





  private void buildAndDisplayGui(){
    JFrame frame = new JFrame("Vending Machine");
    frame.setPreferredSize(new Dimension(900, 600));

    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

    JPanel vendingView = new JPanel();
    frame.getContentPane().add(vendingView);
    frame.getContentPane().add(buttons);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

    buttons.add(Box.createVerticalStrut(40));
    addScreen(buttons);
    buttons.add(Box.createVerticalStrut(40));
    addCoinButtons(buttons);
    buttons.add(Box.createVerticalStrut(20));
    addCoinReturnAndSlot(buttons);

    buttons.add(Box.createVerticalStrut(100));
    addKeypad(buttons);
    buttons.add(Box.createVerticalStrut(25));
    //addCointReturnTray(buttons);
    addVendingMachine(vendingView);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }


JLabel screen;
private void addScreen(JPanel panel){
    screen = new JLabel("Vending machine is ready.");
    screen.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    panel.add(screen);
}

  private void addCoinButtons(JPanel panel){
    JPanel coinPanel = new JPanel();
    coinPanel.setPreferredSize(new Dimension(350, 10));
    coinPanel.setLayout(new GridLayout(2, 4));

    JButton onep = new JButton("1p");
    onep.setActionCommand("coin1");
    onep.addActionListener(this);
    coinPanel.add(onep);

    JButton twop = new JButton("2p");
    twop.setActionCommand("coin2");
    twop.addActionListener(this);
    coinPanel.add(twop);

    JButton fivep = new JButton("5p");
    fivep.setActionCommand("coin5");
    fivep.addActionListener(this);
    coinPanel.add(fivep);

    JButton tenp = new JButton("10p");
    tenp.setActionCommand("coin10");
    tenp.addActionListener(this);
    coinPanel.add(tenp);

    JButton twentyp = new JButton("20p");
    twentyp.setActionCommand("coin20");
    twentyp.addActionListener(this);
    coinPanel.add(twentyp);


    JButton fiftyp = new JButton("50p");
    fiftyp.setActionCommand("coin50");
    fiftyp.addActionListener(this);
    coinPanel.add(fiftyp);

    JButton onepound = new JButton("\u00A31");//\u00A3 is the pound symbol.
    onepound.setActionCommand("coin100");
    onepound.addActionListener(this);
    coinPanel.add(onepound);

    JButton twopound = new JButton("\u00A32");//\u00A3 is the pound symbol.
    twopound.setActionCommand("coin200");
    twopound.addActionListener(this);
    coinPanel.add(twopound);

    panel.add(coinPanel);

}


private int firstPressed;
public void actionPerformed(ActionEvent e) {
    String buttonID = e.getActionCommand();

    //Coin Input
    if(buttonID.charAt(0)=='c'){
    String coin = buttonID.substring(4);
    int val = Integer.parseInt(coin);
    controller.coinInserted(val);
    }

    //Food selection.
    else{
        int keyPressed = Integer.parseInt(buttonID);
        if(keyPressed==-1){ firstPressed=-1;}
        else{
            if(firstPressed == -1){firstPressed = Integer.parseInt(buttonID);}
            else{
                int choice = (firstPressed*10) + Integer.parseInt(buttonID);
                JOptionPane.showMessageDialog(null, "User chose:"+ choice);
                controller.madeChoiceOfItem(choice);
                firstPressed = -1;
            }
        }
    }

}


  private void addCoinReturnAndSlot(JPanel panel){
    //we use a panel here in case we want to add more things
    //onto the panel later, such as a picture of a coin slot.

    JButton coinReturn = new JButton("COIN RETURN");
    coinReturn.setActionCommand("1");
    coinReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String change = controller.coinReturnPressed();
                JOptionPane.showMessageDialog(null, "Returning these coins:\n"+ change);
            }
        });
    panel.add(coinReturn);
}


private void addVendingMachine(JPanel panel){
    String IMG_PATH = "./machine.jpg";
    try{
    BufferedImage img = ImageIO.read(new File(IMG_PATH));
    ImageIcon icon = new ImageIcon(img);
    panel.add(new JLabel(icon));
    }
    catch (IOException e) {
        e.printStackTrace();
    }
}

private void addKeypad(JPanel panel){
        JLabel label = new JLabel("Keypad");
        panel.add(label);

        JPanel keyPanel = new JPanel();
        keyPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        // keyPanel.setPreferredSize(new Dimension(350, 40));
        keyPanel.setLayout(new GridLayout(4, 3));

        for(int i=1; i<=9; i++){
            JButton key = new JButton(Integer.toString(i));
            key.setActionCommand(Integer.toString(i));
            key.addActionListener(this);
            keyPanel.add(key);
        }


        JButton key = new JButton("<==");
        key.setActionCommand(Integer.toString(-1));
        key.addActionListener(this);
        keyPanel.add(key);

        JButton key2 = new JButton(Integer.toString(0));
        key2.setActionCommand(Integer.toString(0));
        key2.addActionListener(this);
        keyPanel.add(key2);

        firstPressed=-1;
        panel.add(keyPanel);
}

public void UpdateScreen(String message){
    screen.setText(message);
}

public void DispenseProduct(String product){

}


}
