<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${restaurant.name} — Menu</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">

<nav class="bg-orange-600 text-white px-6 py-4 flex justify-between items-center shadow-md">
  <h1 class="text-2xl font-bold">🍔 Food Delivery</h1>
  <div class="flex gap-4">
    <a href="/order/cart"
       class="bg-white text-orange-600 px-4 py-2 rounded-lg font-semibold hover:bg-orange-50">
      🛒 Cart
    </a>
    <a href="/order/history"
       class="bg-white text-orange-600 px-4 py-2 rounded-lg font-semibold hover:bg-orange-50">
      My Orders
    </a>
  </div>
</nav>

<div class="max-w-6xl mx-auto px-6 py-10">

  <!-- Back Button + Restaurant Name -->
  <div class="flex items-center gap-4 mb-6">
    <a href="/order/restaurants"
       class="bg-white text-gray-700 px-4 py-2 rounded-xl
              font-semibold hover:bg-gray-200 shadow text-sm">
      ← Back to Restaurants
    </a>
    <div>
      <h2 class="text-3xl font-bold text-gray-800">
        ${restaurant.name}
      </h2>
      <p class="text-sm text-gray-500">
        🚚 Delivery fee: Rs. ${restaurant.deliveryFee}
      </p>
    </div>
  </div>

  <!-- Success/Error -->
  <c:if test="${not empty success}">
    <div class="bg-green-100 text-green-800 px-4 py-3 rounded-xl mb-6">
        ${success}
    </div>
  </c:if>
  <c:if test="${not empty error}">
    <div class="bg-red-100 text-red-800 px-4 py-3 rounded-xl mb-6">
        ${error}
    </div>
  </c:if>

  <!-- Category Filter -->
  <div class="flex flex-wrap gap-2 mb-8">
    <a href="/order/menu/${restaurant.id}"
       class="px-4 py-2 rounded-full text-sm font-semibold
              ${empty activeCategory ? 'bg-orange-600 text-white' :
              'bg-white text-gray-700 border border-gray-200 hover:bg-orange-100'}">
      All
    </a>
    <c:forEach var="category" items="${categories}">
      <a href="/order/menu/${restaurant.id}?category=${category}"
         class="px-4 py-2 rounded-full text-sm font-semibold
                ${activeCategory == category ? 'bg-orange-600 text-white' :
                'bg-white text-gray-700 border border-gray-200 hover:bg-orange-100'}">
          ${category}
      </a>
    </c:forEach>
  </div>

  <!-- Food Items -->
  <form action="/order/cart/${restaurant.id}" method="post">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
      <c:forEach var="item" items="${foodItems}">
        <div class="bg-white rounded-2xl shadow overflow-hidden">
          <c:choose>
            <c:when test="${not empty item.imageUrl}">
              <img src="${item.imageUrl}" alt="${item.name}"
                   class="w-full h-48 object-cover"/>
            </c:when>
            <c:otherwise>
              <div class="w-full h-48 bg-gray-200 flex items-center
                          justify-center text-5xl">
                🍽️
              </div>
            </c:otherwise>
          </c:choose>
          <div class="p-4">
            <div class="flex justify-between items-start mb-1">
              <h3 class="font-bold text-gray-800">${item.name}</h3>
              <span class="text-orange-600 font-bold">
                Rs. ${item.price}
              </span>
            </div>
            <p class="text-gray-500 text-sm mb-3">
                ${item.description}
            </p>
            <span class="bg-orange-100 text-orange-700 text-xs
                         px-2 py-1 rounded-full">
                ${item.category}
            </span>
            <div class="flex items-center gap-3 mt-3">
              <label class="text-sm text-gray-600">Qty:</label>
              <input type="number"
                     name="quantity_${item.id}"
                     min="0" max="25" value="0"
                     class="w-16 border border-gray-300 rounded-lg
                            px-2 py-1 text-center focus:outline-none
                            focus:ring-2 focus:ring-orange-400"/>
              <span class="text-xs text-gray-400">Max 25</span>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>

    <!-- Add to Cart Button -->
    <div class="fixed bottom-6 right-6">
      <button type="submit"
              class="bg-orange-600 text-white px-8 py-4 rounded-2xl
                     font-bold text-lg shadow-lg hover:bg-orange-700">
        🛒 Add to Cart
      </button>
    </div>
  </form>
</div>
</body>
</html>