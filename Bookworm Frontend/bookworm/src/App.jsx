import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'; // ✅ ensure styles are loaded
// Import your pages and components
import Login from './components/AuthPage/Login';
import CategoriesPage from './pages/CategoriesPage';
import HomePage1 from './pages/HomePage1';
import MyLibraryPage from './pages/MyLibraryPage';
import MyShelfPage from './pages/MyShelfPage';
import ShoppingCartPage from './pages/ShoppingCardPage';
import Navbar from './components/Ui/Navbar';
import PrivateRoute from './routes/PrivateRoute';
import AboutUsPage from './pages/AboutUsPage';
function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<HomePage1 />} />
        <Route path="/CategoriesPage" element={<CategoriesPage />} />
        <Route path="/Login" element={<Login />} />
        
        {/* Protected Routes */}
        <Route path="/MyLibraryPage" element={<PrivateRoute><MyLibraryPage /></PrivateRoute>} />
        <Route path="/MyShelfPage" element={<PrivateRoute><MyShelfPage /></PrivateRoute>} />
        <Route path="/AboutUsPage" element={<PrivateRoute><AboutUsPage /></PrivateRoute>} />

        {/* THIS IS THE FIX: Ensure this route exists and is correct.
          The path "/cart/:customerId" matches URLs like "/cart/12", "/cart/45", etc.
        */}
        <Route path="/cart/:customerId" element={<PrivateRoute><ShoppingCartPage /></PrivateRoute>} />
      </Routes>

       {/* ✅ Toast container for global notifications */}
      <ToastContainer
        position="top-center"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="colored"
      />


    </BrowserRouter>
  );
}

export default App;