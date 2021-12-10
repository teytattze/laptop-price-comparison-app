import supertest from 'supertest';
import { app } from '../../app';
import { getDbConnection } from '../../common/database';
import {
  MOCK_PRODUCTS,
  MOCK_PRODUCTS_BRANDS,
  MOCK_PRODUCTS_WITH_SOURCES,
} from '../../modules/products/__mocks__/products.mock';
import * as productsDao from '../../modules/products/products.model';
import {
  findAllBrands,
  findAllProducts,
  findProduct,
  findProductCount,
  findProductCountByBrand,
  findProductsByBrand,
} from '../../modules/products/__mocks__/products.model';

let server: any;
let request: supertest.SuperAgentTest;

beforeAll(async () => {
  server = await app.listen();
  request = supertest.agent(server);
});
afterAll(async () => {
  await getDbConnection().end();
  await server.close();
});

describe('ProductsController', () => {
  jest
    .spyOn(productsDao, 'findAllProducts')
    .mockImplementation(findAllProducts);
  jest.spyOn(productsDao, 'findAllBrands').mockImplementation(findAllBrands);
  jest.spyOn(productsDao, 'findProduct').mockImplementation(findProduct);
  jest
    .spyOn(productsDao, 'findProductCount')
    .mockImplementation(findProductCount);
  jest
    .spyOn(productsDao, 'findProductsByBrand')
    .mockImplementation(findProductsByBrand);
  jest
    .spyOn(productsDao, 'findProductCountByBrand')
    .mockImplementation(findProductCountByBrand);

  const BASE_PATH = '/api/products';

  it('GET /', async () => {
    await request
      .get(`${BASE_PATH}/`)
      .expect(200)
      .expect((res) => {
        expect(res.body.length).toEqual(20);
        expect(res.body).toEqual(MOCK_PRODUCTS.slice(0, 20));
      });
  });

  it('GET /?page=2&perPage=5', async () => {
    await request
      .get(`${BASE_PATH}/?page=2&perPage=5`)
      .expect(200)
      .expect((res) => {
        expect(res.body.length).toEqual(5);
        expect(res.body).toEqual(MOCK_PRODUCTS.slice(5, 10));
      });
  });

  it('GET /?page=4&perPage=100', async () => {
    await request
      .get(`${BASE_PATH}/?page=4&perPage=100`)
      .expect(200)
      .expect((res) => {
        expect(res.body.length).toEqual(0);
        expect(res.body).toEqual([]);
      });
  });

  it('GET /brands', async () => {
    await request
      .get(`${BASE_PATH}/brands`)
      .expect(200)
      .expect((res) => {
        expect(res.body.length).toEqual(11);
        expect(res.body).toEqual(MOCK_PRODUCTS_BRANDS);
      });
  });

  it('GET /search/count?keywords=asus', async () => {
    await request
      .get(`${BASE_PATH}/search/count?keywords=asus`)
      .expect(200)
      .expect((res) => {
        expect(res.body).toEqual(76);
      });
  });

  it('GET /count', async () => {
    await request
      .get(`${BASE_PATH}/count`)
      .expect(200)
      .expect((res) => {
        expect(res.body).toEqual(300);
      });
  });

  it('GET /search?keywords=alienware&page=1&perPage=5', async () => {
    await request
      .get(`${BASE_PATH}/search?keywords=alienware&page=1&perPage=5`)
      .expect(200)
      .expect((res) => {
        expect(res.body.length).toEqual(5);
        expect(res.body).toEqual(MOCK_PRODUCTS.slice(40, 45));
      });
  });

  it('GET /:id 200', async () => {
    await request
      .get(`${BASE_PATH}/a7891e3a-b18e-4952-90ec-efb96d33fb6d`)
      .expect(200)
      .expect((res) => {
        expect(res.body).toEqual(MOCK_PRODUCTS_WITH_SOURCES[1]);
      });
  });

  it('GET /:id 404', async () => {
    await request
      .get(`${BASE_PATH}/abc`)
      .expect(404)
      .expect((res) => {
        expect(res.body).toEqual({
          statusCode: 404,
          message: 'Product not founded',
        });
      });
  });
});
