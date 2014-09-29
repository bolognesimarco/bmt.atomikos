package mio.bmt.atomikos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import javax.transaction.UserTransaction;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

public class ProvaXA {

	public static void main(String[] args) throws Exception{
		ProvaXA p = new ProvaXA();
		p.setUp();
		
		UserTransaction ut = new UserTransactionImp();
		ut.begin();
		
		Connection c1 = p.ds1.getConnection();
		Connection c2 = p.ds2.getConnection();
		
		PreparedStatement statement1 = c1.prepareStatement("insert into acidrest (rest,temp) values (?,?)");
		statement1.setInt(1,1);
		statement1.setInt(2,1);
		statement1.executeUpdate();
		
		PreparedStatement statement2 = c2.prepareStatement("insert into canale (id) values (?)");
		statement2.setInt(1,2);
		statement2.executeUpdate();
		
		
		c1.close();
		c2.close();
		ut.commit();
	}
	
	public AtomikosDataSourceBean ds1 = new AtomikosDataSourceBean();
	public AtomikosDataSourceBean ds2 = new AtomikosDataSourceBean();
	private void setUp() throws Exception{
		ds1.setUniqueResourceName("uno"); 
		ds1.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"); 
		Properties p1 = new Properties(); 
		p1.setProperty ( "user" , "root" ); 
		p1.setProperty ( "password" , "root" ); 
		p1.setProperty ( "URL" , "jdbc:mysql://localhost:3306/mio" ); 
		ds1.setXaProperties ( p1 ); 
		ds1.setPoolSize ( 5 );
		
		ds2.setUniqueResourceName("due"); 
		ds2.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"); 
		Properties p2 = new Properties(); 
		p2.setProperty ( "user" , "root" ); 
		p2.setProperty ( "password" , "root" ); 
		p2.setProperty ( "URL" , "jdbc:mysql://localhost:3306/crm" ); 
		ds2.setXaProperties ( p2 ); 
		ds2.setPoolSize ( 5 );
	}

}
