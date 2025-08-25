import React from 'react';
// import BookCard from '../BookCard';
import './YouMayAlsoLike.css';

// Sample data for "You May Also Like" section
const youMayAlsoLikeItems = [
  {
    id: 3,
    title: 'The Psychology of Money',
    author: 'Morgan Housel',
    type: 'Ebook',
    image: 'https://placehold.co/300x400/9c27b0/ffffff?text=Book',
    price: 11.99,
  },
  {
    id: 4,
    title: 'Project Hail Mary',
    author: 'Andy Weir',
    type: 'Ebook',
    image: 'https://placehold.co/300x400/e91e63/ffffff?text=Book',
    price: 13.99,
  },
  {
    id: 5,
    title: 'Dune',
    author: 'Frank Herbert',
    type: 'Ebook',
    image: 'https://placehold.co/300x400/ffc107/ffffff?text=Book',
    price: 9.99,
  },
  {
    id: 6,
    title: 'The Hobbit',
    author: 'J.R.R. Tolkien',
    type: 'Audiobook',
    image: 'https://placehold.co/300x400/6c757d/ffffff?text=Audiobook',
    price: 15.99,
  },
];

// Reusable card for items in "You May Also Like"
const BookCard = ({ item }) => (
  <div className="book-card card h-100 border-0 shadow-sm">
    {/* <img src={item.image} className="card-img-top rounded-top" alt={item.title} />
    <div className="card-body d-flex flex-column">
      <span className="badge bg-secondary mb-2 align-self-start">{item.type}</span>
      <h6 className="card-title fw-bold text-truncate" title={item.title}>{item.title}</h6>
      <p className="card-text text-muted text-truncate">{item.author}</p>
      <div className="mt-auto d-flex justify-content-between align-items-center">
        <span className="fw-bold text-purple">${item.price.toFixed(2)}</span>
        <button className="btn btn-pink btn-sm">Add to Cart</button>
      </div>
    </div> */}
  </div>
);

// Component for the "You May Also Like" section
const YouMayAlsoLike = () => (
  <div className="you-may-also-like mt-5">
    <h3 className="fw-bold mb-4"></h3>
    <div className="content-carousel">
      {youMayAlsoLikeItems.map((item) => (
        <div className="carousel-item-wrapper" key={item.id}>
          <BookCard item={item} />
        </div>
      ))}
    </div>
  </div>
);

export default YouMayAlsoLike;
