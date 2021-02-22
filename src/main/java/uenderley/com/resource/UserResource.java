package uenderley.com.resource;

import java.sql.*;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.agroal.api.*;
import io.quarkus.agroal.*;
import uenderley.com.model.User;


@Path("/user")
public class UserResource {

    @Inject
    @Default
    AgroalDataSource defaultDataSource;

    @Inject
    @DataSource("users")
    AgroalDataSource usersDataSource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> obter() {
        List<User> usuarios = User.listAll();
        usuarios.forEach(usuario -> {
            usuario.setName(usuario.getName() + " Postgres");
        });

        try {
            Connection conn = usersDataSource.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");

            while (rs.next()) {
                String name = rs.getString("name");
                
                User user = new User();
                user.setName(name + " MySQL");
                usuarios.add(user);
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String gravar(String nome) {
        User user = new User();
        user.setName(nome);

        salvarMysql(user);
        salvarPostgres(user);

        return String.format("Dados gravados com sucesso para o usuario %s", nome);
    }

    /**
     * Salva no banco auxiliar
     * @param user
     */
    private void salvarMysql(User user){
        try{
            Connection conn = usersDataSource.getConnection();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO usuario (name) " + "VALUES ( "+ user.getName() +")");
            stmt.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Salva no banco default usando o Panache
     * @param user
     */
    @Transactional
    public void salvarPostgres(User user){
        try{
            user.persist();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}