package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

@Entity
@Table(name = "my_activity")
public class Activity extends Model {

	@Id
	@GeneratedValue
	public Long id;

	public String type;
	public String location;
	public double distance;
	public static Find<String, Activity> find = new Find<String, Activity>() {
	};

	@OneToMany(cascade = CascadeType.ALL)
	public List<Location> locations = new ArrayList<Location>();

	public Activity() {
	}

	public Activity(String type, String location, double distance) {
		this.type = type;
		this.location = location;
		this.distance = distance;
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(id).addValue(type).addValue(location).addValue(distance).toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Activity) {
			final Activity other = (Activity) obj;
			return Objects.equal(type, other.type) && Objects.equal(location, other.location)
					&& Objects.equal(distance, other.distance);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.type, this.location, this.distance);
	}

	public static Activity findById(Long activityId) {
		return find.where().eq("id", activityId).findUnique();
	}

	public static List<Activity> findAll() {
		return find.all();
	}

	public void update(Activity activity) {
		this.type = activity.type;
		this.location = activity.location;
		this.distance = activity.distance;
	}

	public static void deleteAll() {
		for (Activity activity : Activity.findAll()) {
			activity.delete();
		}
	}

}
