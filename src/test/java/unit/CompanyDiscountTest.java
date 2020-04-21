package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyDiscountTest {
    private List<discount.percentages.concrete.CompanyDiscount> companyDiscount = new ArrayList<>();

    @BeforeEach
    void setup() {
        List<String> listOfCompanyName = Arrays.asList("Airbus", "Safran Engineering Services");
        for (String companyName : listOfCompanyName) {
            this.companyDiscount.add(new discount.percentages.concrete.CompanyDiscount(companyName));
        }
    }

    @Test
    void test() {
        assertEquals(this.companyDiscount.get(0).getCode(), "CE_AIRBUS");
        assertEquals(this.companyDiscount.get(1).getCode(), "CE_SAFRANENGINEERINGSERVICES");
    }
}
