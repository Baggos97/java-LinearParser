import com.sun.source.tree.ParenthesizedTree;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {



        Parser parser=new Parser();

        //INPUT
        Scanner scanner = new Scanner(new File("LP_1.txt"));//type the path of input file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String newline = line.stripLeading().stripTrailing().replaceAll(" ","").replaceAll(",","");
            parser.Parse(newline);
        }

        //OUTPUT
        File file=new File("LP_2.txt");//Output .txt file
        FileOutputStream fileOutputStream=null;
        PrintStream printStream=null;
        try {
            fileOutputStream=new FileOutputStream(file);
            printStream=new PrintStream(fileOutputStream);

            //Print newline
            printStream.println();

            printStream.println("---------RESULTS---------"+"\n");

            //Print MinMax
            parser.printMinMax(printStream);
            printStream.println();

            //Array C
            parser.printCoefficientsOfObjectiveFunctionList(printStream);
            printStream.println();

            //Array A
            parser.printCoefficientsOfTechnologicalRestrictionsList(printStream);
            printStream.println();

            //Array B
            parser.printRightMembersOfTechnologicalRestrictionsList(printStream);
            printStream.println();

            //Eqin Array
            parser.printTypeOfRestrictionsList(printStream);
            printStream.println();

            //Print String
            printStream.println("----------END OF RESULTS----------");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
                if(printStream!=null){
                    printStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
