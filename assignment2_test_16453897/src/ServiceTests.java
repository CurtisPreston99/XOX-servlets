import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class ServiceTests {
	public static final String SERVER_URL = "http://localhost:8080/assignment2_server_16453897/ttt/";
	
	
	//makes sure block is filled when move is called
	@Test
	void moveDone() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		getWebpage(SERVER_URL+"istart",httpclient);
		getWebpage(SERVER_URL+"move/x1y1",httpclient);
		String check=getWebpage(SERVER_URL+"possiblemoves",httpclient);
		String[] cords=check.split(String.format("(?<=\\G.{%1$d})", 3));
		for(String s:cords) {
		assert(!s.equals("1,1"));
		}
		httpclient.close();
	}
	//makes sure Ai fills a block when ustart is called
	@Test
	void AIgoestFirst() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		getWebpage(SERVER_URL+"ustart",httpclient);
		String check=getWebpage(SERVER_URL+"possiblemoves",httpclient);
		String[] cords=check.split(String.format("(?<=\\G.{%1$d})", 3));
		assert(cords.length==8);
		httpclient.close();
	}
	
	@Test
	void BadXMove() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(SERVER_URL+"ustart");
		CloseableHttpResponse response = httpclient.execute(httpget);
		httpget = new HttpGet(SERVER_URL+"move/x4y1");
		response = httpclient.execute(httpget);
		String code=response.getStatusLine().toString();
		assert("HTTP/1.1 400 Bad Request".equals(code));
		response.close();
		httpclient.close();
	}
	
	@Test
	void BadYMove() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(SERVER_URL+"ustart");
		CloseableHttpResponse response = httpclient.execute(httpget);
		httpget = new HttpGet(SERVER_URL+"move/x1y4");
		response = httpclient.execute(httpget);
		String code=response.getStatusLine().toString();
		assert("HTTP/1.1 400 Bad Request".equals(code));
		response.close();
		httpclient.close();
	}
	
	@Test
	void BadMove() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(SERVER_URL+"ustart");
		CloseableHttpResponse response = httpclient.execute(httpget);
		httpget = new HttpGet(SERVER_URL+"move/lolnope");
		response = httpclient.execute(httpget);
		String code=response.getStatusLine().toString();
		assert("HTTP/1.1 400 Bad Request".equals(code));
		response.close();
		httpclient.close();
	}
	
	@Test
	void MoveWithoutSession() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(SERVER_URL+"move/x1y1");
		CloseableHttpResponse response = httpclient.execute(httpget);
		response = httpclient.execute(httpget);
		String code=response.getStatusLine().toString();
		assert("HTTP/1.1 404 Not Found".equals(code));
		response.close();
		httpclient.close();
	}
	
	@Test
	void blankBoardpossiblemoves() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		getWebpage(SERVER_URL+"istart",httpclient);
		String check=getWebpage(SERVER_URL+"possiblemoves",httpclient);
		String[] cords=check.split(String.format("(?<=\\G.{%1$d})", 3));
		assert(cords.length==9);
		httpclient.close();
	}
	
	@Test
	void blankBoardstateTxt() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		getWebpage(SERVER_URL+"istart",httpclient);
		String check=getWebpage(SERVER_URL+"state?format=txt",httpclient);
		String[] cords=check.split(String.format("(?<=\\G.{%1$d})", 3));
		for(String line:cords) {
			assert(line.equals("___"));
		}
		httpclient.close();
	}
	
	@Test
	void statePNG() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(SERVER_URL+"ustart");
		CloseableHttpResponse response = httpclient.execute(httpget);
		httpget = new HttpGet(SERVER_URL+"state?format=png");
		response = httpclient.execute(httpget);
		String type=response.getEntity().getContentType().toString();
		assert("Content-Type: image/png".equals(type));
		response.close();
		httpclient.close();
	}
	
	@Test
	void wonEmptyBoard() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		getWebpage(SERVER_URL+"istart",httpclient);
		String won=	getWebpage(SERVER_URL+"won",httpclient);
		assert(won.equals("none"));
	}
	
	
	@Test
	void wonFullBoard() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		getWebpage(SERVER_URL+"ustart",httpclient);
		for(int i=0;i<4;i++) {
			String[] check=getWebpage(SERVER_URL+"possiblemoves",httpclient).split(String.format("(?<=\\G.{%1$d})", 3));
			String go=check[0];
			String newurl=SERVER_URL+"move/x"+go.charAt(0)+"y"+go.charAt(2);
			getWebpage(newurl,httpclient);
		}

		String won=	getWebpage(SERVER_URL+"won",httpclient);
		String[] outcomes= {"computer","user","draw"};
		List<String> listcomes= Arrays.asList(outcomes);
		assert(listcomes.contains(won));
	}
	
	@Test
	void wonNoGame() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(SERVER_URL+"won");
		CloseableHttpResponse response = httpclient.execute(httpget);
		response = httpclient.execute(httpget);
		String code=response.getStatusLine().toString();
		assert("HTTP/1.1 404 Not Found".equals(code));
		response.close();
		httpclient.close();
		
	}
	
	
	
	
	//just a method to get webpage's
	static String getWebpage(String url,CloseableHttpClient httpclient) throws ClientProtocolException, IOException {


		HttpGet httpget = new HttpGet(url);
//		httpget.addHeader(arg0);
		
		CloseableHttpResponse response = httpclient.execute(httpget);
		
		System.out.println(response.getEntity().getContentType());
	    Scanner sc = new Scanner(response.getEntity().getContent());
	    
	    String result="";
	    while(sc.hasNext()) {
	    	result+=sc.nextLine();
	      }
	    response.close();
	    
	    return result;
		
	}
	
}
