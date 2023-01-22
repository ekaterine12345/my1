package connection;

import com.example.preperation_final_1.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CarUtilTest {
    @BeforeEach
    void setUp() {
        System.out.println(" before each");
    }

    @AfterEach
    void tearDown() {
        System.out.println(" after each");
    }

    @Test
    public  void createTableTest() throws SQLException {
        CarUtil.createTable();
        DatabaseMetaData dbm = JDBCUtil.getConnection().getMetaData();
// check if "CARS1_TB" table is there
        ResultSet tables = dbm.getTables(null, null, "CARS1_TB", null);
        Assertions.assertEquals(tables.next(), true, "not correct");
    }

    @Test
    public void insertTest() throws SQLException {
        String selectTable = "SELECT COUNT(*) FROM CARS1_TB";
        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectTable);
        resultSet.next();
        int count1 = resultSet.getInt(1);
        System.out.println();

        CarUtil.insertItem(new Car("ma", "mo", 2000, 43, 5.7f));
        resultSet = JDBCUtil.getStatement().executeQuery(selectTable);
        resultSet.next();
        int count2 = resultSet.getInt(1);
        Assertions.assertEquals(count2-count1, 1, "insert error :(");
        System.out.println(count2+" "+count1);
        System.out.println("insert test was successfully completed!");
    }

    @Test
    public void getMapDataTest() throws SQLException {

        String selectData = "SELECT Engine,COUNT(*) FROM CARS1_TB GROUP BY Engine";

        Map<Float, Long> map = new HashMap<>();
        ResultSet resultSet = JDBCUtil.getStatement().executeQuery(selectData);
        while (resultSet.next()){
            map.put(resultSet.getFloat("Engine"),
                    resultSet.getLong("COUNT(*)"));
        }

        Map<Float, Long> map2 = CarUtil.getMappedData();

        System.out.println(map);
        System.out.println(map2);
     //   for (Float k: map.keySet())

        Assertions.assertEquals(map, map2, "getMappedData nw");
        System.out.println("GetMapData test was successfully completed");
    }

    @Test
    public void getDataTest() throws SQLException {
        Map<Float, Long> map = CarUtil.getMappedData();
        ObservableList<PieChart.Data> myList = FXCollections.observableArrayList();

        for (Float engine : map.keySet()) {
            myList.add(new PieChart.Data(engine.toString(), map.get(engine)));
        }

        ObservableList<PieChart.Data> list = CarUtil.getData();

        for (int i=0; i<list.size(); i++) {
            Assertions.assertEquals(list.get(i).toString(), myList.get(i).toString(), "getData not working "+i);
        }
        System.out.println("GetData test was successfully completed");
    }
}
