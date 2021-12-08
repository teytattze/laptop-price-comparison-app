import BaseLayout from './layout/base-layout';
import { Route, Routes } from 'react-router-dom';
import Home from './pages/home';
import Products from './pages/products';
import Product from './pages/product';
import ProductsByBrand from './pages/products-by-brand';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route element={<BaseLayout />}>
        <Route path="/products" element={<Products />} />
        <Route path="/products/brands/:brand" element={<ProductsByBrand />} />
        <Route path="/products/:id" element={<Product />} />
      </Route>
    </Routes>
  );
}
