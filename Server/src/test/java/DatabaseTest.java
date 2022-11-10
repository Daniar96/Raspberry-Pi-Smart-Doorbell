import com.group17.JSONObjects.Temp;
import com.group17.server.database.DAO;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * NOTE:
 * Before running the tests make sure to change CREATE queries in database.Queries
 * to avoid messing up the database and SQL errors while running the tests.
 * It's sufficient to change a schema name to a new one, or disabling setup void is also an option
 * Make sure to connect rfid to rpi_id in allowed tags table manually if you run on a new schema
 */
public class DatabaseTest {
    private static final String rpiPass = "pass";
    private static final String rpiId =  "100";
    private static final String userEmail = "user";
    private static final String userPass = "password";
    private static final String rfid = "123456";

    @BeforeAll
    static void setup() throws SQLException {

        //DAO.registerUser(userEmail,userPass, rfid);
        //DAO.registerRpi(rpiId,rpiPass);
    }




    @Test
    void testSmoke() throws SQLException {
        DAO.smokeAlert(true,rpiId);
        Assertions.assertEquals(1, DAO.getSmoke(rpiId));
        DAO.smokeAlert(false,rpiId);
        Assertions.assertEquals(0, DAO.getSmoke(rpiId));

    }

    @Test
    void testFlame() throws SQLException {
        DAO.flameAlert(true,rpiId);
        Assertions.assertEquals(1, DAO.getFlame(rpiId));
        DAO.flameAlert(false,rpiId);
        Assertions.assertEquals(0, DAO.getFlame(rpiId));

    }
    @Test
    void testMic() throws SQLException {
        DAO.micAlert(true,rpiId);
        Assertions.assertEquals(1, DAO.getMic(rpiId));
        DAO.micAlert(false,rpiId);
        Assertions.assertEquals(0, DAO.getMic(rpiId));
    }
    @Test
    void testLog() throws SQLException {
        DAO.addLog("Test log, nothing has happened", rpiId);
        Assertions.assertTrue(DAO.getLogs(rpiId).get(0).getLog().contains("Test"));
    }

    @Test
    void testImage() throws SQLException{
        DAO.addImage(rpiId,"image 22.22.2222","image encoding emulator");
        String encoding = DAO.getImage(rpiId);
        Assertions.assertTrue(encoding.equals("image encoding emulator"));
    }

    @Test
    void testTemperature() throws SQLException {
        DAO.addTemp("50","40",rpiId);
        Temp tempinfo = DAO.getTemp(rpiId);
        Assertions.assertEquals("50", tempinfo.getTemp());
        Assertions.assertEquals("40", tempinfo.getHumidity());

    }



}
