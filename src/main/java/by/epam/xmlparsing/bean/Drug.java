package by.epam.xmlparsing.bean;

import java.util.*;

public class Drug {
    private int id;
    private String name;
    private String pharm;
    private DrugGroup group;
    private List<String> analogs;
    private List<DrugVersion> versions;

    public Drug() {
        analogs = new ArrayList<>();
        versions = new ArrayList<>();
    }

    public Drug(int id, String name, String pharm, DrugGroup group) {
        this.id = id;
        this.name = name;
        this.pharm = pharm;
        this.group = group;
        analogs = new ArrayList<>();
        versions = new ArrayList<>();
    }

    public Drug(int id, String name, String pharm, DrugGroup group, List<String> analogs, List<DrugVersion> versions) {
        this.id = id;
        this.name = name;
        this.pharm = pharm;
        this.group = group;
        this.analogs = analogs;
        this.versions = versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return id == drug.id &&
                Objects.equals(name, drug.name) &&
                Objects.equals(pharm, drug.pharm) &&
                group == drug.group &&
                Objects.equals(analogs, drug.analogs) &&
                Objects.equals(versions, drug.versions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pharm, group, analogs, versions);
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pharm='" + pharm + '\'' +
                ", group=" + group +
                ", analogs=" + analogs +
                ", versions=" + versions +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPharm() {
        return pharm;
    }

    public void setPharm(String pharm) {
        this.pharm = pharm;
    }

    public DrugGroup getGroup() {
        return group;
    }

    public void setGroup(DrugGroup group) {
        this.group = group;
    }

    public String getAnalog(int index) {
        return analogs.get(index);
    }

    public void setAnalog(String analog) {
        analogs.add(analog);
    }

    public List<String> getAnalogs() {
        return new ArrayList<>(analogs);
    }

    public void setAnalogs(List<String> analogs) {
        this.analogs = new ArrayList<>(analogs);
    }

    public List<DrugVersion> getVersions() {
        return new ArrayList<>(versions);
    }

    public void setVersions(List<DrugVersion> versions) {
        this.versions = new ArrayList<>(versions);
    }

    public DrugVersion getVersion(int index) {
        return versions.get(index);
    }

    public void setVersion(DrugVersion version) {
        versions.add(version);
    }
}
