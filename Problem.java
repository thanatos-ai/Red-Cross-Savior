//import java.lang.*;
public class Problem
{
    private int severity;
    private int numPatients;

    public Problem(int severe)
    {
        severity = severe;
        if(severity == 1)
        {
            numPatients = (int)(Math.random() * 5 + 1);
        }
        else if(severity == 2)
        {
            numPatients = (int)(Math.random() * 5 + 1) + 5;
        }
        else if(severity == 3)
        {
            numPatients = (int)(Math.random() * 5 + 1) + 10;
        }
    }
    public int getSeverity()
    {
        return severity;
    }
    public int getPatients()
    {
        return numPatients;
    }
    public String toString()
    {
        if(severity == 1)
        {
            return (numPatients + " people have been injured in a low severity accident! :/");
        }
        else if(severity == 2)
        {
            return (numPatients + " people have been injured in a medium severity accident! :o");
        }
        else if(severity == 3)
        {
            return (numPatients + " people have been injured in a high severity accident! :(");
        }
        return "";
    }
}