package Genrics;

public class AllPrintermultiple <Type1 ,Type2>{

	private Type1 whatToPrint1;
	private Type2 whatToPrint2;
	
	
	public AllPrintermultiple(Type1 whatToPrint1, Type2 whatToPrint2) {
		this.whatToPrint1 = whatToPrint1;
		this.whatToPrint2 = whatToPrint2;
	}


	@Override
	public String toString() {
		return "AllPrintermultiple [whatToPrint1=" + whatToPrint1 + ", whatToPrint2=" + whatToPrint2 + "]";
	}


	void printing()
	{
		System.out.println(toString());
	}
	
}