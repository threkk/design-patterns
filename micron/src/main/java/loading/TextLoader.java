package loading;

import java.io.InputStream;
import java.util.Scanner;

import org.jrabbit.base.data.loading.SystemLoader;

/*****************************************************************************
 * TextLoader provides a convenient method for reading a file as a String.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class TextLoader
{
	/*************************************************************************
	 * Reads the contents of the indicated file.
	 * 
	 * @param filepath
	 * 			  The location of the target file, from within the project.
	 * 
	 * @return The contents of the file, as a String.
	 *************************************************************************/
	public static String read(String filepath)
	{
		InputStream stream = new SystemLoader(filepath).stream();
		if(stream != null)
		{
			Scanner scanner = new Scanner(new SystemLoader(filepath).stream());
			String result = "";
			while(scanner.hasNextLine())
			{
				result += scanner.nextLine();
				if(scanner.hasNextLine())
					result += '\n';
			}
			return result;
		}
    	return "(content not found)";
	}
}