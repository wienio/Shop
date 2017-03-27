package sample;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Wienio on 2017-03-22.
 */
@XmlRootElement(name = "products")
public class ProductListWrapper {
    private List<Product> products;

    @XmlElement(name = "products")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products=products;
    }
}
