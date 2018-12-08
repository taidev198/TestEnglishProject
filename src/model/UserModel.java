package model;



import helper.ConnectDataHelper;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by traig on 10:18 AM, 11/15/2018
 */
public class UserModel  {

    public void update(User user, int id){
        String query = "update userinfo SET userInfoid = ?, username =  ?, password = ?, address = ?, email= ?, phone = ?, firstname = ?, lastname = ?, birth = ? WHERE userInfoid = ?";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(user.getUserInfoid()));
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getFirstname());
            statement.setString(8, user.getLastname());
            statement.setDate(9, Date.valueOf(user.getBirth()));
            statement.setInt(10, id);
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> getUserInfo(){
        String query = "select * from userinfo";
        List<List<String>> ans = new ArrayList<>();
        try(Statement statement = ConnectDataHelper.getInstance().connectDB().createStatement()) {
            statement.execute("use sql12268841");
            ResultSet resultSet = statement.executeQuery(query);

            List<String> userInfoid = new ArrayList<>();
            List<String> username = new ArrayList<>();
            List<String> password = new ArrayList<>();
            List<String> address = new ArrayList<>();
            List<String> email = new ArrayList<>();
            List<String> phone = new ArrayList<>();
            List<String> firstname = new ArrayList<>();
            List<String> lastname = new ArrayList<>();
            List<String> birth = new ArrayList<>();
            while (resultSet.next()){
                userInfoid.add(String.valueOf(resultSet.getInt("userInfoid")));
                username.add(resultSet.getString("username"));
                password.add(resultSet.getString("password"));
                address.add(resultSet.getString("address"));
                email.add(resultSet.getString("email"));
                phone.add(String.valueOf(resultSet.getInt("phone")));
                firstname.add(resultSet.getString("firstname"));
                lastname.add(resultSet.getString("lastname"));
                birth.add(String.valueOf(resultSet.getDate("birth")));
            }
            ans.add(userInfoid);
            ans.add(username);
            ans.add(password);
            ans.add(address);
            ans.add(email);
            ans.add(phone);
            ans.add(firstname);
            ans.add(lastname);
            ans.add(birth);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public boolean removeUser(int id){
        String query = "delete from userInfo where userInfoid = " + id;
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use data");
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addUser(User user, boolean isSignup){
        String query = "insert into userinfo(userInfoid,username, password, address, email, phone, firstname, lastname, birth) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (isSignup)
            query = "insert into userinfo(username, password, email, phone) values(?, ?, ?, ?)";
        try(PreparedStatement statement = ConnectDataHelper.getInstance().connectDB().prepareStatement(query)) {
            statement.execute("use sql12268841");
            if (isSignup){
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3,user.getEmail());
                statement.setInt(4, Integer.parseInt(user.getPhone()));
            }else {
                statement.setInt(1, Integer.parseInt(user.getUserInfoid()));
                statement.setString(2,user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getAddress());
                statement.setString(5,user.getEmail());
                statement.setInt(6, Integer.parseInt(user.getPhone()));
                statement.setString(7, user.getFirstname());
                statement.setString(8, user.getLastname());
                statement.setDate(9, Date.valueOf(user.getBirth()));
            }
            statement.executeUpdate();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static class User{
        private String userInfoid;
        private String username;
        private String password;
        private String address = "Empty";
        private String email = "Empty";
        private String phone = "Empty";
        private String firstname = "Empty";
        private String lastname = "Empty";
        private String birth = "1990-12-31";
        private MenuButton menuButton;

        public User(String userInfoid, String username, String password, String address, String email, String phone,
                    String firstname, String lastname, String birth, MenuButton menuButton) {
            this.userInfoid = userInfoid;
            this.username = username;
            this.password = password;
            this.address = address;
            this.email = email;
            this.phone = phone;
            this.firstname = firstname;
            this.lastname = lastname;
            this.birth = birth;
            this.menuButton = menuButton;
        }

        public User( String username, String password, String email, String phone) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.phone = phone;

        }

        public String getUserInfoid() {
            return userInfoid;
        }

        public void setUserInfoid(String userInfoid) {
            this.userInfoid = userInfoid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public MenuButton getMenuButton() {
            return menuButton;
        }

        public void setMenuButton(MenuButton menuButton) {
            this.menuButton = menuButton;
        }
    }

}
