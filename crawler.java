/*References
https://www.programcreek.com/2012/12/how-to-make-a-web-crawler-using-java/
https://jsoup.org/cookbook/extracting-data/selector-syntax
https://www.geeksforgeeks.org/establishing-jdbc-connection-in-java/
Author : Nihar Suryawnashi.
email: nihar.suryawanshi@gmail.com
*/

package web_crawler;
import java.io.IOException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 

public class crawler {

	
	public static database_con db = new database_con();
	public static Integer i = 0;
	public static Integer j=0;
	public static String name = null;
	public static void main(String[] args) throws SQLException, IOException{
		// TODO Auto-generated method stub
		db.runSql2("TRUNCATE record;");
		Scanner read = new Scanner(System.in);
		System.out.println("Enter the Keyword to search:" );
		name = read.nextLine();
		System.out.println("System scan in process....");
		
		RunCrawler("https://www.uta.edu/uta/");
		
		System.out.println("Search complete");
		System.out.println("Total Iterations :"+i);
		System.out.println("Total Pages hit with given keyword:" +j);
		read.close();
	}
	
	public static void RunCrawler(String URL) throws SQLException, IOException{
		
		i++;
		//System.out.println("Iteration :"+i);
		String sq = "select * from record where URL = '"+URL+"'";
		ResultSet rs = db.runSql1(sq);
		//System.out.println("hello its here");
		if(rs.next()) {
			
		}
		else {
			sq = "INSERT INTO crawler.record " + "(URL) VALUES" + "(?);" ; 
			PreparedStatement stmt = db.con.prepareStatement(sq, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, URL);
			stmt.execute();
			
			
			Document doc = Jsoup.connect("https://www.uta.edu/uta/").get();
			if(doc.text().contains(name)){
				System.out.println(URL);
				j++;
			}	
			//get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				if(link.attr("href").contains("uta.edu"))
					RunCrawler(link.attr("abs:href"));
			}
		}
		
	}

}
