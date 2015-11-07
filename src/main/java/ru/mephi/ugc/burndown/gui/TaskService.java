package ru.mephi.ugc.burndown.gui;

import ru.mephi.ugc.burndown.model.Task;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name = "taskService")
@ApplicationScoped
public class TaskService {

    private final static String[] statuses;

    private final static String[] colors;
    private final static String[] brands;

    static {

        statuses = new String[2];
        statuses[0] = "Запланировано";
        statuses[1] = "Выполнено";

        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";

        brands = new String[10];
        brands[0] = "BMW";
        brands[1] = "Mercedes";
        brands[2] = "Volvo";
        brands[3] = "Audi";
        brands[4] = "Renault";
        brands[5] = "Fiat";
        brands[6] = "Volkswagen";
        brands[7] = "Honda";
        brands[8] = "Jaguar";
        brands[9] = "Ford";
    }

    public List<Task> createCars(int size) {
        List<Task> list = new ArrayList<Task>();
        for (int i = 0; i < size; i++) {
            list.add(new Task(getRandomId(), getRandomBrand(), i, "Запланировано"));
        }

        return list;
    }

    private int getRandomId() {
        return (int) Math.random() * 100;
    }

    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }

    private String getRandomColor() {
        return colors[(int) (Math.random() * 10)];
    }

    private String getRandomBrand() {
        return brands[(int) (Math.random() * 10)];
    }

    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }

    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true : false;
    }

    public List<String> getColors() {
        return Arrays.asList(colors);
    }

    public List<String> getBrands() {
        return Arrays.asList(brands);
    }

    public List<String> getStatuses() {
        return Arrays.asList(statuses);
    }
}
