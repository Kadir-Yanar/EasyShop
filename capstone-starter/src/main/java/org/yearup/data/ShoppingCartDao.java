package org.yearup.data;

import org.yearup.models.Category;
import org.yearup.models.ShoppingCart;

import java.util.List;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    void addProductToCart(int userId, int productId);
    void updateProductQuantity(int userId, int productId, int quantity);
    void removeProductFromCart(int userId, int productId);
    void clearCart(int userId);
    void update(int categoryId, Category category);
    void delete(int categoryId);
}
