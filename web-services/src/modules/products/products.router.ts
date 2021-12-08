import express from 'express';
import {
  getProduct,
  getProductCount,
  getProductCountByBrand,
  listProductByBrand,
  listProducts,
  listProductsBrand,
} from './products.controller';

const productsRouter = express.Router();

productsRouter.get('/', listProducts);

productsRouter.get('/search', listProductByBrand);

productsRouter.get('/search/count', getProductCountByBrand);

productsRouter.get('/brands', listProductsBrand);

productsRouter.get('/count', getProductCount);

productsRouter.get('/:id', getProduct);

export { productsRouter };
