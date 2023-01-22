package com.example.preperation_final_1;

import connection.CarUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
    //    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CarUtil.createTable();
        CarUtil.getMappedData();
        CarUtil.getMappedData2();
        Label label1 = new Label("mark");
        Label label2 = new Label("model");
        Label label3 = new Label("year");
        Label label4 = new Label("speed");
        Label label5 = new Label("engine (float)");

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        TextField textField5 = new TextField();

        Button button = new Button("Add Item");

        PieChart pieChart = new PieChart();
        pieChart.setData(CarUtil.getData());
        pieChart.setTranslateX(100);
        pieChart.setTranslateY(200);

        //BarChart
        CategoryAxis xaxis= new CategoryAxis(); // aucileblad CategoryAxis unda ikos
        NumberAxis yaxis = new NumberAxis(0,10,1);
        BarChart<String,Long> barchart = new BarChart(xaxis,yaxis);
         barchart.getData().add(CarUtil.getDataforBarChart());
        //BarChart

        //AreaChart
        NumberAxis xaxis1 = new NumberAxis(0,10,1);
        NumberAxis yaxis1 = new NumberAxis(0,10,1);
        AreaChart<Float,Long> areaChart = new AreaChart(xaxis1,yaxis1);

//        XYChart.Series seriesSouth = new XYChart.Series();
//        seriesSouth.getData().add(new XYChart.Data(3,2));
//        seriesSouth.getData().add(new XYChart.Data(2,1));

        System.out.println(CarUtil.getDataforAreaChart());
        areaChart.getData().add(CarUtil.getDataforAreaChart());
      //  areaChart.getData().add(seriesSouth);
        //AreaChart

        //LineChart

        final NumberAxis xaxis2 = new NumberAxis(0,50,1);
        final NumberAxis yaxis2 = new NumberAxis(10,4020,10);

        LineChart lineChart = new LineChart<>(xaxis2, yaxis2);
        lineChart.getData().add(CarUtil.getDataforLineChart());
        //LineChart
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("clicked");
                String mark = textField1.getText();
                String model = textField2.getText();
                int year = Integer.parseInt(textField3.getText());
                int speed = Integer.parseInt(textField4.getText());
                float engine = Float.parseFloat(textField5.getText());
                Car myCar = new Car(mark, model, year, speed, engine);
                try {
                    CarUtil.insertItem(myCar);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        GridPane root = new GridPane();
        root.addRow(0, label1, textField1);
        root.addRow(1, label2, textField2);
        root.addRow(2, label3, textField3);
        root.addRow(3, label4, textField4);
        root.addRow(4, label5, textField5);
        root.addRow(5, button);
      //  root.addRow(10, pieChart);
      //  root.addRow(10, barchart);
      //   root.addRow(10, areaChart);
        root.addRow(10, lineChart);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
        Car car = new Car(1L, "mark1", "model1", 2000, 4, 100.0f);
        Car car1 = new Car(1L, "mark1", "model1", 2000, 4, 5.0f);
        Car car3 = new Car("mmkmk", "dewdd", 32323, 3, 1.4f);
        car.setEngine(5);
        System.out.println(car);
        System.out.println(car.equals(car1));
        System.out.println(car3);
    }
}