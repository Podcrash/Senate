import com.google.gson.JsonObject;
import com.podcrash.service.communication.Restful;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

public class RestfulCodecTest {

    @Test
    public void testMain() {
        CodecTest restful = new CodecTest();
        Method[] methods = restful.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Restful.Endpoint.class))
                continue;
            if (method.getParameterCount() != 1)
                continue;
            if (method.getParameterTypes()[0] != JsonObject.class)
                continue;
            Restful.Endpoint endpoint = method.getDeclaredAnnotation(Restful.Endpoint.class);
            assertEquals("getStrings", method.getName());
            assertEquals("/string", endpoint.route());
            assertEquals(Restful.RequestType.GET, endpoint.reqType());
        }
    }

    static class CodecTest implements Restful {

        @Endpoint(route = "/string", reqType = RequestType.GET)
        public String getStrings(JsonObject jsonObject) {
            return "Test";
        }


        @Endpoint(route = "/double", reqType = RequestType.GET)
        public double getDouble() {
            return 1;
        }

        public int getInt() {
            return 1;
        }
    }
}
