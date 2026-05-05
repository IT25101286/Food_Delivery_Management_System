<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cart</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">

<nav class="bg-orange-600 text-white px-6 py-4 flex justify-between items-center shadow-md">
  <h1 class="text-2xl font-bold">🍔 Food Delivery</h1>
  <div class="flex gap-4">
    <a href="/order/menu"
       class="bg-white text-orange-600 px-4 py-2 rounded-lg font-semibold hover:bg-orange-50">
      Menu
    </a>
    <a href="/order/history"
       class="bg-white text-orange-600 px-4 py-2 rounded-lg font-semibold hover:bg-orange-50">
      My Orders
    </a>
  </div>
</nav>

<div class="max-w-3xl mx-auto px-6 py-10">
  <h2 class="text-3xl font-bold text-gray-800 mb-8">🛒 Your Cart</h2>

  <c:choose>
    <c:when test="${empty cartItems}">
      <div class="bg-white rounded-2xl shadow p-12 text-center">
        <p class="text-6xl mb-4">🛒</p>
        <p class="text-gray-500 text-lg mb-6">Your cart is empty!</p>
        <a href="/order/menu"
           class="bg-orange-600 text-white px-6 py-3 rounded-xl
                              font-semibold hover:bg-orange-700">
          Browse Menu
        </a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="bg-white rounded-2xl shadow p-6 mb-6">
        <div class="space-y-4">
          <c:forEach var="cartItem" items="${cartItems}">
            <div class="flex justify-between items-center border-b pb-4">
              <div class="flex items-center gap-4">
                <c:if test="${not empty cartItem.foodItem.imageUrl}">
                  <img src="${cartItem.foodItem.imageUrl}"
                       alt="${cartItem.foodItem.name}"
                       class="w-16 h-16 object-cover rounded-xl"/>
                </c:if>
                <div>
                  <p class="font-bold text-gray-800">
                      ${cartItem.foodItem.name}
                  </p>
                  <p class="text-sm text-gray-500">
                    Rs. ${cartItem.foodItem.price}
                    x ${cartItem.quantity}
                  </p>
                </div>
              </div>
              <div class="flex items-center gap-4">
                <p class="font-bold text-orange-600">
                  Rs. ${cartItem.subtotal}
                </p>
                <form action="/order/cart/remove/${cartItem.foodItem.id}"
                      method="post">
                  <button type="submit"
                          class="text-red-500 hover:text-red-700">
                    ✕
                  </button>
                </form>
              </div>
            </div>
          </c:forEach>
        </div>

        <!-- Total -->
        <div class="flex justify-between items-center mt-4 pt-4">
          <span class="text-xl font-bold text-gray-800">Total</span>
          <span class="text-2xl font-bold text-orange-600">
                            Rs. ${total}
                        </span>
        </div>

        <!-- Bulk Order Notice -->
        <c:if test="${total gt 2500}">
          <div class="mt-4 bg-purple-50 border border-purple-200
                                    rounded-xl p-3 text-sm text-purple-700">
            🎁 This is a <strong>Bulk Order</strong> —
            10% discount will be applied!
          </div>
        </c:if>
      </div>

      <!-- Actions -->
      <div class="flex justify-between">
        <form action="/order/cart/clear" method="post">
          <button type="submit"
                  class="bg-gray-200 text-gray-700 px-6 py-3
                                       rounded-xl font-semibold hover:bg-gray-300">
            Clear Cart
          </button>
        </form>
        <form action="/order/checkout" method="post">
          <button type="submit"
                  class="bg-orange-600 text-white px-8 py-3
                                       rounded-xl font-bold hover:bg-orange-700">
            Place Order →
          </button>
        </form>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>