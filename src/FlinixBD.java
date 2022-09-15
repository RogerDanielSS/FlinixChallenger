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
        
        User user = new User("userName", "userName@email.com", "12345678", "current_timestamp()", "789");
        
        // this user is not on database, I did't implemented post to User class

        Doctor doctor = new Doctor( "1243", "Botafogo Botafogo", "789456", "4321");

        doctor.postProdutivity_CurrentDate("120.50", "Eu sou Fluminense e vou cantar pra te apoiar e assim até o dia que eu morrer, vamos beber");
        //String[] teste = { "1243", "Botafogo Botafogo", "789456", "4321" };

        //conect.genericPost("doctors", teste);

        //String test = user.getDoctorByID("1243");

       // System.out.println(test);
    }

    public static void printCursosSheet() throws SQLException {
        String url = "jdbc:mysql://localhost/cadastro";

        Connection conexao = DriverManager.getConnection(url, "root", "");

        PreparedStatement pesquisa = conexao.prepareStatement("select * from cursos");

        ResultSet resultado = pesquisa.executeQuery();

        while (resultado.next()) {
            String codigo = resultado.getString("codigo");
            String nome = resultado.getString("nome");
            String descricao = resultado.getString("descricao");
            String carga = resultado.getString("carga");
            String total_aulas = resultado.getString("total_aulas");
            String ano = resultado.getString("ano");

            System.out.println(codigo + nome + descricao + carga + total_aulas + ano);
        }
    }

    public void insert() throws SQLException {
        String url = "jdbc:mysql://localhost/cadastro";
        Connection conexao = DriverManager.getConnection(url, "root", "");
        String sql = "INSERT INTO cursos VALUE (DEFAULT, 'teste', 'isso é um teste', '00', '12', '2000')";
        PreparedStatement atualizar = conexao.prepareStatement(sql);
        atualizar.execute();

        printCursosSheet();
    }
}
