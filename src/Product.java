import java.util.Calendar;
public class Product
{
    private String name;
    private String description;
    private String ID;
    private double cost;

    public Product(String name, String description, String ID, double cost)
    {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    public String getName()
    {
        return padString(name, 35);
    }

    public String getDescription()
    {
        return padString(description, 75);
    }

    public String getID()
    {
        return padString(ID, 6);
    }

    public double getCost()
    {
        return cost;
    }

    private String padString(String str, int length)
    {
        if (str.length() > length)
        {
            return str.substring(0, length);
        } else if (str.length() < length)
        {
            StringBuilder sb = new StringBuilder(str);
            while (sb.length() < length)
            {
                sb.append(" ");
            }
            return sb.toString();
        }
        return str;
    }
}