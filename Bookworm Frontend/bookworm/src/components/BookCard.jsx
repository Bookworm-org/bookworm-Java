import React from 'react';
import './BookCard.css';

const BookCard = ({ book, onAddToCart, onCardClick }) => {
  if (!book) return null;

  const placeholderImage = `https://placehold.co/400x600/e91e63/white?text=${encodeURIComponent(book.name)}`;
  const imageUrl = book.imageSource || placeholderImage;

  const handleCardClick = () => {
    if (onCardClick) {
      onCardClick(book.id);
    }
  };

  return (
    <div
      className="card h-100 shadow-sm book-card-hover"
      style={{ cursor: onCardClick ? 'pointer' : 'default' }}
      onClick={handleCardClick}
    >
      <img
        src={imageUrl}
        className="card-img-top"
        alt={book.name}
        style={{ height: '250px', objectFit: 'cover' }}
        onError={(e) => {
          e.target.onerror = null;
          e.target.src = placeholderImage;
        }}
      />

      <div className="card-body d-flex flex-column">
        <span className="badge bg-pink-light text-pink-dark align-self-start mb-2">
          {book.productTypeName || 'Book'}
        </span>
        <h5 className="card-title fw-bold text-truncate">{book.name}</h5>
        <p className="card-text text-muted small">{book.author}</p>

        <div className="mt-auto">
          <p className="fw-bold mb-2 fs-5 text-success">₹{book.offerPrice?.toFixed(2)}</p>
          <div className="d-flex gap-2">
            <button
              className="btn btn-primary flex-fill"
              onClick={(e) => {
                e.stopPropagation();
                onAddToCart(book, 'buy');
              }}
            >
              Buy
            </button>

            {/* This is the corrected line */}
            {book.isRentable && (
              <button
                className="btn btn-outline-secondary flex-fill"
                onClick={(e) => {
                  e.stopPropagation();
                  onAddToCart(book, 'rent');
                }}
              >
                Rent
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookCard;