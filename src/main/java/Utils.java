public class Utils {
    public static Consts.Commands getCommand(String data){
        if(data.equals("stop")) return Consts.Commands.STOP;
        if(data.equals("size")) return Consts.Commands.SIZE;
        if(data.equals("1970")) return Consts.Commands.TASK_FILE;
        if(data.equals("close_connection")) return Consts.Commands.CLOSE_CONNECTION;
        return null;
    }
}
