package models;

import static com.google.common.base.MoreObjects.toStringHelper;

//import java.util.ArrayList;
//import java.util.List;

import com.google.common.base.Objects;

import play.data.validation.Constraints.Required;

import javax.persistence.*;
import com.avaje.ebean.Model;
//import com.avaje.ebean.Model.Find;

@Entity
@Table(name="my_location")
public class Location extends Model
{
  @Id
  @GeneratedValue
  public Long id;
  @Required
  public float latitude;
  @Required
  public float longitude;
  
  public static Find<String, Location> find = new Find<String, Location>(){};

  public Location()
  {
  }

  public Location (float latitude, float longitude)
  {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Location)
    {
      final Location other = (Location) obj;
      return Objects.equal(latitude, other.latitude) 
          && Objects.equal(longitude, other.longitude);
    }
    else
    {
      return false;
    }
  }
  
  @Override
  public String toString()
  {
    return toStringHelper(this)
        .add("Latitude", latitude)
        .add("Longitude", longitude).toString();
  }

  public static Location findById(Long id) {
	return find.where().eq("id", id).findUnique();
  }
}