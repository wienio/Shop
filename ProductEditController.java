package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

/**
 * Created by Wienio on 2017-03-20.
 */
public class ProductEditController {
    @FXML
    private TextField productName;
    @FXML
    private TextField productAmount;

    @FXML
    private CheckBox ifPreservatives;

    @FXML
    private ComboBox enumType;

    private Stage productStage;
    private Product product;
    private boolean isOkClicked = false;

    @FXML
    private void initialize() {
    }

    public boolean isOkClicked() {
        return isOkClicked;
    }

    public void setProductStage(Stage productStage) {
        this.productStage=productStage;
    }

    public void setProduct(Product product) {
        this.product=product;

        productName.setText(product.getName());
        productAmount.setText(Integer.toString(product.getAmount()));
        ifPreservatives.setSelected(product.isIfConttainsPreservatives());
        enumType.getSelectionModel().select(product.getType());
        enumType.getItems().setAll(ProductType.values());
    }

    @FXML
    private void handleOk() {
        if(isInputValid()) {
            product.setName(productName.getText());
            product.setAmount(Integer.parseInt(productAmount.getText()));
            product.setType((ProductType) enumType.getSelectionModel().getSelectedItem());
            product.setIfConttainsPreservatives(ifPreservatives.isSelected());

            isOkClicked=true;
            productStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if(productName.getText() == null || productName.getText().length() == 0) {
            errorMessage += "Brak nazwy!\n";
        }
        if (productAmount.getText()== null || productAmount.getText().length() == 0) {
            errorMessage += "Brak wpisanej ilosci!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        }
        else {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.initOwner(productStage);
            alert.setTitle("Niepoprawne pola!");
            alert.setHeaderText("Prosze podac prawidlowe dane.");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }


    @FXML
    private void handleCancel() {
        productStage.close();
    }
}
