package models;

import static org.junit.Assert.*;
import org.junit.Test;

import parsers.JsonParser;
import play.test.WithApplication;
import static org.hamcrest.CoreMatchers.*;

public class ActivityTest extends WithApplication{
//automatically ensures that a fake application is started
//and stopped for each test method.
		
	@Test
	public void createAndRetrieveActivityForUser() {
		// Create a new user and save it in the database
	    new User("Jim", "Simpson", "jim@simpson.com", "secret").save();
	    User joesoap = User.findByEmail("jim@simpson.com");
	    joesoap.activities.add(new Activity("Run", "Tramore", 12.33));
		joesoap.save();
		
	    // Retrieve the activity we just added; id should be 1
	    Activity activity = Activity.findById(1l);
	      
	    //Test that the fields in the returned activity was set up correctly
	    assertNotNull(activity);
		assertThat("Run", equalTo(activity.type));
		assertThat("Tramore", equalTo(activity.location));
		assertThat(12.33, equalTo(activity.distance));
	}
}