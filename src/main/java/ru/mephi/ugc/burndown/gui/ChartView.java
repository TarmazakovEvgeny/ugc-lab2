package ru.mephi.ugc.burndown.gui;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import ru.mephi.ugc.burndown.interfaces.TaskService;
import ru.mephi.ugc.burndown.model.Task;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ApplicationScoped
public class ChartView implements Serializable {

    private LineChartModel dateModelIdealSprint;
    private LineChartModel dateModelRealSprint;
    private List<Task> taskList;
    private ArrayList<Integer> complexityList;
    private Integer lengthSprint = 7;

    @NotNull(message = "Введите начало спринта")
    private Date startSprintDay;
    @NotNull(message = "Введите конец спринта")
    private Date finishSprintDay;

    private Integer sumComplexity = 0;

    private Boolean isRendered = false;

    @EJB
    private TaskService service;


    @PostConstruct
    public void init() {
        isRendered = true;
        //createDateModel();
    }

    public void updateChart() {
        dateModelIdealSprint = new LineChartModel();
        taskList = service.getTasksFromDB();
        complexityList = new ArrayList<Integer>();
        sumComplexity = 0;
        for (Task t : taskList) {
            complexityList.add(t.getComplexity());
            sumComplexity += t.getComplexity();
        }

        for (Iterator<Task> task = taskList.listIterator(); task.hasNext(); ) {
            Task t = task.next();
            if (t.getFinishDate() == null) {
                task.remove();
            }
        }
        Collections.sort(taskList, new Task());

        addIdealSprint();
        addRealSprint();

//        createDateModel();
    }

    private void addRealSprint() {
        LineChartSeries realSprint = new LineChartSeries();
        realSprint.setLabel("Real Sprint");

        realSprint.set(getTime(startSprintDay), sumComplexity);
        for (Task task : taskList) {
            sumComplexity -= task.getComplexity();
            realSprint.set(task.getFinishDate().toString(), sumComplexity);
        }
        if (sumComplexity == 0) {
            realSprint.set(getTime(finishSprintDay), sumComplexity);
        }
        if (sumComplexity != 0) {
            realSprint.set(getTime(finishSprintDay), 0);
        }
        dateModelIdealSprint.addSeries(realSprint);

        dateModelIdealSprint.getAxis(AxisType.Y).setLabel("Complexity");
        DateAxis axisX = new DateAxis("Dates");
        if (startSprintDay != null) {
            startSprintDay.setDate(startSprintDay.getDate() - 1);
            finishSprintDay.setDate(finishSprintDay.getDate() + 1);
        }
        axisX.setMin(getTime(startSprintDay));
        axisX.setMax(getTime(finishSprintDay));

        startSprintDay.setDate(startSprintDay.getDate() + 1);
        finishSprintDay.setDate(finishSprintDay.getDate() - 1);

        axisX.setTickFormat("%b %#d, %y");
        dateModelIdealSprint.getAxes().put(AxisType.X, axisX);
    }

    private void addIdealSprint() {
        LineChartSeries idealSprint = new LineChartSeries();
        idealSprint.setLabel("Ideal Sprint");

        idealSprint.set(getTime(startSprintDay), sumComplexity);
        idealSprint.set(getTime(finishSprintDay), 0);
        dateModelIdealSprint.addSeries(idealSprint);

        dateModelIdealSprint.getAxis(AxisType.Y).setLabel("Complexity");
        DateAxis axisX = new DateAxis("Dates");
        axisX.setTickFormat("%b %#d, %y");
        dateModelIdealSprint.getAxes().put(AxisType.X, axisX);
    }


    private void createDateModel() {
        dateModelIdealSprint = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set("2014-01-01", 51);
        series1.set("2014-01-06", 65);
        series1.set("2014-01-13", 74);
        series1.set("2014-01-30", 51);
        series1.set("2014-01-03", 65);

        dateModelIdealSprint.addSeries(series1);

        dateModelIdealSprint.getAxis(AxisType.Y).setLabel("Complexity");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");

        dateModelIdealSprint.getAxes().put(AxisType.X, axis);
    }

    public void addNewDate(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Sprint Date added", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private String getTime(Date date) {
        if (date != null) {
            String s = new String();
            String year = String.valueOf(date.getYear() + 1900);//возвращает 115
            String month = String.format("%02d", date.getMonth() + 1);// возвращает месяц на 1 меньше
            String day = String.format("%02d", date.getDate());
            s = year + "-" + month + "-" + day;
            return s;
        }
        return null;
    }

    public LineChartModel getDateModelIdealSprint() {
        updateChart();
        return dateModelIdealSprint;
    }

    public void addNewDate(ActionEvent actionEvent) {

        FacesMessage msg = new FacesMessage("Sprint Date added", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Date getStartSprintDay() {
        return startSprintDay;
    }

    public void setStartSprintDay(Date startSprintDay) {
        this.startSprintDay = startSprintDay;
    }

    public Date getFinishSprintDay() {
        return finishSprintDay;
    }

    public void setFinishSprintDay(Date finishSprintDay) {
        this.finishSprintDay = finishSprintDay;
    }
}
