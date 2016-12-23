package controllers;

import javax.inject.Inject;

import models.User;
import play.*;
import play.mvc.*;

import views.html.*;

import play.data.FormFactory; 
import play.data.Form;


public class Accounts extends Controller
{  
 
  private static Form<User> userForm;
  private static Form<User> loginForm;

  @Inject
  public Accounts(FormFactory formFactory) {
     this.userForm  = formFactory.form(User.class);
     this.loginForm = formFactory.form(User.class);
  }
 
  public Result index()
  {
    return ok(welcome_main.render());
  }

  public Result signup()
  {
    return ok(accounts_signup.render());
  }
  
  public Result login()
  {
    return ok(accounts_login.render());
  }
  
  public Result logout()
  {
    session().clear();
    return ok(welcome_main.render());
  }

  public Result register()
  {
	Form<User> boundForm = userForm.bindFromRequest();    
	if(loginForm.hasErrors()) 
    {
      return badRequest(accounts_login.render());
    }
    else
    {
      User user = boundForm.get();
      Logger.info ("User = " + user.toString());
      user.save();
      return ok(welcome_main.render());
    }
  }

  public Result authenticate() 
  {
	Form<User> boundForm = loginForm.bindFromRequest();    
	
	if(loginForm.hasErrors()) 
    {
      return badRequest(accounts_login.render());
    } 
    else 
    {	
       session("email", boundForm.get().email);
       return redirect(routes.Dashboard.index());
    }
  }
}
