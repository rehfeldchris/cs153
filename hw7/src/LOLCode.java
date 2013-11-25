import wci.frontend.LOLCodeParser;

public class LOLCode
{
    public static void main(String[] args)
    {
        try {
            LOLCodeParser.main(args);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}