package test_data;

import org.testng.annotations.DataProvider;

public class Data {

    @DataProvider
    public Object[][] invalidDataForBody() {
        return new Object[][]{
                {-1, 3950, "Alan", "Wake", "Id can't be negative"},
                {0, 3950, "Alan", "Wake", "Id can't be zero"},
                {192, 0, "Alan", "Wake", "BookId can't be zero"},
                {192, -1, "Alan", "Wake", "BookId can't be negative"},
                {293, 123405, "", "Wake", "Name can't be empty"},
                {293, 123405, "Alan", "", "Lastname can't be empty"},
                {293, 123405, "", "", "Name and lastname can't be empty"},
                {293, 123405, "*%)(#", "Wake", "Name can't has special symbols"},
                {293, 123405, "Alan", "+_+_+_", "Lastname can't has special symbols"},
                {293, 123405, "111111", "Wake", "Name can't has numbers"},
                {293, 123405, "Alan", "000000", "Lastname can't has numbers"},
                {293, 123405, "Alan", "000000", "Lastname can't has numbers"}
        };
    }

    @DataProvider
    public Object[][] invalidId() {
        return new Object[][]{
                {1039329434, "The id doesn't exist"}
        };

    }
}
