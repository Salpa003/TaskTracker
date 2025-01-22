package main.manager;

public  class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileBackedManager() {
        return new FileBackedTasksManager("src/CSV.txt");
    }
}
