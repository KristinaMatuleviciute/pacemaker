package models;

public class Fixtures {

	  public static String userJson = "{\n"
              +  "\"email\"    : \"jim@simpson.com\" ,\n"
              +  "\"firstName\": \"Jim\"             ,\n"
              +  "\"lastName\" : \"Simpson\"         ,\n"
              +  "\"password\" : \"secret\"           \n"
         + "}";

      public static User users[] = { 
          new User ("homer",  "simpson", "homer@simpson.com",  "secret"),
          new User ("lisa",   "simpson", "lisa@simpson.com",   "secret"),
          new User ("maggie", "simpson", "maggie@simpson.com", "secret"),
          new User ("bart",   "simpson", "bart@simpson.com",   "secret"),
          new User ("marge",  "simpson", "marge@simpson.com",  "secret"),
        };

      public static String activityJson  = "{\n"
              +  "\"type\"      : \"run\"                 ,\n"
              +  "\"location\"  : \"Dunmore\"             ,\n"
              +  "\"distance\"  : 3                        \n"
         + "}";

}
