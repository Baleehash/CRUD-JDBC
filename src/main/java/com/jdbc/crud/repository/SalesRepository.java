package com.jdbc.crud.repository;

import com.jdbc.crud.model.Sales;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesRepository {
    private final String URL = "jdbc:mysql://localhost:3306/akademik";
    private final String USER = "root";
    private final String PASSWORD = "password";

    public List<Sales> findAll() {
        List<Sales> salesList = new ArrayList<>();
        String query = "SELECT * FROM sales";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("id"));
                sale.setItem(rs.getString("item"));
                sale.setQuantity(rs.getInt("quantity"));
                sale.setAmount(rs.getDouble("amount"));
                salesList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesList;
    }

    public Sales findById(int id) {
        Sales sale = null;
        String query = "SELECT * FROM sales WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sale = new Sales();
                sale.setId(rs.getInt("id"));
                sale.setItem(rs.getString("item"));
                sale.setQuantity(rs.getInt("quantity"));
                sale.setAmount(rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }

    public void save(Sales sales) {
        String query = "INSERT INTO sales (item, quantity, amount) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sales.getItem());
            stmt.setInt(2, sales.getQuantity());
            stmt.setDouble(3, sales.getAmount());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Sales sales) {
        String query = "UPDATE sales SET item = ?, quantity = ?, amount = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sales.getItem());
            stmt.setInt(2, sales.getQuantity());
            stmt.setDouble(3, sales.getAmount());
            stmt.setInt(4, sales.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String query = "DELETE FROM sales WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

