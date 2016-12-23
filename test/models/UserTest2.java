package models;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static play.test.Helpers.*;

public class UserTest2 {

	@Test
	public void createAndRetrieveUserByEmail() {
	  running(fakeApplication(inMemoryDatabase()), () -> {
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
      });	
   }
}
