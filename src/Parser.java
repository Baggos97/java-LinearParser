import javax.sound.midi.Soundbank;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static ArrayList<Integer> coefficientsOfObjectiveFunctionList = new ArrayList<Integer>();//Array C
    private static ArrayList<LinkedList<Integer>> coefficientsOfTechnologicalRestrictionsList = new ArrayList<LinkedList<Integer>>();//Array A
    private static ArrayList<Integer> rightMembersOfTechnologicalRestrictionsList = new ArrayList<Integer>(); //Array B
    private static ArrayList<Integer> typeOfRestrictionsList = new ArrayList<Integer>(); //Eqin Array


    private static ArrayList<String> variablesOfObjectiveFunctionList = new ArrayList<String>(); //Variables of Objective Funtion e.g x1,x2
    private static int MinMax=0;


    public void Parse(String anEquation)
    {
        String equation = anEquation.replaceAll(" ","").replaceAll(",","");
        String parsedEquation="";
        boolean isObjectFunctionCoefficient=false;

        LinkedList<Integer> coefficientsOfRistrictionsHelpList = new LinkedList<Integer>();//Ristrictions Coefficient Help List

        ArrayList<String> variablesOfRistrictionsList = new ArrayList<String>();//Variables of Ristriction equation

        if(!(equation.equalsIgnoreCase("st")) && !(equation.equalsIgnoreCase("end"))
        && !(equation.equalsIgnoreCase("s.t")) && !(equation.equalsIgnoreCase("subjectto")) ) {

            if(equation.contains("max") || equation.contains("min") || equation.contains("MAX") || equation.contains("MIN")){
                  if(equation.contains("min") || equation.contains("MIN"))
                      MinMax=-1;
                  else
                      MinMax=1;

                  String[] equationParts = equation.split("=");
                  parsedEquation=equationParts[1];
                  isObjectFunctionCoefficient = true;
            }else {
                //Check if ristrictionEquations are valid
                String ristrictionEquationFormat = "^(((^|[+-])+[\\d]*[a-zA-Z]?\\d*)+([<|>][=]|=)+[+-]*[\\d]*)+";
                Pattern rep = Pattern.compile(ristrictionEquationFormat);
                Matcher rem = rep.matcher(equation);
                rem.find();
                if (rem.matches()) {

                String[] equationParts;
                if (equation.contains(">=")) {
                    equationParts = equation.split(">=");
                    parsedEquation = equationParts[0];
                    rightMembersOfTechnologicalRestrictionsList.add(Integer.parseInt(equationParts[1]));
                    typeOfRestrictionsList.add(1);
                } else if (equation.contains("<=")) {
                    equationParts = equation.split("<=");
                    parsedEquation = equationParts[0];
                    rightMembersOfTechnologicalRestrictionsList.add(Integer.parseInt(equationParts[1]));
                    typeOfRestrictionsList.add(-1);
                } else {
                    equationParts = equation.split("=");
                    parsedEquation = equationParts[0];
                    rightMembersOfTechnologicalRestrictionsList.add(Integer.parseInt(equationParts[1]));
                    typeOfRestrictionsList.add(0);
                }
            }else
                    System.out.println(rem.group());

            }

            //Extraction values for Arrays using regex
            String parsedEquationFormat = "(([+-]|^)+[\\d\\.]*[a-zA-Z]?\\d*)";
            String parsedEquationPartsFormat = "([+-]?[\\d\\.]*)([a-zA-Z]?)\\^?(\\d*)";
            Pattern p1 = Pattern.compile(parsedEquationFormat);
            Matcher m1 = p1.matcher(parsedEquation);

            while (!m1.hitEnd()) {
                m1.find();
                Pattern p2 = Pattern.compile(parsedEquationPartsFormat);
                Matcher m2 = p2.matcher(m1.group());

                if (m2.find()) {

                    int coefficient;
                    try {
                        String coef = m2.group(1);
                        if (isNumeric(coef)) {
                            coefficient = Integer.valueOf(coef);
                        } else {
                            coefficient = Integer.valueOf(coef + "1");
                        }
                    } catch (IllegalStateException e) {
                        coefficient = 0;
                    }
                    if(isObjectFunctionCoefficient){
                        coefficientsOfObjectiveFunctionList.add(coefficient);
                    }else{
                        coefficientsOfRistrictionsHelpList.add(coefficient);
                    }

                        int exponent;
                        try {
                            String exp = m2.group(3);
                            if (isNumeric(exp)) {
                                exponent = Integer.valueOf(exp);
                            } else {
                                exponent = 1;
                            }
                        } catch (IllegalStateException e) {
                            exponent = 0;
                        }

                        String variable = m2.group(2);

                        String ristrictionVariable = variable+String.valueOf(exponent);

                        if(isObjectFunctionCoefficient)
                            variablesOfObjectiveFunctionList.add(ristrictionVariable.replaceAll(" ",""));
                        else
                            variablesOfRistrictionsList.add(ristrictionVariable.replaceAll(" ",""));

                }
            }

            //Fill with zeros when a variable has 0 coefficient
            int index=0;
            for (int i = 0; i < variablesOfObjectiveFunctionList.size(); i++) {
                boolean find=false;
                index = variablesOfObjectiveFunctionList.indexOf(variablesOfObjectiveFunctionList.get(i));
                for(int j=0; j<variablesOfRistrictionsList.size(); j++){
                    if((variablesOfObjectiveFunctionList.get(index).equalsIgnoreCase(variablesOfRistrictionsList.get(j)))){
                        find=true;
                    }
                }
                if(find==false){
                    coefficientsOfRistrictionsHelpList.add(index,0);
                }
            }

            if(!(isObjectFunctionCoefficient))
                coefficientsOfTechnologicalRestrictionsList.add(coefficientsOfRistrictionsHelpList);
        }
    }

    public static boolean isNumeric(String str) {

        return str.matches("[+-]*\\d*\\.?\\d+");
    }

    public  void printCoefficientsOfTechnologicalRestrictionsList(PrintStream printStream) {
        printStream.println("A=[");
        for(LinkedList list: coefficientsOfTechnologicalRestrictionsList)
            printStream.println(list.toString().replace(",","").replace("[","").replace("]",""));

        printStream.println("]");
    }

    public void printCoefficientsOfObjectiveFunctionList(PrintStream printStream){
        printStream.println("c=[");
        for(int list : coefficientsOfObjectiveFunctionList)
            printStream.println(list);

        printStream.println("]");
    }

    public void printRightMembersOfTechnologicalRestrictionsList(PrintStream printStream){
        printStream.println("b=[");
        for(int list : rightMembersOfTechnologicalRestrictionsList)
            printStream.println(list);

        printStream.println("]");
    }

    public void printTypeOfRestrictionsList(PrintStream printStream){
        printStream.println("Eqin=[");
        for(int list : typeOfRestrictionsList)
            printStream.println(list);

        printStream.println("]");
    }

    public void printMinMax(PrintStream printStream){
        printStream.println("MinMax="+MinMax);
    }
}

