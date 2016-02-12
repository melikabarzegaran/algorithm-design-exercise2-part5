package part5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Distribution
{
    private String path;
    private int[] values;

    public Distribution(String path)
    {
        this.path = path;
    }

    private void read(File file)
    {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream input;
        try
        {
            input = new FileInputStream(file);
            input.read(bytes);
            input.close();

            String[] valuesString = new String(bytes).trim().split("\\s+");

            values = new int[valuesString.length];
            for(int counter = 0; counter < valuesString.length; counter++)
            {
                values[counter] = Integer.parseInt(valuesString[counter]);
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("error: the file '" + path + "' was not found.");
            System.exit(1);
        }
        catch(NumberFormatException e)
        {
            System.err.println("error: the file '" + path + "' is not structured properly.\n it should contain a list" +
                    " of integer numbers separated by spaces.");
            System.exit(1);
        }
        catch(IOException e)
        {
            System.err.println("error: an IO error occurred during reading from the file '" + path + "'.");
            System.exit(1);
        }
    }

    private void read(String path)
    {
        if(path == null || path.isEmpty())
        {
            System.err.println("error: the path provided is null or empty.");
            System.exit(1);
        }

        File folder = new File(path);
        if(!folder.exists() || !folder.isDirectory())
        {
            System.err.println("error: the path '" + path + "' doesn't exist or isn't a directory.");
            System.exit(1);
        }

        File[] files = folder.listFiles();
        if(files == null)
        {
            System.err.println("error: the path '" + path + "' does not exist.");
            System.exit(1);
        }
        if(files.length == 0)
        {
            System.err.println("error: the path '" + path + "' doesn't contain any testcase file.");
            System.exit(1);
        }

        for(File file : files)
        {
            if(file.exists() && file.isFile())
            {
                System.out.println("the file '" + file.getName() + "': ");
                read(file);
            }
            else
            {
                System.err.println("error: the file '" + file.getName() + "' doesn't exist or is a directory.");
            }
        }
    }

    private void compute()
    {
        read(path);
    }

    public static void main(String args[])
    {
        if(args.length == 1)
        {
            Distribution distribution = new Distribution(args[0]);
            distribution.compute();
        }
        else
        {
            System.err.println("error: the input is invalid. it should be the path of the folder containing testcase " +
                    "files.");
        }
    }
}