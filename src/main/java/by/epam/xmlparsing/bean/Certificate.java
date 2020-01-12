package by.epam.xmlparsing.bean;

import java.time.LocalDate;
import java.util.Objects;

public class Certificate {
    private int id;
    private LocalDate dateOfIssue;
    private String registerOrganization;

    public Certificate() {
    }

    public Certificate(int id, LocalDate dateOfIssue, String registerOrganization) {
        this.id = id;
        this.dateOfIssue = dateOfIssue;
        this.registerOrganization = registerOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return id == that.id &&
                Objects.equals(dateOfIssue, that.dateOfIssue) &&
                Objects.equals(registerOrganization, that.registerOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfIssue, registerOrganization);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", dateOfIssue=" + dateOfIssue +
                ", registerOrganization='" + registerOrganization + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getRegisterOrganization() {
        return registerOrganization;
    }

    public void setRegisterOrganization(String registerOrganization) {
        this.registerOrganization = registerOrganization;
    }
}
