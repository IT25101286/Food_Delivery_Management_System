<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">

<nav class="bg-orange-600 text-white px-6 py-4 flex justify-between items-center shadow-md">
    <h1 class="text-2xl font-bold">🍔 Food Delivery</h1>
    <div class="flex gap-4">
        <a href="/order/restaurants"
           class="bg-white text-orange-600 px-4 py-2 rounded-lg font-semibold hover:bg-orange-50">
            Restaurants
        </a>
        <a href="/order/history"
           class="bg-white text-orange-600 px-4 py-2 rounded-lg font-semibold hover:bg-orange-50">
            My Orders
        </a>
    </div>
</nav>

<div class="max-w-2xl mx-auto px-6 py-10">
    <div class="bg-white rounded-2xl shadow p-8">
        <div class="flex justify-between items-start mb-6">
            <div>
                <h2 class="text-2xl font-bold text-gray-800">
                    Order #${order.id}
                </h2>
                <p class="text-gray-500 text-sm">${order.createdAt}</p>
            </div>
            <!-- Status — Coming Soon -->
            <span class="bg-gray-100 text-gray-600
                             px-3 py-1 rounded-full text-sm font-semibold">
                    🕐 Coming Soon
                </span>
        </div>

        <!-- Order Items -->
        <div class="mb-6">
            <h3 class="font-bold text-gray-700 mb-3">Items Ordered</h3>
            <div class="space-y-2">
                <c:forEach var="item" items="${order.items}">
                    <div class="flex justify-between text-sm">
                            <span class="text-gray-600">
                                ${item.foodItemName} x${item.quantity}
                            </span>
                        <span class="font-semibold text-gray-800">
                                Rs. ${item.price}
                            </span>
                    </div>
                </c:forEach>
            </div>
            <div class="border-t mt-3 pt-3 flex justify-between font-bold">
                <span>Total</span>
                <span class="text-orange-600">Rs. ${order.totalAmount}</span>
            </div>
        </div>

        <!-- Track Order — Coming Soon -->
        <div class="bg-gray-50 rounded-xl p-4 mb-6 text-center">
            <p class="text-gray-400 text-sm mb-2">Order Tracking</p>
            <a href="/order/coming-soon"
               class="bg-gray-200 text-gray-600 px-6 py-2 rounded-xl
                          font-semibold hover:bg-gray-300 inline-block">
                🕐 Track Order — Coming Soon
            </a>
        </div>

        <!-- Actions -->
        <div class="flex gap-3">
            <a href="/order/history"
               class="bg-gray-200 text-gray-700 px-6 py-2 rounded-xl
                          font-semibold hover:bg-gray-300">
                ← Back
            </a>
            <c:if test="${order.status == 'PENDING' or
                              order.status == 'PAYMENT_SUCCESS'}">
                <form action="/order/cancel/${order.id}"
                      method="post"
                      onsubmit="return confirm('Cancel this order?')">
                    <button type="submit"
                            class="bg-red-500 text-white px-6 py-2
                                       rounded-xl font-semibold hover:bg-red-600">
                        Cancel Order
                    </button>
                </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>