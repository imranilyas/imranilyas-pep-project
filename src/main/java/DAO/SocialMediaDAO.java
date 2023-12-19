package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class SocialMediaDAO {
    // For RegisterUser
    public boolean getUser(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select username from account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            // If the result is empty, then there was no found user
            if(!result.first()) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    // For CreateMessage
    public boolean getUser(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select account_id from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);
            ResultSet result = preparedStatement.executeQuery();

            // If the result is empty, then there was no found user
            if(!result.first()) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
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
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account loginUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select account_id, username, password from account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet result = preparedStatement.executeQuery();

            if(result.first()) {
                int account_id = result.getInt("account_id");
                return new Account(account_id, result.getString("username"), result.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // NOT DONE YET
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) return null;
        
        // TODO: check if message.getpostedBy is a real user
        // if(getUser(message.getPosted_by())) return null;
        
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
                System.out.println(generatedId);
                System.out.println(result);
                return new Message(generatedId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch()); 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> list = new ArrayList<>();
        try {
            String sql = "select * from message";
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                list.add(message);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Message getMessageByMessageId(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());        
        }
        return null;
    }

    public Message deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Grab Selected Row
            String searchSql = "select * from message where message_id = ?";
            PreparedStatement searchStatement = connection.prepareStatement(searchSql);
            searchStatement.setInt(1, message_id);
            ResultSet result = searchStatement.executeQuery();
            
            // Set Message or Return Before Deletion
            Message message;
            if(result.next()) {
                message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
            } else {
                return null;
            }
            
            // Delete Row
            String deleteSql = "delete from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);

            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
            return message;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int account_id) {
        List<Message> list = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                list.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
