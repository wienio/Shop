package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product,String> firstColumn;
    @FXML
    private TableColumn<Product,Integer> secondColumn;

    @FXML
    private Label typeLabel;
    @FXML
    private Label productLabel;
    @FXML
    private Label amountLabel;

    @FXML
    private CheckBox preservativesCheckBox;

    private Main main; // referencja

    public Controller() {
    }

    @FXML
    public void initialize() {
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        secondColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        showProductDetails(null);
        productsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    private void showProductDetails(Product product) {
        if (product != null) {
            typeLabel.setText(product.getType().toString());
            productLabel.setText(product.getName());
            amountLabel.setText(String.valueOf(product.getAmount()));
            preservativesCheckBox.setSelected(product.isIfConttainsPreservatives());
            if (product.isIfConttainsPreservatives()) {
                preservativesCheckBox.setText("Tak");
            }
            else {
                preservativesCheckBox.setText("Nie");
            }
        }
        else {
            typeLabel.setText("");
            productLabel.setText("");
            amountLabel.setText("");
            preservativesCheckBox.setSelected(false);
        }
        preservativesCheckBox.setDisable(true);
        preservativesCheckBox.setStyle("-fx-opacity: 1");
    }

    @FXML
    private void handleDeleteProduct() {
        int selectedIndex=productsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            productsTable.getItems().remove(selectedIndex);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nic nie wybrano!");
            alert.setHeaderText("Nie wybrano żadnego produktu!");
            alert.setContentText("Proszę wybrać jakiś produkt!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null ) {
            boolean okClicked = main.showProductEdit(selectedProduct);
            if (okClicked) {
                showProductDetails(selectedProduct);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nic nie wybrano.");
            alert.setHeaderText("Nie wybrano produktu!");
            alert.setContentText("Prosze wybrac produkt z listy!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewProduct() {
        Product temp = new Product(0,false,"",ProductType.BREAD);
        boolean okClicked = main.showProductEdit(temp);
        if (okClicked) {
            main.getProducts().add(temp);
        }
    }

    @FXML
    private void handleNew() {
        main.getProducts().clear();
        main.setProductFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null) {
            main.loadProductDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File productFile = main.getProductFilePath();
        if (productFile != null) {
            main.saveProductsDataToFile(productFile);
        }
        else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null ) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            main.saveProductsDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shop");
        alert.setHeaderText("O aplikacji");
        alert.setContentText("Autor: Kamil Wieniecki");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    public void setMain (Main main) {
        this.main=main;
        productsTable.setItems(main.getProducts());
    }
}
