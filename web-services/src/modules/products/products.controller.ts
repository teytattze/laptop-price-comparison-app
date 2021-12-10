import { NextFunction, Request, Response } from 'express';
import {
  findAllProducts,
  findProduct,
  findProductsByBrand,
  findProductCount,
  findAllBrands,
  findProductCountByBrand,
} from './products.model';
import { paginationHelper } from '../../lib/pagination-helper';

export const listProducts = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const { page, perPage, offset } = paginationHelper(req);
  try {
    const products = await findAllProducts({ page, perPage, offset });
    res.status(200).json(products);
  } catch (e) {
    next(e);
  }
};

export const listBrands = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const products = await findAllBrands();
    res.status(200).json(products);
  } catch (e) {
    next(e);
  }
};

export const getProduct = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const { id } = req.params;
  try {
    const product = await findProduct(id);
    res.status(200).json(product);
  } catch (e) {
    next(e);
  }
};

export const getProductCount = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const count = await findProductCount();
    res.status(200).json(count);
  } catch (e) {
    next(e);
  }
};

export const getProductCountByBrand = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const { keywords } = req.query;
  try {
    const count = await findProductCountByBrand(keywords as string);
    res.status(200).json(count);
  } catch (e) {
    next(e);
  }
};

export const listProductByBrand = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const { page, perPage, offset } = paginationHelper(req);
  const { keywords } = req.query;

  try {
    const products = await findProductsByBrand(keywords as string, {
      page,
      perPage,
      offset,
    });
    res.status(200).json(products);
  } catch (e) {
    next(e);
  }
};
