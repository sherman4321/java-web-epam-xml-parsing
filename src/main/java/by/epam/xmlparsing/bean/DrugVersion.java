package by.epam.xmlparsing.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DrugVersion {
    private DrugForm form;
    private List<DrugManufacturer> manufacturers;

    public DrugVersion() {
        manufacturers = new ArrayList<>();
    }

    public DrugVersion(DrugForm form) {
        this.form = form;
        manufacturers = new ArrayList<>();
    }

    public DrugVersion(DrugForm form, List<DrugManufacturer> manufacturers) {
        this.form = form;
        this.manufacturers = manufacturers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugVersion version = (DrugVersion) o;
        return form == version.form &&
                Objects.equals(manufacturers, version.manufacturers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(form, manufacturers);
    }

    @Override
    public String toString() {
        return "DrugVersion{" +
                "form=" + form +
                ", manufacturers=" + manufacturers +
                '}';
    }

    public DrugForm getForm() {
        return form;
    }

    public void setForm(DrugForm form) {
        this.form = form;
    }

    public List<DrugManufacturer> getManufacturers() {
        return new ArrayList<>(manufacturers);
    }

    public void setManufacturers(List<DrugManufacturer> manufacturers) {
        this.manufacturers = new ArrayList<>(manufacturers);
    }

    public DrugManufacturer getManufacturer(int index) {
        return manufacturers.get(index);
    }

    public void setManufacturer(DrugManufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }
}
