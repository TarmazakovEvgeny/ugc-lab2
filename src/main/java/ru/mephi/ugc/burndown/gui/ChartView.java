package ru.mephi.ugc.burndown.gui;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import ru.mephi.ugc.burndown.interfaces.TaskService;
import ru.mephi.ugc.burndown.model.Task;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ManagedBean
public class ChartView implements Serializable {

    private LineChartModel dateModelIdealSprint;
    private LineChartModel dateModelRealSprint;
    private List<Task> taskList;
    private ArrayList<Integer> complexityList;
    private Integer lengthSprint = 7;

    private Date startSprintDay = new Date(2015, 12, 2);
    private Date finishSprintDay = new Date(2015, 12, 19);

    @EJB
    private TaskService service;

    private Integer sumComplexity = 0;

    @PostConstruct
    public void init() {
        updateChart();
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
        dateModelIdealSprint.addSeries(realSprint);

        dateModelIdealSprint.getAxis(AxisType.Y).setLabel("Complexity");
        DateAxis axisX = new DateAxis("Dates");
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

    private String getTime(Date date) {
        String s = new String();
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonth());
        String day = String.format("%02d", date.getDate());
        s = year + "-" + month + "-" + day;
        return s;
    }

    public LineChartModel getDateModelIdealSprint() {
        updateChart();
        return dateModelIdealSprint;
    }
}
