package services.IO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {
    private static Audit service;
    protected final static String FILE_NAME = "data/audit.csv";

    public static Audit getAudit() {
        if (service == null)
            service = new Audit();
        return service;
    }

    public void addAction(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.append(message).append(", ").append(LocalDateTime.now().format(formatter)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
