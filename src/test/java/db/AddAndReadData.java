package db;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.java.Log;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static randomaizer.GenerateData.getRandomWord;

@Log
public class AddAndReadData extends ConnectToDB {


    public void addDataToTable() throws SQLException {
        List<Person> persons = generatePersons(5);

        PreparedStatement preparedStatement = CONNECTION.prepareStatement(
                "INSERT INTO Persons (FirstName, LastName, Gender, Nationality) VALUES " +
                        "(?, ?, ?, ?)");
        for (Person person : persons) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getGender());
            preparedStatement.setString(4, person.getNationality());
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                log.info("Failed to store in DB : " + person.getFirstName() + " " + person.getLastName()
                        + " " + person.getGender() + " " + person.getNationality());
            }
        }
    }

    public List<Person> readDataFromTable() {
        List<Person> listWithPerson = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(
                    "SELECT * FROM Persons LIMIT 2");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listWithPerson.add(new Person(rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("Nationality")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listWithPerson;
    }

    public List<Person> generatePersons(int amountOfPersons) {
        List<Person> listWithPerson = new ArrayList<>();
        for (int i = 0; i < amountOfPersons; i++) {
            listWithPerson.add(new Person(getRandomWord(), getRandomWord(), getRandomWord(), getRandomWord()));
        }
        return listWithPerson;
    }
}
