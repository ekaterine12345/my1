package connection;

import com.example.preperation_final_1.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarUtil {
    public static void createTable() throws SQLException {
        String createSql = "CREATE TABLE IF NOT EXISTS CARS1_TB"+
                "(ID INTEGER NOT NULL AUTO_INCREMENT," +
                "Mark VARCHAR(255), " +
                "Model VARCHAR(255), " +
                "Year INTEGER NOT NULL, "+
                "Speed INTEGER NOT NULL, "+
                "Engine FlOAT NOT NULL, "+
                "PRIMARY KEY (ID))";
        JDBCUtil.getStatement().executeUpdate(createSql);

        System.out.println("Created Table in given database if it is not exists");
    }

    public static void insertItem(Car car) throws SQLException {
        String insertSql = "INSERT INTO CARS1_TB(Mark, Model, Year, Speed, Engine) VALUES( "
                +"'"+car.getMark()+"', "
                +"'"+car.getModel()+"', "
                +"'"+car.getYear()+"', "
                +"'"+car.getSpeed()+"', "
                +"'"+car.getEngine()+"' )";

        JDBCUtil.getStatement().executeUpdate(insertSql);
        System.out.println("inserted given element successfully");
    }

    public static Map<Float, Long> getMappedData() throws SQLException {
        String selectSQL = "SELECT * FROM CARS1_TB";
        List<Car> myCars = new ArrayList<>();

        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectSQL);
        while (resultSet.next()){
            myCars.add(new Car(resultSet.getString("Mark"),
                    resultSet.getString("Model"),
                    resultSet.getInt("Year"),
                    resultSet.getInt("Speed"),
                    resultSet.getFloat("Engine")));
        }
        Map<Float, Long> myMap = myCars.stream()
                .collect(Collectors.groupingBy(Car::getEngine, Collectors.counting()));

        System.out.println(myMap);
        return myMap;
    }

    public static Map<Integer, Integer> getMappedData2() throws SQLException {
        String selectSql = "SELECT * FROM CARS1_TB";
        List<Car> myCars = new ArrayList<>();

        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectSql);
        while(resultSet.next()){
            myCars.add(new Car(resultSet.getString("Mark"),
                    resultSet.getString("Model"),
                    resultSet.getInt("Year"),
                    resultSet.getInt("Speed"),
                    resultSet.getFloat("Engine")));
        }

        Map<Integer, Integer> myMap = myCars.stream()
                .collect(Collectors.groupingBy(Car::getSpeed, Collectors.summingInt(Car::getYear)));

        System.out.println("new myMap "+ myMap);
        return myMap;
    }

    public static ObservableList<PieChart.Data> getData() throws SQLException {
        Map<Float, Long> map = getMappedData();
        ObservableList<PieChart.Data> myList = FXCollections.observableArrayList(
        );

        for (Map.Entry<Float, Long> entry : map.entrySet()) {
            //  System.out.println(entry.getKey() + ":" + entry.getValue());
            myList.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
        }

        return myList;
    }

    public static XYChart.Series<String,Long> getDataforBarChart() throws SQLException {
        Map<Float, Long> map = getMappedData();
        XYChart.Series series = new XYChart.Series();
        for (Float k : map.keySet()){
            series.getData().add(new XYChart.Data(k.toString(), map.get(k)));
        }
        return series;
    }

    public static XYChart.Series getDataforAreaChart() throws SQLException {
        Map<Float, Long> map = getMappedData();
        XYChart.Series series = new XYChart.Series();
        for (Float k : map.keySet()){
            series.getData().add(new XYChart.Data(k, map.get(k)));
            System.out.println(k+" "+map.get(k));
        }
        return series;
    }

    public static XYChart.Series getDataforLineChart() throws SQLException {
        Map<Integer, Integer> map = getMappedData2();
        XYChart.Series series = new XYChart.Series();
        for (int k : map.keySet()){
            series.getData().add(new XYChart.Data(k, map.get(k)));
        }
        return series;
    }
}
