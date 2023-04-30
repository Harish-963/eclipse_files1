package Genrics;

public class GenericMethodsEX {
public static void main(String[] args) {
	PrintingU(9);
	PrintingU("Harish",'P');
	System.out.println(PrintingU(2.2f,99,"harsha")+"printed from returned one...");
	
}
	
//method with single parameters
private static <Type> void  PrintingU(Type WhattoPrint) {
	System.out.println(WhattoPrint+"!!!!!");
}

//method with multiple parameters
private static <Type1,Type2> void PrintingU(Type1 WhattoPrint1, Type2 WhattoPrint2) {
	System.out.println(WhattoPrint1+"!!!!!");
	System.out.println(WhattoPrint1+"!!!!!"+WhattoPrint2);
}

//method with multiple parameters and returning value
private static <Type1,Type2,Type3> Type1 PrintingU(Type1 WhattoPrint1, Type2 WhattoPrint2, Type3 WhattoPrint3) {
	System.out.println(WhattoPrint1+"!!!!!");
	System.out.println(WhattoPrint1+"!!!!!"+WhattoPrint2);
	System.out.println(WhattoPrint1+"!!!!!"+WhattoPrint2+"!!!!!"+WhattoPrint3);
	return WhattoPrint1;

}

}