<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurants</title>
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
    <h2 class="text-3xl font-bold text-gray-800 mb-2">
        Available Restaurants
    </h2>
    <p class="text-gray-500 mb-8">
        Choose a restaurant and order from multiple places in one go!
    </p>

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

    <!-- Restaurant Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <c:forEach var="restaurant" items="${restaurants}">
            <a href="/order/menu/${restaurant.id}"
               class="bg-white rounded-2xl shadow overflow-hidden
                hover:shadow-lg transition-shadow duration-200 block">

                <!-- Restaurant Image -->
                <c:choose>
                    <c:when test="${not empty restaurant.imageUrl}">
                        <img src="${restaurant.imageUrl}"
                             alt="${restaurant.name}"
                             class="w-full h-48 object-cover"/>
                    </c:when>
                    <c:otherwise>
                        <div class="w-full h-48 bg-gray-200 flex items-center
                        justify-center text-6xl">
                            🍽️
                        </div>
                    </c:otherwise>
                </c:choose>

                <div class="p-5">
                    <div class="flex justify-between items-start mb-2">
                        <h3 class="font-bold text-gray-800 text-xl">
                                ${restaurant.name}
                        </h3>
                        <!-- Open Badge -->
                        <span class="bg-green-100 text-green-700 text-xs
                         px-2 py-1 rounded-full font-semibold">
              ● Open
            </span>
                    </div>

                    <p class="text-gray-500 text-sm mb-4">
                            ${restaurant.description}
                    </p>

                    <div class="flex justify-between items-center">
            <span class="text-sm text-gray-500">
              🚚 Delivery: <span class="font-semibold text-gray-700">
                Rs. ${restaurant.deliveryFee}
              </span>
            </span>
                        <span class="bg-orange-600 text-white px-4 py-2
                         rounded-xl text-sm font-semibold">
              View Menu →
            </span>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>