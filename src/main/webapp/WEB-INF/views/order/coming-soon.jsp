<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Coming Soon</title>
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

<div class="max-w-lg mx-auto px-6 py-24 text-center">
    <div class="bg-white rounded-2xl shadow p-12">
        <div class="text-8xl mb-6">🚧</div>
        <h2 class="text-3xl font-bold text-gray-800 mb-3">
            Coming Soon!
        </h2>
        <p class="text-gray-500 mb-8">
            This feature is currently being developed
            by our team. Check back soon!
        </p>
        <a href="/order/menu"
           class="bg-orange-600 text-white px-8 py-3 rounded-xl
                      font-bold hover:bg-orange-700">
            Back to Menu
        </a>
    </div>
</div>
</body>
</html>