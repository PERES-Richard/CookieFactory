package discount.percentages.concrete;

import discount.percentages.PercentageDiscount;

public class CompanyDiscount extends PercentageDiscount {
    public CompanyDiscount(String companyName) {
        this.value = 0.05;
        this.code = parseCompanyName(companyName);
    }

    private String parseCompanyName(String companyName) {
        String parsed_company = companyName.replaceAll(" ", "");
        StringBuilder str_builder = new StringBuilder(parsed_company);
        str_builder.insert(0, "CE_");
        return str_builder.toString().toUpperCase();
    }
}
