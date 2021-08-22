import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.IntStream;

public class UnileverCharts extends Application {

    public static void main(String[] args) {
        ArrayList<UnileverData> unileverDataArrayList = new ArrayList<>();
        filling1000elements(unileverDataArrayList);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Charts.fxml"));
        primaryStage.setTitle("Charts for Unilever");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private static void filling1000elements(ArrayList<UnileverData> arrayList){

        try {
            DbHandler db = DbHandler.getInstance();
            db.createDb();

            Calendar firstDate = GregorianCalendar.getInstance();
            firstDate.set(2020, 1, 1, 0, 0, 0);

            List<UnileverData> unileverDataList = db.getAllEntries();
            arrayList.addAll(unileverDataList);

            int[] range = IntStream.rangeClosed(1, 1000).toArray();
            for(int item : range){
                if(arrayList.isEmpty()){
                    UnileverData first = new UnileverData(firstDate);
                    arrayList.add(first);
                    db.addItemToTableUnilever(first);
                }else{
                    UnileverData lastElement = arrayList.get(arrayList.size() - 1);
                    Calendar nextDate = lastElement.getCalendar();
                    nextDate.add(Calendar.DATE, 1);
                    UnileverData nextUnileverItem = new UnileverData(nextDate);
                    arrayList.add(nextUnileverItem);
                    db.addItemToTableUnilever(nextUnileverItem);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
