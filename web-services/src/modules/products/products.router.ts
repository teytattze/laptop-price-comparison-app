import express from 'express';
import {
  getProduct,
  getProductCount,
  getProductCountByBrand,
  listBrands,
  listProductByBrand,
  listProducts,
} from './products.controller';

const productsRouter = express.Router();

productsRouter.get('/', listProducts);

productsRouter.get('/search', listProductByBrand);

productsRouter.get('/search/count', getProductCountByBrand);

productsRouter.get('/brands', listBrands);

productsRouter.get('/count', getProductCount);

productsRouter.get('/:id', getProduct);

export { productsRouter };
