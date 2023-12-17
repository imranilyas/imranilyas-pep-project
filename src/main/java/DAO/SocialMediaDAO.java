package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class SocialMediaDAO {
    // For RegisterUser
    public boolean getUser(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select username from account where username = ? limit 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            // If there is a result, then there was a found user
            if(result != null) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // For CreateMessage
    public boolean getUser(int account_id) {
        return false;
    }

    public Account registerUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        // Check for valid username and password
        if(account.getUsername().trim().isEmpty() || account.getPassword().trim().length() < 4) {
            return null;
        }
        // Check if username already exists
        if(getUser(account.getUsername())) {
            return null;
        }

        try {
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // Set Parameters
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Execute Query
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();

            if(result.next()) {
                int generatedAccountId = result.getInt(1);
                System.out.println(generatedAccountId);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // NOT DONE YET
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) return null;
        // TODO: check if message.getpostedBy is a real user
        try {
            // SQL String
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set parameters
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            // Execute the query
            preparedStatement.executeUpdate();
            // Get the generated message_id
            ResultSet result = preparedStatement.getGeneratedKeys();

            if(result.next()) {
                int generatedId = result.getInt(1);
                return new Message(generatedId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch()); 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
