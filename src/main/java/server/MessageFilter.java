package server;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageFilter {

    public static void main(String[] args) {
        Response x = filterMessage("SVR GAME MATCH {PLAYERTOMOVE: \"teamZuiver\", GAMETYPE: \"Tic-tac-toe\", OPPONENT: \"HanzeExperience\"}");
        System.out.println(x.getCommand());
    }

    public static Response filterMessage(String message) {
        String rawCommand = message.substring(9, message.indexOf("{")-1);
        String rawMessage = message.substring(message.indexOf("{"), message.length());

        return new Response(getCommandType(rawCommand), decodeJson(rawMessage));
    }

    private static Command getCommandType(String rawCommand) {
        try{
            return Command.valueOf(rawCommand);
        }catch (IllegalArgumentException e) {
            return Command.UNKNOWN_COMMAND;
        }
    }

    private static JSONObject decodeJson(String rawContent) {
        try {
            return new JSONObject(rawContent);
        } catch (JSONException e) {
            throw new RuntimeException("Couldn't decode json " + rawContent, e);
        }
    }
}