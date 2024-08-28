package lab10.second;

public class Export {
    private String name;
    private String country;
    private int quantity;

    public Export() {
        this.name = "";
        this.country = "";
        this.quantity = 0;
    }

    public Export(String name, String country, int quantity) {
        this.name = name;
        this.country = country;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Export{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Export export = (Export) o;
        return quantity == export.quantity && name.equals(export.name) && country.equals(export.country);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
