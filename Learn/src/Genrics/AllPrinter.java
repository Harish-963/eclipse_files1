package Genrics;

//Single parameter
public class AllPrinter <T extends Number>{

	private T whatToPrint;

	public AllPrinter(T whatToPrint) {
		this.whatToPrint = whatToPrint;
	} 
	
	void printing()
	{
		
		System.out.println(whatToPrint);
	}
	
}


