import java.io.File;
import java.util.StringTokenizer;


public class Test
{
	public static void main(String args[])
	{
		String folder = ("d:\\upload\\XXGX\\2014\\08\\25");
		
		CreateFolders(folder);


	}
	
	public static  void CreateFolders(final String folders) {
        StringTokenizer st = new StringTokenizer(folders, File.separator);
        StringBuilder sb = new StringBuilder();
        String osname = System.getProperty("os.name");
        if (osname.compareToIgnoreCase("linux") == 0)
            sb.append(File.separator);
 
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            File file = new File(sb.toString());
            if (!file.exists())
                file.mkdir();
            sb.append(File.separator);
        }
 
    }

}
