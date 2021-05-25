package smshandy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import smshandy.controller.HandyController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        String path = System.getProperty("user.dir") +"\\src\\main\\java\\smshandy\\view\\sample.fxml";
//        Parent parent = FXMLLoader.load(Main.class.getResource("view\\sample.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/view/phones.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        primaryStage.setScene(new Scene(root));

//        HandyController controller = loader.getController();
        primaryStage.show();
    }


    public static List<PrepaidSmsHandy> getAllPrepaidHandy(){
        List<PrepaidSmsHandy> res= new ArrayList<>();
        res.add(new PrepaidSmsHandy("+1111111", getAllProviders().get(0), "Anna"));
        res.add(new PrepaidSmsHandy("+1111112",getAllProviders().get(1),"Bob"));
        res.add(new PrepaidSmsHandy("+1111113)",getAllProviders().get(2),"Tom"));

        return res;
    }
    public static List<SmsHandy> getAllHandy(){
        ArrayList<SmsHandy> res = new ArrayList<>();
        res.addAll(getAllTariffHandy());
        res.addAll(getAllPrepaidHandy());
        return res;
    }
    public static List<TariffPlanSmsHandy> getAllTariffHandy(){
        ArrayList<TariffPlanSmsHandy> res = new ArrayList<>();
        res.add(new TariffPlanSmsHandy("+1111114",getAllProviders().get(0), "Beka"));
        res.add(new TariffPlanSmsHandy("+1111115",getAllProviders().get(1), "Kyla"));
        res.add(new TariffPlanSmsHandy("+1111116",getAllProviders().get(2), "Bema"));
        return null;
    }

    public static List<Provider> getAllProviders(){
        ArrayList<Provider> providers = new ArrayList<>();
        providers.add(new Provider("Beeline"));
        providers.add(new Provider("Megacom"));
        providers.add(new Provider("Oshka!"));

        return providers;
    }
}
