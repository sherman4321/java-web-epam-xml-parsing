package by.epam.xmlparsing.bean;

import java.util.Objects;

public class DrugManufacturer {
    private String name;
    private Certificate certificate;
    private DrugPackage drugPackage;
    private int dosage;

    public DrugManufacturer() {
    }

    public DrugManufacturer(String name, Certificate certificate, DrugPackage drugPackage, int dosage) {
        this.name = name;
        this.certificate = certificate;
        this.drugPackage = drugPackage;
        this.dosage = dosage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugManufacturer that = (DrugManufacturer) o;
        return dosage == that.dosage &&
                Objects.equals(name, that.name) &&
                Objects.equals(certificate, that.certificate) &&
                Objects.equals(drugPackage, that.drugPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, certificate, drugPackage, dosage);
    }

    @Override
    public String toString() {
        return "DrugManufacturer{" +
                "name='" + name + '\'' +
                ", certificate=" + certificate +
                ", drugPackage=" + drugPackage +
                ", dosage=" + dosage +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public DrugPackage getDrugPackage() {
        return drugPackage;
    }

    public void setDrugPackage(DrugPackage drugPackage) {
        this.drugPackage = drugPackage;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }
}
