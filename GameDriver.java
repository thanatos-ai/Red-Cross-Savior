import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import java.net.URL;

//import javafx.beans.value.ChangeListener;
//import javafx.event.ActionEvent;

public class GameDriver {
    private static int day = 0;
    private static int numFailures=0;
    //private static int money=1000;
    public  static void main(String[] args) {
        JFrame startFrame = new JFrame("Red Cross Savior");
        startFrame.setSize(525, 350);
        startFrame.setLocation(400, 200);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);
        
        JPanel gamePanel = createStartPanel();

        
        Image img = Toolkit.getDefaultToolkit().getImage("Images/logo.png");
        Image dimg = img.getScaledInstance(500, 125, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(dimg));
        image.setBounds(5,5,500,125);  
        gamePanel.add(image);

        JButton startGame = startButton(gamePanel, "Play", 212, 187, 100);
        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                startFrame.dispose();
                createMainFrame();
            }
        } );

        JButton instructions = startButton(gamePanel, "instructions", 90, 200, 75);
        instructions.addActionListener(new ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {
                openWebpage("https://docs.google.com/document/d/1V7OrhmwmRypAfS_4s1dNXM-TBq_QnmQGAL4KZ_yGTis/edit");
            } 
        } );

        JButton exit = startButton(gamePanel, "exit", 360, 200, 75);
        exit.addActionListener(new ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            } 
        } );

        startFrame.add(gamePanel);
        startFrame.setVisible(true);

    }
    public static boolean openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }       

    private static JPanel createStartPanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(null);

        //change from map to something better looking        
        return gamePanel;
    }

    private static void createMainFrame() {
        JFrame frame = new JFrame("Game1");
        frame.setSize(820, 620);
        frame.setLocation(300, 25);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        JPanel panel = null;
        try {
           panel = createBoardPanel(frame);
           frame.add(panel);
        } catch (Exception e) {
           frame.dispose();
           main(null);
        }
        frame.setVisible(true);
    }

    private static JButton startButton(JPanel startPanel, String name, int x, int y, int size) {
        Image img = Toolkit.getDefaultToolkit().getImage("Images/" + name + ".png");
        Image dimg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(dimg));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(x,y,size,size);
        startPanel.add(button);

        button.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
              if (button.getModel().isPressed()) {
                 Image newimg = Toolkit.getDefaultToolkit().getImage("Images/" + name + "-pressed.png");
                 Image newdimg = newimg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                 button.setIcon(new ImageIcon(newdimg));
              } else {
                 Image newimg = Toolkit.getDefaultToolkit().getImage("Images/" + name + ".png");
                 Image newdimg = newimg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                 button.setIcon(new ImageIcon(newdimg));
              }
           }
        } );
        
        return button;
    }

    private static JPanel createBoardPanel(JFrame mainFrame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        Image img = Toolkit.getDefaultToolkit().getImage("Images/us-map-transparent.png");
        Image dimg = img.getScaledInstance(780, 480, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(dimg));

        mainPanel.add(image);
        image.setBounds(10,10,790,490);
        image.repaint();
        
        JButton endGame = new JButton("End Game");
        endGame.setFont(new Font("Arial", Font.PLAIN, 14));
        endGame.setBounds(400,485,130,45);
        mainPanel.add(endGame);
        endGame.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
               endGameAction(mainFrame, false);
            } 
        } );
    
    
        JButton nextDay = new JButton("Continue to next Day?");
        nextDay.setFont(new Font("Arial", Font.PLAIN, 14));
        nextDay.setBounds(200,485,170,45);
        mainPanel.add(nextDay);

        JLabel days = new JLabel("Day number: " + day );
        days.setFont(new Font("Arial", Font.PLAIN, 14));
        days.setBounds(25,485,130,45);
        mainPanel.add(days);

        JLabel fails = new JLabel("Failed Hospitals (lose at 10): " + numFailures);
        fails.setFont(new Font("Arial", Font.PLAIN, 14));
        fails.setBounds(25,520,200,45);
        mainPanel.add(fails);

        JLabel moneyLabel = new JLabel("Money: $" + RedCrossCenter.getMoney());
        moneyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        moneyLabel.setBounds(25,450,130,45);
        mainPanel.add(moneyLabel);

        RedCrossCenter [] centers = readCenters("centers.txt", mainPanel, moneyLabel);
    
        nextDay.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {            
                nextDayAction(mainFrame, centers);
                day += 1;
                days.setText("Day number: "+day);
                fails.setText("Failed Hospitals (lose at 10): " + numFailures);
                int moneyToAdd = 0;
                if(day == 30 && numFailures <= 1) moneyToAdd+=100;
                if(day == 50 && numFailures <= 2) moneyToAdd+=300;
                if(day == 75 && numFailures <=2) moneyToAdd+=600;
                if(day == 100 && numFailures <= 5)moneyToAdd+=400;
                if (moneyToAdd!=0)
                    JOptionPane.showMessageDialog(null, "You have been donated $" + moneyToAdd + " for reaching " + 
                        day + " days with " + numFailures + " failures.");
                RedCrossCenter.addMoney(moneyToAdd);
                moneyLabel.setText("Money: $" + RedCrossCenter.getMoney());
            } 
        } );
        
        return mainPanel;
    }
    private static void nextDayAction(JFrame mainFrame, RedCrossCenter[] centers) {
        int rand = (int)(Math.random()*50);
        System.out.println();
        numFailures += centers[rand].createProblem();
        if (numFailures>=10)
            endGameAction(mainFrame, true);
        else if(RedCrossCenter.getMoney()<0)
            endGameAction(mainFrame, true);
        else {
            JOptionPane.showMessageDialog(null, "A Problem has occurred at " + centers[rand].getName() + " center.");
        }
    }

    private static void endGameAction(JFrame mainFrame, boolean gameLost) {
        if (gameLost) {
            JOptionPane.showMessageDialog(null, "Good game! You helped the country survive " + day + " days.");
        } else {
            JOptionPane.showMessageDialog(null, "Sad you didn't want to finish the game. You survived " + day + " days.");
        }
        mainFrame.dispose();
    }
    

    private static RedCrossCenter[] readCenters(String filename, JPanel mainPanel, JLabel toUpdate) {
      Scanner infile = null;
        try {
            infile = new Scanner(new File(filename));
        }
        catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"The file could not be found.");
            System.exit(0);
        }
    
        int numitems = infile.nextInt();
        RedCrossCenter[] propArray = new RedCrossCenter[numitems];
        for(int k = 0; k < numitems; k++) {
            String a = infile.next().replace("*"," ");
            String b = infile.next(); 
            int c = infile.nextInt();
            int d = infile.nextInt(); 
            RedCrossCenter newcenter = new RedCrossCenter(a,b,c,d);
            newcenter.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) {
                    if (newcenter.getIsDead()) {
                        JOptionPane.showMessageDialog(null, "Center Name: " + newcenter.getName() 
                        + "\nLatest problem: " + newcenter.getProblem()
                        + "\nCurrent number in hospital (not possible to help): " + newcenter.getHospital() );
                        return;
                    }
                    String[] choices = {"No", "Yes"};
                    String questionText = "Center Name: " + newcenter.getName() 
                        + "\nLatest problem: " + newcenter.getProblem()
                        + "\nCurrent number in hospital (max 30): " + newcenter.getHospital()
                        + "\n\n Helping this center will release 10 patients, costing $200. Will you assist this center?";
                    String helpAnswer = (String) JOptionPane.showInputDialog(null, questionText, "Center Name: " + newcenter.getName(), 
                        JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                    if (helpAnswer.equals("Yes")) {
                        newcenter.changePatients();
                        RedCrossCenter.addMoney(-200);
                    }
                    toUpdate.setText("Money: $" + RedCrossCenter.getMoney());
                } 
            } );
            propArray[k] = newcenter;
            mainPanel.add(newcenter);

        }
        infile.close();
        return propArray;
    }

}