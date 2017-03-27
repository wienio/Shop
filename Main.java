package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private Stage primaryStage;

    public Main () {
        products.add(new Product(6, true,"Bulka", ProductType.BREAD ));
        products.add(new Product(15 ,true, "Chleb",ProductType.BREAD));
        products.add(new Product(50 ,false,"Grahamka", ProductType.BREAD));
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/rootLayout.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Shop");
        primaryStage.setScene(new Scene(root, 597, 312));
        primaryStage.show();
        Controller controller = loader.getController();
        controller.setMain(this);

        File file = getProductFilePath();
        if (file != null) {
            loadProductDataFromFile(file);
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean showProductEdit(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/sample/ProductEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage productStage = new Stage();
            productStage.setTitle("Edycja produktu");
            productStage.initModality(Modality.WINDOW_MODAL);
            productStage.initOwner(primaryStage);
            Scene scene= new Scene(page);
            productStage.setScene(scene);

            ProductEditController controller = loader.getController();
            controller.setProductStage(productStage);
            controller.setProduct(product);

            productStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public File getProductFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath",null);
        if (filePath != null ){
            return new File(filePath);
        }
        else {
            return null;
        }
    }

    public void setProductFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null ) {
            prefs.put("filePath", file.getPath() );
            primaryStage.setTitle("Shop - " + file.getName() );
        }
        else {
            prefs.remove("filePath");
            primaryStage.setTitle("Shop");
        }
    }

    public void loadProductDataFromFile (File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            ProductListWrapper wrapper = (ProductListWrapper) um.unmarshal(file);

            products.clear();
            products.addAll(wrapper.getProducts());

            setProductFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie mozna odczytac danych!");
            alert.setContentText("Blad odczytywania danych z plku:\n" + file.getPath() );
            alert.showAndWait();
        }
    }

    public void saveProductsDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ProductListWrapper wrapper = new ProductListWrapper();
            wrapper.setProducts(products);
            m.marshal(wrapper, file);
            setProductFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie można zapisać danych!");
            alert.setContentText("Nie można zapisać danych do pliku:\n" + file.getPath() );
            alert.showAndWait();
        }
    }
}
