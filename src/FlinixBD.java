import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.Conect_to_data_base;
import DTO.Doctor;
import DTO.User;

public class FlinixBD {
    public static void main(String[] args) throws Exception {
        Conect_to_data_base conect = new Conect_to_data_base();

        User user = new User("userName", "user@email.com", "12345678", "current_timestamp()", "789");

        String[] values = { "userName", "user@email.com", "12345678", "current_timestamp()", "789" };

        Integer[] functions = { 3 };

        // post user
        // conect.genericPost("users", values, functions);

        // item b)
        // user.postDoctor("789456123", "TChalla", "11111");
        // user.postDoctor("321654987", "Shuri", "22222");

        // item a)
        System.out.println(user.getDoctors());

        System.out.println("\n\n" + user.getDoctorByID("789456123"));
        System.out.println("\n\n" + user.getDoctorByCRM("22222"));

        Doctor doctor = new Doctor("789456123", "TChalla", "11111", "789");

         //doctor.postProductivity_CurrentDate("120.50", "This is a test");
         doctor.postProductivity("2022-09-15", "120.50", "This is a test");

    }
}
