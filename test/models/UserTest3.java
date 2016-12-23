package models;

import static org.junit.Assert.*;
import org.junit.Test;
import play.test.WithApplication;
import static org.hamcrest.CoreMatchers.*;
import static play.test.Helpers.*;

public class UserTest3 extends WithApplication{
//automatically ensures that a fake application is started
//and stopped for each test method.
	
  @Test
  public void createAndRetrieveUserByEmail() {
	
	// Create a new user and save it
	new User("Joe", "Soap", "joesoap@gmail.com", "secret").save();
	
	//Retrieve the user with e-mail address joesoap@gmail.com
	User joesoap = User.findByEmail("joesoap@gmail.com");
	
	// Test 
	assertNotNull(joesoap);
	assertThat("Joe", equalTo(joesoap.firstname));
	assertThat("Soap", equalTo(joesoap.lastname));
	assertThat("joesoap@gmail.com", equalTo(joesoap.email));
	assertThat("secret", equalTo(joesoap.password));
  }
}



