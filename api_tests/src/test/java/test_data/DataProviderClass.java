package test_data;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider
    public Object[][] invalidDataForBodyAuthorsEndpointPost() {
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
    public Object[][] invalidDataForBodyAuthorsEndpointPut() {
        return new Object[][]{
                {0, "Alan", "Wake", "BookId can't be zero"},
                {-1, "Alan", "Wake", "BookId can't be negative"},
                {123405, "", "Wake", "Name can't be empty"},
                {123405, "Alan", "", "Lastname can't be empty"},
                {123405, "", "", "Name and lastname can't be empty"},
                {123405, "*%)(#", "Wake", "Name can't has special symbols"},
                {123405, "Alan", "+_+_+_", "Lastname can't has special symbols"},
                {123405, "111111", "Wake", "Name can't has numbers"},
                {123405, "Alan", "000000", "Lastname can't has numbers"},
                {123405, "Alan", "000000", "Lastname can't has numbers"}
        };
    }

    @DataProvider
    public Object[][] invalidDataForBodyUsersEndpointPost() {
        return new Object[][]{
                {-1, "Alan", "12kkk12", "Id can't be negative"},
                {0, "Alan", "12kkk12", "Id can't be zero"},
                {949459593, "Alan", "12kkk12", "Id doesn't exist"},
                {293, "", "Wake", "Username can't be empty"},
                {293, "Alan", "", "Password can't be empty"},
                {293, "", "", "Username and password can't be empty"},
                {293, "*%)(#", "Wake", "Username can't has special symbols"},
                {293, "111111", "Wake", "Name can't has numbers"},
                {293, "Alan", "0000000000", "Password contains more than 10 symbols"},
                {293, "Alan", "0", "Password should contains at least 5 symbols"},
                {293, "Alan", "0000-1", "Password can have a negative value"}
        };
    }

    @DataProvider
    public Object[][] invalidDataForBodyUsersEndpointPut() {
        return new Object[][]{
                {"", "Wake", "Username can't be empty"},
                {"Alan", "", "Password can't be empty"},
                {"", "", "Username and password can't be empty"},
                {"*%)(#", "Wake", "Username can't has special symbols"},
                {"111111", "Wake", "Name can't has numbers"},
                {"Alan", "0000000000", "Password contains more than 10 symbols"},
                {"Alan", "0", "Password should contains at least 5 symbols"},
                {"Alan", "0000-1", "Password can not have a negative value"}
        };
    }

    @DataProvider
    public Object[][] invalidId() {
        return new Object[][]{
                {-1, "The id can not be negative"},
                {0, "The id can not be zero"}
        };

    }
}
