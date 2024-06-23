package com.example.Agent.controller;

import com.example.Agent.modal.Cart;
import com.example.Agent.modal.CartItem;
import com.example.Agent.modal.User;
import com.example.Agent.request.AddCartItemRequest;
import com.example.Agent.request.UpdateCartItemRequest;
import com.example.Agent.service.CartService;
import com.example.Agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem>addItemToCart(@RequestBody AddCartItemRequest request, @RequestHeader("Authorization")String jwt)throws Exception{
        CartItem cartItem=cartService.addItemToCart(request,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantitu(@RequestBody UpdateCartItemRequest request, @RequestHeader("Authorization")String jwt) throws Exception{
        CartItem cartItem=cartService.updateCartItemQuantity(request.getCartItemId(),request.getQuantity());

        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart>removeCartItem(@PathVariable Long id,@RequestHeader("Authorization")String jwt)throws Exception{
        Cart cart=cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization")String jwt)throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt)throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartService.findCartByUserId(user.getId());

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }


}
