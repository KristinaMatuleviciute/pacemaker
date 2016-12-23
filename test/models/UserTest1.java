package models;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import play.Application;
import play.test.Helpers;

public class UserTest1 {

	public static Application fakeApp; 

	  public static String createDdl = "";
	  public static String dropDdl = "";

	  @BeforeClass
	  public static void startApp() throws IOException {
	    fakeApp = Helpers.fakeApplication(Helpers.inMemoryDatabase());
	    Helpers.start(fakeApp);
	    // Reading the evolution file
	    String evolutionContent = FileUtils.readFileToString( 
	    fakeApp.getWrappedApplication().getFile("conf/evolutions/default/1.sql"));
	    // Splitting the String to get Create & Drop DDL
	    String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
	    String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
	    createDdl = upsDowns[0];
	    dropDdl = upsDowns[1];
	  }

	  @AfterClass
	  public static void stopApp(){
	    Helpers.stop(fakeApp);
	  }

	  @Before
	  public void createCleanDb() {
	    Ebean.execute(Ebean.createCallableSql(dropDdl));
	    Ebean.execute(Ebean.createCallableSql(createDdl));
	  } 


	@Test
	public void createAndRetrieveUserByEmail() {
		
	    // Create a new user and save it
	    new User("Joe", "Soap", "joesoap@gmail.com", "secret").save();
	    
	    // Retrieve the user with e-mail address joesoap@gmail.com
	    User joesoap = User.findByEmail("joesoap@gmail.com");
	    
	    // Test 
	    assertNotNull(joesoap);
	    assertThat("Joe", equalTo(joesoap.firstname));
	    assertThat("Soap", equalTo(joesoap.lastname));
	    assertThat("joesoap@gmail.com", equalTo(joesoap.email));
	    assertThat("secret", equalTo(joesoap.password));
	}		
	
	@Test
	public void deleteAllUsersResultsInEmptyUserTable() {

	  //Assert that the user table is empty
	  assertThat(User.findAll().size(), equalTo(0));

	  // Add all the users listed in the Fixtures class to the user table
	  for (User user : Fixtures.users){
	    new User(user.firstname, 
	             user.lastname, 
	             user.email, 
	             user.password)
	    		.save();
	  }
	  
	  //Ensure all were added successfully
	  assertThat(User.findAll().size(), equalTo(Fixtures.users.length));

	  //Delete all the users that were just added
	  User.deleteAll();

	  //Assert that the user table is once again empty
	  assertThat(User.findAll().size(), equalTo(0));
	}

}
