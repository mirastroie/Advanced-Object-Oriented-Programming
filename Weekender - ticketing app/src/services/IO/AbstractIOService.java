package services.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractIOService<T> {


    public List<T> load(String FILE_NAME) {
        List<T> entities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                List<String> value = List.of(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                value = value
                        .stream()
                        .map(elem -> elem.replaceAll("\"", "").trim())
                        .collect(Collectors.toList());
                System.out.println(value);
                entities.add(parse(value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public abstract T parse(List<String> v);

    public abstract String unparse(T t);

    public void save(List<T> s, String FILE_NAME, String HEADER_FILE) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.append(HEADER_FILE).append("\n");
            for (T entity : s) {

                writer.append(unparse(entity)).append("\n");
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
