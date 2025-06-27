package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.ProductDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    private final ProductDao productDao;

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao)
    {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart cart = new ShoppingCart();
        Map<Integer, ShoppingCartItem> items = new HashMap<>();

        String sql = "SELECT sc.product_id, sc.quantity " +
                "FROM shopping_cart sc " +
                "WHERE sc.user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            while (row.next())
            {
                int productId = row.getInt("product_id");
                int quantity = row.getInt("quantity");

                Product product = productDao.getById(productId);
                if (product != null) {
                    ShoppingCartItem item = new ShoppingCartItem(product, quantity);
                    items.put(productId, item);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        cart.setItems(items);
        return cart;
    }

    @Override
    public void addProductToCart(int userId, int productId)
    {
        String sql = """
                MERGE INTO shopping_cart AS target
                USING (SELECT ? AS user_id, ? AS product_id) AS source
                    ON target.user_id = source.user_id AND target.product_id = source.product_id
                WHEN MATCHED THEN
                    UPDATE SET quantity = quantity + 1
                WHEN NOT MATCHED THEN
                    INSERT (user_id, product_id, quantity)
                    VALUES (source.user_id, source.product_id, 1);
                
                """;

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity)
    {
        if (quantity <= 0) {
            removeProductFromCart(userId, productId);
            return;
        }

        String sql = "UPDATE shopping_cart SET quantity = ? " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeProductFromCart(int userId, int productId)
    {
        String sql = "DELETE FROM shopping_cart " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int userId)
    {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}