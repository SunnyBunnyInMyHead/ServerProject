public interface Consts {



    enum Commands{
        STOP("stop"), SIZE("size"), TASK_FILE("1970"), CLOSE_CONNECTION("close_connection");

        private String command;

        Commands(String command) {
            this.command = command;
        }

        public String getCommand() {
            return command;
        }
    }
}
