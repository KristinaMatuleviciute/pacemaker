package controllers;

import javax.inject.Inject;

import models.Activity;
import models.Location;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;

public class Dashboard extends Controller {
	
	private static Form<Activity> activityForm;
	  

	  @Inject
	  public Dashboard(FormFactory formFactory) {
	     this.activityForm  = formFactory.form(Activity.class);
	    
	  }

	public Result index() {
		String email = session().get("email");
		User user = User.findByEmail(email);
		if (user != null){
		return ok(dashboard_main.render(user.activities));
		} else {
			return badRequest (accounts_login.render());
		}
	}
		

	public Result uploadActivityForm() {
		return ok(dashboard_uploadactivity.render());
	}
	
	public Result uploadLocationForm() {
		return ok(dashboard_uploadlocation.render());
	}

	public Result submitActivity() {
		Form<Activity> boundForm = activityForm.bindFromRequest();
		Activity activity = boundForm.get();

		if (boundForm.hasErrors()) {
			return badRequest();
		}

		String email = session().get("email");
		User user = User.findByEmail(email);

		user.activities.add(activity);
		user.save();
		return redirect(routes.Dashboard.index());
	}
	
	public Result submitLocation(Long activityId) {
		Result result = null;
		DynamicForm requestData = Form.form().bindFromRequest();
		String latitude = requestData.get("latitude");
		String longitude = requestData.get("longitude");
		if (latitude.matches("\\-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)")
				&& longitude.matches("\\-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)")) {
			float parsedLatitude = Float.parseFloat(latitude);
			float parsedLongitude = Float.parseFloat(longitude);
			Location location = new Location(parsedLatitude, parsedLongitude);
			Activity activity = Activity.findById(activityId);
			activity.locations.add(location);
			activity.save();
			result = redirect(routes.Dashboard.index());
		} else {
			result = redirect(routes.Dashboard.index());
		}
		return result;
	}
}
