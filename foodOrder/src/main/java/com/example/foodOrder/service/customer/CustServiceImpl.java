package com.example.foodOrder.service.customer;

import com.example.foodOrder.dto.*;
import com.example.foodOrder.entity.*;
import com.example.foodOrder.enums.OrderStatus;
import com.example.foodOrder.enums.PaymentStatus;
import com.example.foodOrder.repo.*;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustServiceImpl implements CustService{


    public static String blobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Value("${razorpay.api.key}")
    private String apikey;
    @Value("${razorpay.api.secret}")
    private String apisecret;
    private final UserRepo userRepo;

    private final CatRepo catRepo;


    @Autowired
    private JavaMailSender emailsender;





    private final ProductRepo productRepo;

    private final CartRepo cartRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderRepo orderRepo;

    private final ResRepo repo;

    private final CartItemRepo cartItemRepo;
    private final PasswordEncoder passwordEncoder;

    public CustServiceImpl(UserRepo userRepo, CatRepo catRepo, ProductRepo productRepo, CartRepo cartRepo, OrderItemRepo orderItemRepo, OrderRepo orderRepo, ResRepo repo, CartItemRepo cartItemRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.catRepo = catRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.orderItemRepo = orderItemRepo;
        this.orderRepo = orderRepo;
        this.repo = repo;
        this.cartItemRepo = cartItemRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getUser(Long userId) {
        User user=userRepo.findById(userId).get();
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPhoneNum(user.getPhoneNum());
        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        User user=userRepo.findById(userId).get();
        System.out.println(user);
        userRepo.delete(user);
    }
    public void sendEmail(Long userId,String toEmail, String body,String subject){
        return;

    }

    @Override
    public PaymentResponse createPayment(Long orderId, String jwt) throws RazorpayException {
        OrderItem orderItem=orderItemRepo.findById(orderId).get();
        System.out.println(apikey);
        System.out.println(apisecret);
        RazorpayClient razorpayClient=new RazorpayClient(apikey,apisecret);

        JSONObject paymentLinkRequest=new JSONObject();
        paymentLinkRequest.put("amount",orderItem.getTotal()*100);
        paymentLinkRequest.put("currency","INR");

        JSONObject customer=new JSONObject();
        customer.put("name",orderItem.getUser().getName());
        customer.put("email",orderItem.getUser().getEmail());
        paymentLinkRequest.put("customer",customer);

        JSONObject notification=new JSONObject();
        notification.put("sms",true);
        notification.put("email",true);
        paymentLinkRequest.put("notify",notification);

        paymentLinkRequest.put("callback_url","http://localhost:5173/payment/"+orderId);
        paymentLinkRequest.put("callback_method","get");
        PaymentLink paymentLink=razorpayClient.paymentLink.create(paymentLinkRequest);
        String paymenturl=paymentLink.get("short_url");
        String paymentId=paymentLink.get("id");
        PaymentResponse paymentResponse=new PaymentResponse();
        paymentResponse.setPaymentId(paymentId);
        paymentResponse.setPaymentLink(paymenturl);
        return paymentResponse;
    }

    @Override
    public ApiRes successfulPayment(String paymentid, Long orderId) throws RazorpayException {
        OrderItem orderItem=orderItemRepo.findById(orderId).get();
        Restraunt restraunt=repo.findById(orderItem.getRestraunt().getId()).get();
        User user=userRepo.findById(restraunt.getOwner().getId()).get();
        String ownerEmail= user.getEmail();
        RazorpayClient razorpayClient=new RazorpayClient(apikey,apisecret);
        try{
            Payment payment=razorpayClient.payments.fetch(paymentid);
            if(payment.get("status").equals("captured")){
                orderItem.setPaymentId(paymentid);
                orderItem.setPaymentStatus(PaymentStatus.COMPLETED);
                OrderItem orderItem1=orderItemRepo.save(orderItem);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                String body="Order Details: " +"Customer Name: "+orderItem.getUser().getName()+" "+"Order Id: "+orderItem.getId()+" " + "Order Total: "+"₹"+orderItem.getTotal()+" "+"Date: "+orderItem.getOrderedAt()+" "+" "+"Order Status: "+orderItem.getOrderStatus()+" "+"Payment Status: "+orderItem1.getPaymentStatus()+" "+"Payment Reference Id: "+orderItem1.getPaymentId();
                mailMessage.setSubject("Order Received from "+orderItem.getUser().getName());
                mailMessage.setText("Hello "+user.getName()+" "+body);
                mailMessage.setFrom("mittalvaibhav277@gmail.com");
                mailMessage.setTo(ownerEmail);
                emailsender.send(mailMessage);
            }
            else{
                orderItem.setPaymentStatus(PaymentStatus.FAILED);
                orderItemRepo.save(orderItem);
            }
            ApiRes apiRes=new ApiRes();
            apiRes.setMessage("Payment Successful");
            apiRes.setStatus(true);
            return apiRes;
        }
        catch (RazorpayException e){
            throw new RazorpayException(e.getMessage());
        }
    }

    @Override
    public OrderItemDto getOrderById(Long orderId) {
        OrderItem orderItem=orderItemRepo.findById(orderId).get();
        OrderItemDto orderItemDto=new OrderItemDto();
        orderItemDto.setTotal(orderItem.getTotal());
        orderItemDto.setUserId(orderItem.getUser().getId());
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setPaymentStatus(orderItem.getPaymentStatus());
        orderItemDto.setOrderStatus(orderItem.getOrderStatus());
        orderItemDto.setOwnerName(orderItem.getUser().getName());
        orderItemDto.setPaymentid(orderItem.getPaymentId());
        orderItemDto.setOrderedAt(orderItem.getOrderedAt());
        orderItemDto.setRestId(orderItem.getRestraunt().getId());
        orderItemDto.setRestName(orderItem.getRestraunt().getName());
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        return orderItemDto;
    }

    @Override
    public List<ResDto> getResByName(String name) {
        return repo.findAllByNameContaining(name).stream().map(Restraunt::getResDto).collect(Collectors.toList());

    }

    @Override
    public List<ProductDto> getProdsByCategory(Long catId) {
        return productRepo.findAllByCategoryId(catId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user=userRepo.findById(userId).get();
        System.out.println(user);
        if(userId!=user.getId()){
            System.out.println("not matching");
            return null;
        }
        if( userDto.getName()!=null && !userDto.getName().isEmpty()){
            user.setName(userDto.getName());
        }
        if(userDto.getEmail()!=null && !userDto.getEmail().isEmpty()){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPhoneNum()!=null &&  !userDto.getPhoneNum().isEmpty()){
            user.setPhoneNum(userDto.getPhoneNum());
        }
        User updatedUser=userRepo.save(user);
        UserDto newUserDto=new UserDto();
        newUserDto.setName(updatedUser.getName());
        newUserDto.setEmail(updatedUser.getEmail());
        newUserDto.setPhoneNum(updatedUser.getPhoneNum());
        return newUserDto;

    }
    @Override
    public List<CategoryDto> getCats() {
        return catRepo.findAll().stream().map(Category::getCatDto).collect(Collectors.toList());
    }
    @Override
    public List<ResDto> getRes() {
        return repo.findAll().stream().map(Restraunt::getResDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCatbyRes(Long restId) {
        return catRepo.findAllByRestrauntId(restId).stream().map(Category::getCatDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCatbyResName(String restName) {
        return catRepo.findAllByRestrauntNameContaining(restName).stream().map(Category::getCatDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCatbyName(String name) {
        return catRepo.findAllByNameContaining(name).stream().map(Category::getCatDto).collect(Collectors.toList());

    }

    @Override
    public List<ProductDto> getProds(Long restId) {
        return productRepo.findAllByRestrauntId(restId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProdByCat(Long restId, Long catId) {
        return productRepo.findAllByRestrauntIdAndCategoryId(restId,catId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductbyNameandRestraunt(String productName,Long restrauntId) {
        return productRepo.findAllByRestrauntIdAndProductNameContaining(restrauntId,productName).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductbyNameandRestrauntandCat(String productName, Long restrauntId, Long catId) {
        return productRepo.findAllByRestrauntIdAndCategoryIdAndProductNameContaining(restrauntId,catId,productName).stream().map(Product::getProductDto).collect(Collectors.toList());
    }
    @Override
    public ProductDto getProductbyId(Long productId) {
        Product product=productRepo.findById(productId).get();
        ProductDto productDto=new ProductDto();
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());

        productDto.setRestrauntId(product.getRestraunt().getId());
        productDto.setRestrauntName(product.getRestraunt().getName());
        productDto.setCategoryid(product.getCategory().getId());
        productDto.setCategoryname(product.getCategory().getName());
        productDto.setName(product.getProductName());
        Blob blob= product.getImg();
        String base64=blobToBase64(blob);
        productDto.setReturnedimg(base64);
        return productDto;
    }



    @Override
    public OrderItemDto addOrder(Long userId, Long restrauntId,int total) {
        User user=userRepo.findById(userId).get();
        //Cart cart=cartRepo.findByCustomer(user);

        Order order=orderRepo.findByUser(user);
        //order.setOrderItems();
        Restraunt restraunt=repo.findById(restrauntId).get();
        OrderItem orderItem=new OrderItem();
       // orderItem.setCart(cart);
        orderItem.setRestraunt(restraunt);
        orderItem.setOrderStatus(OrderStatus.PENDING);
        orderItem.setUser(user);
        orderItem.setOrderedAt(new Date(System.currentTimeMillis()));
        orderItem.setOrder(order);
        orderItem.setTotal(total);
        orderItem.setPaymentStatus(PaymentStatus.PENDING);
        orderItem.setPaymentId(null);

        OrderItem savedOne=orderItemRepo.save(orderItem);
        OrderItemDto orderItemDto=new OrderItemDto();
        orderItemDto.setId(savedOne.getId());
        String body="Order Id:"+savedOne.getId()+" " + "Order Total: "+"₹"+total+" "+"Date: "+savedOne.getOrderedAt()+" "+"Restraunt: "+savedOne.getRestraunt().getName()+" "+"Status: "+savedOne.getOrderStatus();
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("Your order details");
        mailMessage.setText("Hello "+user.getName()+" "+body);
        mailMessage.setFrom("mittalvaibhav277@gmail.com");
        mailMessage.setTo(user.getEmail());
        emailsender.send(mailMessage);
        System.out.println("sent");
        return orderItemDto;

    }

    @Override
    public List<OrderItemDto> getOrders(Long userId) {
        User user=userRepo.findById(userId).get();
        Order order=orderRepo.findByUser(user);
        List<OrderItem> orderItems=order.getOrderItems();
        List<OrderItemDto> orderItemDtos=new ArrayList<>();
        for(OrderItem orderItem :orderItems){
            System.out.println(orderItem);
            OrderItemDto orderItemDto=new OrderItemDto();
            orderItemDto.setOrderId(order.getId());
            orderItemDto.setOrderedAt(orderItem.getOrderedAt());
            orderItemDto.setOrderStatus(orderItem.getOrderStatus());
            orderItemDto.setId(orderItem.getId());
            orderItemDto.setUserId(orderItem.getUser().getId());
//            Optional<Cart> cart=cartRepo.findById(orderItem.getCart().getId());
//            if(cart.isPresent()){
//                orderItemDto.setCartItemList(cart.get().getCartItems());
//
//            }
            orderItemDto.setRestId(orderItem.getRestraunt().getId());
            orderItemDto.setOwnerName(orderItem.getUser().getName());
            orderItemDto.setRestName(orderItem.getRestraunt().getName());
            orderItemDto.setTotal(orderItem.getTotal());
            orderItemDto.setPaymentStatus(orderItem.getPaymentStatus());
            orderItemDto.setPaymentid(orderItem.getPaymentId());
//            orderItemDto.setCartId(orderItem.getCart().getId());
            orderItemDtos.add(orderItemDto);

        }
        return orderItemDtos;

    }

    @Override
    public CartItemDto addToCart(CartRequest cartRequest, Long productId,Long userId) {
        CartItem cartItem=new CartItem();
        Restraunt restraunt=repo.findById(cartRequest.getRestId()).get();
        Category category=catRepo.findById(cartRequest.getCatId()).get();
        Product product=productRepo.findById(productId).get();
        cartItem.setRestraunt(restraunt);
        User user=userRepo.findById(userId).get();
        Cart cart=cartRepo.findByCustomer(user);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setCategory(category);
        cartItem.setUser(user);
        cart.setTotalPrice(cart.getTotalPrice()+cartRequest.getPrice());
        cartRepo.save(cart);
        CartItemDto cartItemDto=new CartItemDto();
        CartItem savedCartItem=cartItemRepo.save(cartItem);
        cartItemDto.setId(savedCartItem.getId());
        return cartItemDto;
    }

    @Override
    public List<CartItemDto> getCart(Long userId) {
        User user=userRepo.findById(userId).get();
        Cart cart=cartRepo.findByCustomer(user);
        List<CartItem> cartItems=cartItemRepo.findAllByCart(cart);
        List<CartItemDto> cartItemDtoList=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            CartItemDto cartItemDto=new CartItemDto();
            cartItemDto.setCartId(cartItem.getCart().getId());
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setProductId(cartItem.getProduct().getId());
            Blob blob=cartItem.getProduct().getImg();
            String base64=blobToBase64(blob);
            cartItemDto.setProductName(cartItem.getProduct().getProductName());

            cartItemDto.setProductImg(base64);
            cartItemDto.setRestId(cartItem.getRestraunt().getId());
            cartItemDto.setProductPrice(cartItem.getProduct().getPrice());
            cartItemDto.setRestrauntName(cartItem.getRestraunt().getName());
            cartItemDtoList.add(cartItemDto);
        }
        return cartItemDtoList;
    }

    @Override
    public void updateCart(Long userId, Long cartItemId) {
        User user=userRepo.findById(userId).get();
        Cart cart=cartRepo.findByCustomer(user);
        CartItem cartItem=cartItemRepo.findById(cartItemId).get();
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getProduct().getPrice());
        cartItemRepo.delete(cartItem);
        cartRepo.save(cart);

    }

    @Override
    public void clearCart(Long userId) {
        User user=userRepo.findById(userId).get();
        Cart cart=cartRepo.findByCustomer(user);
        List<CartItem> cartItems=cartItemRepo.findAllByCart(cart);
        for(CartItem cartItem:cartItems){
            cartItemRepo.delete(cartItem);
        }
        cart.setTotalPrice(0);
        cartRepo.save(cart);

    }
}
