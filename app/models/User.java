package models;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.MoreObjects.toStringHelper;
import com.google.common.base.Objects;

import javax.persistence.*;
import com.avaje.ebean.Model;

@Entity
@Table(name="my_user")
public class User extends Model
{
  @Id
  @GeneratedValue
  public Long   id;
  public String firstname;
  public String lastname;
  public String email;
  public String password;
  
  @OneToMany(cascade=CascadeType.ALL)
  public List<Activity> activities = new ArrayList<Activity>();

  public static Find<String, User> find = new Find<String, User>(){};

  public User()
  {
  }

  public User(String firstname, String lastname, String email, String password)
  {
    this.firstname = firstname;
    this.lastname  = lastname;
    this.email     = email;
    this.password  = password;
  }

  public void update (User user)
  {
    this.firstname = user.firstname;
    this.lastname  = user.lastname;
    this.email     = user.email;
    this.password  = user.password;
  }

  public String toString()
  {
    return toStringHelper(this)
        .add("Id", id)
        .add("Firstname", firstname)
        .add("Lastname", lastname)
        .add("Email", email)
        .add("Password", password).toString();
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof User)
    {
      final User other = (User) obj;
      return Objects.equal(firstname, other.firstname) 
          && Objects.equal(lastname, other.lastname)
          && Objects.equal(email, other.email);
    }
    else
    {
      return false;
    }
  }

  public static User findByEmail(String email)
  {
    return  User.find.where().eq("email", email).findUnique();
  }

  public static User findById(Long id)
  {
    return find.where().eq("id", id).findUnique();
  }

  public static List<User> findAll()
  {
    return find.all();
  }

  public static void deleteAll()
  {
    for (User user: User.findAll())
    {
      user.delete();
    }
  } 

}
