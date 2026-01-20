import model.*;
import service.ActivityService;

public class Main {
    public static void main(String[] args) {

        ActivityService activityService = new ActivityService();


        String sql = "SELECT * FROM routines WHERE name = Morning" and minutes;
        Prapared statement ps = con.preaparedStatement(sql);
        ps.SetString(1,"Morning");
        ResultSet rs = ps.executeQuery;
    }
}

