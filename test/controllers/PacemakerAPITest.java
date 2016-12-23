package controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import models.Activity;
import models.Fixtures;
import models.User;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http.RequestBuilder;

import static play.test.Helpers.DELETE;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static org.hamcrest.CoreMatchers.equalTo;
import play.mvc.Result;
import play.test.WithApplication;

public class PacemakerAPITest extends WithApplication{

	@Test
    //This route test should call the createUser() method in PacemakerAPI
    public void POSTOnApiUsersRouteCreatesUser() {
		
      //ensure the database is empty before exercising tests
      assertThat(User.findAll().size(), equalTo(0));	      
      
      //Invoke the createUser() method and ensure the HTTP status returned was OK
      assertThat(addUser().status(), equalTo(OK));
      
      //ensure the database has one user after exercising tests
      assertThat(User.findAll().size(), equalTo(1));
	}
	
	@Test
    //This route test should call the createActivity() method in PacemakerAPI
	//and add the activity for a specific user  
    public void POSTOnApiUsersActivityRouteCreatesActivity() {
		
      //ensure the User and Activity tables are empty before exercising tests
      assertThat(User.findAll().size(), equalTo(0));	      
      assertThat(Activity.findAll().size(), equalTo(0));
      
	  //First, invoke createUser() to create a user whom we will add activities to
      assertThat(addUser().status(), equalTo(OK));
      assertThat(User.findAll().size(), equalTo(1));
      
      //Now that the user is added successfully, we can exercise the activity test
      assertThat(addActivityToUser().status(), equalTo(OK));
      assertThat(Activity.findAll().size(), equalTo(1));
    }
    
	@Test
    public void DELETEOnApiUsersIdRouteShouldDeleteUserAndAssociatedActivity() {
      //ensure the User and Activity tables are empty before exercising tests
      assertThat(User.findAll().size(), equalTo(0));	      
      assertThat(Activity.findAll().size(), equalTo(0));
	      
	  //First, invoke createUser() to create a user whom we will add activities to
      assertThat(addUser().status(), equalTo(OK));
      assertThat(User.findAll().size(), equalTo(1));
      
      //Second, invoke createActivity to add an activity to the user above
      assertThat(addActivityToUser().status(), equalTo(OK));
      assertThat(Activity.findAll().size(), equalTo(1));
		
	  //Now invoke deleteUser(Long id) and test both the user and the activity are deleted
      RequestBuilder deleteRequest = new RequestBuilder()
              .method(DELETE)
              .uri("/api/users/1");
      Result deleteResult = route(deleteRequest);
      assertThat(deleteResult.status(), equalTo(OK));
   
      assertThat(User.findAll().size(), equalTo(0));
      assertThat(Activity.findAll().size(), equalTo(0));
    }
    
	@Test
    public void DELETEOnApiUsersRouteShouldExist() {
        RequestBuilder request = new RequestBuilder()
                .method(DELETE)
                .uri("/api/users");

        Result result = route(request);
        assertThat(result.status(), equalTo(OK));
    }

	@Test
    public void GETOnApiUsersRouteShouldExist() {
        Result result = route(fakeRequest(GET, "/api/users"));
        assertThat(result.status(), equalTo(OK));
    }
    
    @Test
    public void GETOnDummyRouteShouldNotBeFound() {
        Result result = route(fakeRequest(GET, "/api/blah"));
        assertThat(result.status(), equalTo(NOT_FOUND));
    }
  
    private Result addUser()
    {
        RequestBuilder userRequest = new RequestBuilder()
                .method(POST)
                .uri("/api/users")
                .bodyJson(Json.parse(Fixtures.userJson));
       return route(userRequest);
    }

    private Result addActivityToUser()
    {
        RequestBuilder activityRequest = new RequestBuilder()
                .method(POST)
                .uri("/api/users/1/activities")
                .bodyJson(Json.parse(Fixtures.activityJson));
        return route(activityRequest);    	
    }
}


