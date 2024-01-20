package framework.server.communication;

import org.json.JSONException;
import org.json.JSONObject;

public class Response {
    private Command command;
    private JSONObject content;

    public Response(Command command, JSONObject content) {
        this.command = command;
        this.content = content;
    }

    public Command getCommand() {
        return command;
    }

    public JSONObject getContent() {
        return content;
    }

    public String getStringValue(String key) {
        try {
            return content.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    public int getIntValue(String key) {
        try {
            return content.getInt(key);
        } catch (JSONException e) {
            return -1;
        }
    }
}