// src/pages/CategoriesPage.jsx

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Footer from '../components/Footer';
import BookCard from '../components/BookCard';
import BookDetailModal from '../components/BookDetailModal';
import AddToCartModal from '../components/AddToCartModal';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      // Corrected: Use backticks for the template literal
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const CategoriesPage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchType, setSearchType] = useState('name');

  // --- HIGHLIGHT START: New State for Frontend Pagination ---
  const [allBooks, setAllBooks] = useState([]); // Stores all books fetched once
  const [booksToDisplay, setBooksToDisplay] = useState([]); // The books currently visible
  const [page, setPage] = useState(1);
  const BOOKS_PER_PAGE = 10;
  // --- HIGHLIGHT END ---

  const [results, setResults] = useState([]); // Search results are separate
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedBook, setSelectedBook] = useState(null);
  const [bookDetailLoading, setBookDetailLoading] = useState(false);
  const [isCartModalOpen, setIsCartModalOpen] = useState(false);
  const [cartModalMode, setCartModalMode] = useState('buy'); // 'buy' or 'rent'
  const [selectedBookForCart, setSelectedBookForCart] = useState(null);

  // --- HIGHLIGHT START: New useEffect for initial fetch ---
  useEffect(() => {
    const fetchAllBooks = async () => {
      setIsLoading(true);
      try {
        // Fetch ALL books once, as the API doesn't seem to support pagination
        const response = await axiosInstance.get('/products');
        setAllBooks(response.data);
        // Initially, display only the first 10 books
        setBooksToDisplay(response.data.slice(0, BOOKS_PER_PAGE));
      } catch (err) {
        console.error("Error fetching all books:", err);
        setError('Failed to load the book collection.');
      } finally {
        setIsLoading(false);
      }
    };
    fetchAllBooks();
  }, []);
  // --- HIGHLIGHT END ---

  // --- HIGHLIGHT START: useEffect to handle 'Load More' ---
  useEffect(() => {
    if (page > 1) {
      const start = (page - 1) * BOOKS_PER_PAGE;
      const end = start + BOOKS_PER_PAGE;
      const newBooks = allBooks.slice(start, end);
      setBooksToDisplay(prevBooks => [...prevBooks, ...newBooks]);
    }
  }, [page, allBooks]);
  // --- HIGHLIGHT END ---

  useEffect(() => {
    if (!searchTerm.trim()) {
      setResults([]); // Clear search results
      return;
    }
    const delayDebounceFn = setTimeout(async () => {
      setIsLoading(true);
      try {
        const params = { [searchType]: searchTerm };
        // Corrected: Use backticks for the template literal
        const response = await axiosInstance.get(`/products/search/by-${searchType}`, { params });
        setResults(response.data);
      } catch (err) {
        // Corrected: Use backticks for the template literal
        console.error(`Error searching by ${searchType}:`, err);
        setError('Failed to fetch search results.');
        setResults([]);
      } finally {
        setIsLoading(false);
      }
    }, 500);
    return () => clearTimeout(delayDebounceFn);
  }, [searchTerm, searchType, allBooks]);

  const openCartModal = (book, mode) => {
    const userStr = localStorage.getItem("user");
    if (!userStr || userStr === "undefined") {
      toast.error("You must be logged in to add items to the cart.");
      return;
    }

    const user = JSON.parse(userStr);
    if (!user.id) {
      toast.error("Could not find your user ID. Please try logging in again.");
      return;
    }
    
    setSelectedBookForCart(book);
    setCartModalMode(mode);
    setIsCartModalOpen(true);
  };

  const confirmAddToCart = async ({ quantity, rentNumberOfDays }) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const customerId = user.id;

    const payload = {
      productId: selectedBookForCart.id,
      quantity: quantity,
      isRented: cartModalMode === 'rent',
      rentNumberOfDays: rentNumberOfDays,
    };

    try {
      // Corrected: Use backticks for the template literal
      await axiosInstance.post(`/carts/${customerId}/items`, payload);
      toast.success(`"${selectedBookForCart.name}" has been added to your cart.`);
    } catch (err) {
      console.error('Failed to add item to cart:', err);
      if (err.response && (err.response.status === 401 || err.response.status === 403)) {
        toast.error('Your session has expired. Please log in again.');
      } else {
        toast.error('There was an error adding the item to your cart. Please try again.');
      }
    } finally {
      setIsCartModalOpen(false);
    }
  };

  const handleCardClick = async (bookId) => {
    try {
      setBookDetailLoading(true);
      // Corrected: Use backticks for the template literal
      const response = await axiosInstance.get(`/products/${bookId}`);
      setSelectedBook(response.data);
    } catch (err) {
      toast.error('Failed to load book details');
      console.error(err);
    } finally {
      setBookDetailLoading(false);
    }
  };

  // --- HIGHLIGHT START ---
  const handleLoadMore = () => {
    setPage(prevPage => prevPage + 1);
  };
  // --- HIGHLIGHT END ---

  // --- HIGHLIGHT START ---
  // The list to display depends on whether a search term is active
  const booksToList = searchTerm.trim() ? results : booksToDisplay;
  const hasMoreBooks = booksToDisplay.length < allBooks.length;
  // --- HIGHLIGHT END ---

  return (
    <div className="categories-page container ">
      {/* Updated the ToastContainer position to center */}
      <ToastContainer position="top-center" />
      <div className="text-center text-md-start mb-4">
        <h1 className="fw-bold">Search Collection</h1>
        <p className="text-muted">Discover books and audiobooks by name or author.</p>
      </div>

      <div className="input-group mb-4">
        <select
          className="form-select flex-grow-0"
          style={{ width: '120px' }}
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <option value="name">By Name</option>
          <option value="author">By Author</option>
        </select>
        <input
          type="text"
          className="form-control"
          // Corrected: Use backticks for the template literal
          placeholder={`Search for a book by ${searchType}...`}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {error && <div className="alert alert-danger">{error}</div>}
      {isLoading && <div className="text-center"><p>Loading...</p></div>}

      <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-4">
        {!isLoading && booksToList.map((item) => (
          <div key={item.id} className="col">
            <BookCard
              book={item}
              onAddToCart={openCartModal}
              onCardClick={handleCardClick}
            />
          </div>
        ))}
      </div>

      {(!isLoading && booksToList.length === 0) && (
        <div className="text-center p-5">
          <p className="text-muted fs-4">
            {searchTerm.trim() ? 'No results found.' : 'No books available.'}
          </p>
        </div>
      )}

      {/* --- HIGHLIGHT START --- */}
      {/* Show Load More button only if not searching and there are more books */}
      {!isLoading && !searchTerm.trim() && hasMoreBooks && (
        <div className="text-center my-5">
          <button className="btn btn-primary btn-lg" onClick={handleLoadMore}>
            Load More Books
          </button>
        </div>
      )}
      {/* --- HIGHLIGHT END --- */}

      {isCartModalOpen && selectedBookForCart && (
        <AddToCartModal
          book={selectedBookForCart}
          mode={cartModalMode}
          onClose={() => setIsCartModalOpen(false)}
          onConfirm={confirmAddToCart}
        />
      )}

      {selectedBook && (
        <BookDetailModal book={selectedBook} onClose={() => setSelectedBook(null)} />
      )}

      <Footer />
    </div>
  );
};

export default CategoriesPage;
