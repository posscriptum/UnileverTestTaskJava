import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AreaChart<String, Integer> areaChart;

    @FXML
    private BarChart<String, Integer> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateChart();
        updateBarChart();
    }



    public void updateChart() {
        try {
            DbHandler db = DbHandler.getInstance();
            List<UnileverData> unileverDataList = db.getAllEntries();

            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            Integer index = 0;
            for (UnileverData item : unileverDataList) {
                index++;
                SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                series.getData().add(new XYChart.Data<>(formatDate.format(item.getDate()), item.getRandomValue()));
            }
            series.setName("Линейный график");
            areaChart.getData().setAll(series);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateBarChart() {
        try {
            DbHandler db = DbHandler.getInstance();
            List<UnileverData> unileverDataList = db.getAllEntries();

            XYChart.Series<String, Integer> seriesBarChart = new XYChart.Series<>();

            for (int i = 100; i <= 200; ++i) {
                int finalI = i;
                String x = String.format("%s",i);
                Integer y = (int)unileverDataList.stream().map(UnileverData::getRandomValue).filter(n -> n == finalI).count();
                seriesBarChart.getData().add(new XYChart.Data<>(x,y));
            }

            seriesBarChart.setName("Гистограмма");
            barChart.getData().setAll(seriesBarChart);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
