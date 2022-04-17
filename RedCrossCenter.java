import javax.swing.*;
import java.awt.*;

public class RedCrossCenter extends JButton{
    private String myName;
    private String myColor;
    private int myX;
    private int myY;
    private boolean isDead;
    private Problem problems;
    private int hospital;

    private static int money=1500;

    public RedCrossCenter(String name, String color, int x, int y){
        this.myName = name;
        this.myColor = color;
        this.myX = x;
        this.myY = y;
        this.isDead = true;

        setBackground(Color.GREEN);
        setOpaque(true);
        setBounds(myX,myY,15,15);
        setBorder(BorderFactory.createLineBorder(Color.black));

        // addActionListener(new ActionListener() { 
        //     public void actionPerformed(ActionEvent e) {
        //         String[] choices = {"No", "Yes"};
        //         String questionText = "Center Name: " + myName 
        //             + "\nCurrent problems: " + problems 
        //             + "\nCurrent number in hospital (max 30): " + hospital
        //             + "\n\n Helping this center will release 10 patients, costing $200.";
        //         String helpAnswer = (String) JOptionPane.showInputDialog(null, questionText, "Center Name: " + myName, 
        //             JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        //         if (helpAnswer.equals("Yes")) {
        //             hospital -=10;
        //             if (hospital<=0) {
        //                 hospital=0;
        //                 setBackground(Color.GREEN);
        //             }
        //             money -=200;
        //         }
        //     } 
        // } );
    }
    
    public String getName() {return this.myName;}
    public String getColor() {return this.myColor;}
    public int getX() {return this.myX;}
    public int getY() {return this.myY;}
    public int getHospital() {return hospital;}
    public static int getMoney() {return money;}
    public Problem getProblem() {return problems;}
    public boolean getIsDead() {return isDead;}

    public static void addMoney(int a) {money+=a;}
    public void changePatients() {
        hospital-=10;
        if (hospital<=0) {
            hospital=0;
            setBackground(Color.GREEN);
        }
    }
        
    public int createProblem() {
        int toReturn = this.isDead ? 0:1;
        this.isDead=false;
        int severity = (int) (Math.random() * 3) + 1;
        this.problems = new Problem(severity);
        this.hospital+=problems.getPatients();
        setBackground(Color.ORANGE);
        if (this.hospital>=30 ) {
            setBackground(Color.RED);
            this.isDead = true;
            return toReturn;
        }
        else if(this.hospital == 0) {
            this.isDead = true;
            return 0;
        }
        return 0;
        //this.hasProblem = true;
    }
    
    //public boolean hasProb() {return this.hasProblem;}
}
