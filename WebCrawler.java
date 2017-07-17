import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bharath K Devaraju
 *
 *
 */
public class WebCrawler {

	public static void main(String args[]) throws MalformedURLException, IOException
	{
		String root="https://wizardofbigdata.wordpress.com/";
		//int number=20;
		//int count=0;
		Queue<String> urls=new LinkedList<String>();
		Set<String> visited=new HashSet<String>();
		urls.add(root);
		visited.add(root);
		while(!urls.isEmpty())
		{
			String url=urls.poll();
//			if(count>=depth)
//			{
//				System.out.println("Crawling complete");
//				return;
//			}

			StringBuffer htmlBody=new StringBuffer();
			try{
				HttpURLConnection connection=(HttpURLConnection) new URL(url).openConnection();
				 BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
				
				String line;
				while((line=in.readLine())!=null)
				{
					htmlBody.append(line);
				}
			//	count++;
				 System.out.println(url);
				//System.out.println(htmlBody);
				String regexpattern="(http|https)://(\\w+\\.)*(\\w+)";
				Pattern pattern=Pattern.compile(regexpattern);
				Matcher matcher=pattern.matcher(htmlBody);
				while(matcher.find())
				{
					String w=matcher.group();
					if(!visited.contains(w))
					{
						visited.add(w);
						urls.add(w);
					}
				}
				in.close();
			}
			catch(UnknownHostException e)
			{
				System.out.println("Broken Url Found:"+url);
			}
			catch(FileNotFoundException e)
			{
				System.out.println("Broken Url Found:"+url);
			}
			
			//if(!visited)
		}
	}
}
