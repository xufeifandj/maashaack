
public class NumberTheory
{
	public static final String NUMBER = "36484379009457399269217182889395826722660566693989257289404709863891849615322840169192133464099837107" +
			"563290320068627859223102364122264401785848633686914239718396824942863542362872670850647423969609315959515511402019435615717737240" +
			"510626468808851903266920099765545245394707";
	
	public static void main(String[] args)
	{
		for(int i = 0; i < NUMBER.length(); i += 2)
		{
			System.out.print((char)(Integer.valueOf(NUMBER.substring(i, i + 2)).intValue() + 10));
		}
	}
}
