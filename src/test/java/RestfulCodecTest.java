import com.podcrash.service.communication.Restful;
import org.junit.Test;

public class RestfulCodecTest {

    @Test
    public void testMain() {
        //
    }

    class CodecTest implements Restful {

        @Endpoint(route = "/string", reqType = RequestType.GET)
        public String getStrings() {
            return "Test";
        }
    }
}
