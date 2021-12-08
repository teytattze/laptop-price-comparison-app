import { IPagination } from '../../../shared/interfaces/pagination.interface';
import { MOCK_PRODUCTS, MOCK_PRODUCTS_WITH_SOURCES } from './products.mock';
import { NotFoundException } from '../../../errors/http-exceptions';

export const findAllProducts = jest
  .fn()
  .mockImplementation((pagination: IPagination) => {
    const { perPage, offset } = pagination;
    return MOCK_PRODUCTS.slice(offset, offset + Number(perPage));
  });

export const findProduct = jest.fn().mockImplementation((id: string) => {
  const product = MOCK_PRODUCTS_WITH_SOURCES.find(
    (product) => product.id === id,
  );
  if (!product) throw new NotFoundException('Product not founded');
  return product;
});

export const findProductCount = jest.fn().mockImplementation(() => {
  return MOCK_PRODUCTS.length;
});

export const findProductsByBrand = jest
  .fn()
  .mockImplementation((keywords: string, pagination: IPagination) => {
    const { perPage, offset } = pagination;
    return MOCK_PRODUCTS.filter((product) => product.brand !== keywords).slice(
      offset,
      offset + Number(perPage),
    );
  });
