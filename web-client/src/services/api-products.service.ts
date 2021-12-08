import axios from 'axios';
import { IPagination } from '../shared/interfaces/pagination.interface';
import { API_PRODUCTS_BASE_URL } from '../shared/constants/services.const';

const baseUrl = API_PRODUCTS_BASE_URL;

export const listProducts = async (pagination: IPagination) => {
  const res = await axios.get(`${baseUrl}?page=${pagination.page}`);
  return res.data;
};

export const listProductsByBrand = async (
  keywords: string,
  pagination: IPagination,
) => {
  const res = await axios.get(
    `${baseUrl}/search?keywords=${keywords}&page=${pagination.page}`,
  );
  return res.data;
};

export const listProductsBrands = async () => {
  const res = await axios.get(`${baseUrl}/brands`);
  return res.data;
};

export const getProduct = async (id: string) => {
  const res = await axios.get(`${baseUrl}/${id}`);
  return res.data;
};

export const getProductsCount = async () => {
  const res = await axios.get(`${baseUrl}/count`);
  return res.data;
};

export const getProductsBrandCount = async (keywords: string) => {
  const res = await axios.get(`${baseUrl}/search/count?keywords=${keywords}`);
  return res.data;
};
