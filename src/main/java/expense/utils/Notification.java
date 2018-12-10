package expense.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ovidiu on 10-Dec-18.
 */
public class Notification {

  private Notification() {
  }

  public static Map<String, String> build(String type, String text) {
    Map<String, String> notificationMap = new HashMap<>();
    notificationMap.put("type", type);
    notificationMap.put("text", text);
    return notificationMap;
  }

}
