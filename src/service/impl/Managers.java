package service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.HistoryManager;
import adapter.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFileBackedTasksManager() throws IOException {
        return new FileBackedTasksManager("src//file//defaultFile.txt");
    }

    public static HttpTaskManager getDefault() throws IOException, InterruptedException {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static Gson getDefaultGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        Gson gson = gsonBuilder.create();

        return gson;
    }
}
