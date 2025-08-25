import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './ShoppingCardPage.css';
import CartItemCard from '../components/Card/CartItemCard';
import OrderSummary from '../components/Card/OrderSummery';
import YouMayAlsoLike from '../components/Card/YouMayAlsoLike';
import Footer from '../components/Footer';

const ShoppingCartPage = () => {
    const [cartItems, setCartItems] = useState([]);
    const [promoDiscount, setPromoDiscount] = useState(0);
    const [loading, setLoading] = useState(true);
    const [isCheckingOut, setIsCheckingOut] = useState(false);
    const { customerId: urlCustomerId } = useParams();

    const userData = localStorage.getItem('user');
    const customerId = userData ? JSON.parse(userData).id : urlCustomerId || null;

    useEffect(() => {
        const fetchCartData = async () => {
            if (!customerId) {
                setCartItems([]);
                setLoading(false);
                return;
            }
            const token = localStorage.getItem('token');
            if (!token) {
                setCartItems([]);
                setLoading(false);
                return;
            }
            try {
                const response = await fetch(`http://localhost:8080/api/v1/carts/${customerId}`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                if (!response.ok) {
                    setCartItems([]);
                } else {
                    const data = await response.json();
                    if (!data || !Array.isArray(data.items)) {
                        setCartItems([]);
                    } else {
                        const formattedItems = data.items.map(item => ({
                            id: item.productId,
                            title: item.productName,
                            author: item.author,
                            price: item.itemCost,
                            quantity: item.quantity,
                            image: item.imageSource,
                            // Pass the 'rented' status directly to the item object.
                            isRented: item.rented
                        }));
                        setCartItems(formattedItems);
                    }
                }
            } catch {
                toast.error("Error fetching cart data");
                setCartItems([]);
            } finally {
                setLoading(false);
            }
        };
        fetchCartData();
    }, [customerId]);

    const handleRemoveItem = async (productId) => {
        const token = localStorage.getItem('token');
        if (!token) return;
        try {
            const response = await fetch(`http://localhost:8080/api/v1/carts/${customerId}/items/${productId}`, {
                method: 'DELETE',
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (!response.ok) throw new Error();
            setCartItems(currentItems =>
                currentItems.filter(item => item.id !== productId)
            );
            toast.info("Item removed from cart");
        } catch {
            toast.error("Failed to remove item");
        }
    };

    // The handleQuantityChange function is no longer used, but kept for completeness.
    const handleQuantityChange = (productId, newQuantity) => {
        console.log(`TODO: Implement API call to update product ${productId} to quantity ${newQuantity}`);
    };

    const handleCheckout = async () => {
        setIsCheckingOut(true);
        const token = localStorage.getItem('token');
        if (!token) {
            setIsCheckingOut(false);
            toast.error("Please log in to checkout");
            return;
        }
        try {
            const response = await fetch(`http://localhost:8080/api/orders/create-from-cart/${customerId}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) throw new Error();
            await response.json();
            setCartItems([]);
            toast.success("Your order has been placed successfully!");
        } catch {
            toast.error("Failed to create order");
        } finally {
            setIsCheckingOut(false);
        }
    };

    const handlePromoApply = (promoCode) => {
        console.log(`Applying promo code: ${promoCode}`);
        setPromoDiscount(10);
        toast.success("Promo code applied!");
    };

    const subtotal = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);
    const estimatedTax = 0;

    if (loading) {
        return <div className="container my-5 text-center"><h2>Loading Your Cart...</h2></div>;
    }

    return (
        <div className="shopping-cart-page container">
            <ToastContainer position="top-center" />
            <div className="row">
                <div className="col-lg-8">
                    <h1 className="fw-bold">Shopping Cart</h1>
                    <p className="text-muted mb-4">Review and checkout your items</p>
                    {cartItems.length > 0 ? (
                        cartItems.map((item) => (
                            <CartItemCard
                                key={item.id}
                                item={item}
                                onRemove={handleRemoveItem}
                                // Pass the 'isRented' status to the CartItemCard component.
                                isRented={item.isRented}
                            />
                        ))
                    ) : (
                        <div className="card p-5 text-center text-muted">
                            Your cart is empty.
                        </div>
                    )}
                    <button className="btn btn-outline-secondary mt-3">Continue Shopping</button>
                </div>
                <div className="col-lg-4">
                    <OrderSummary
                        subtotal={subtotal}
                        discount={promoDiscount}
                        estimatedTax={estimatedTax}
                        onPromoApply={handlePromoApply}
                        onCheckout={handleCheckout}
                        isCheckingOut={isCheckingOut}
                    />
                </div>
            </div>
            <YouMayAlsoLike />
            <Footer />
        </div>
    );
};

export default ShoppingCartPage;
